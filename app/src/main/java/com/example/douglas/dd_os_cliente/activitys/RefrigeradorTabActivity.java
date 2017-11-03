package com.example.douglas.dd_os_cliente.activitys;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.douglas.dd_os_cliente.R;
import com.example.douglas.dd_os_cliente.adapters.ListRefrigeradorAdapter;
import com.example.douglas.dd_os_cliente.app.AppConfig;
import com.example.douglas.dd_os_cliente.app.AppController;
import com.example.douglas.dd_os_cliente.controler.RefrigeradorCtrl;
import com.example.douglas.dd_os_cliente.controler.ServPendenteCtrl;
import com.example.douglas.dd_os_cliente.helper.SQLiteHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RefrigeradorTabActivity extends AppCompatActivity {
    private static String TAG = RefrigeradorTabActivity.class.getName();
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private TabLayout tabLayout ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refrigerador_tab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Refrigeradores");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.containerRefri);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_refrigerador_tab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == android.R.id.home) {
//            Intent i = new Intent(getApplicationContext(), ListServPendenteFragment.class);  //your class
//            startActivity(i);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements  SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener  {

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public static RefrigeradorCtrl refrigeradorSelected;
        public static int positionREfrigerador;

        private CheckBox checkBoxExaustor;
        private CheckBox checkBoxControl;
        private Spinner spinnerMarca;
        private Spinner spinnerModelo;
        private Spinner spinnerTensao;
        private Spinner spinnerLvlEcon;
        private Spinner spinnerBTU;
        private EditText editTamanho;
        private EditText editTempoUso;
        private EditText editLotacionamento;
        public List<RefrigeradorCtrl> refrigeradores;

        private ListView lvListRefrigerador;

        private FloatingActionButton fab;

        private SQLiteHandler db;
        private AlertDialog alerta;
        private ProgressDialog dialog = null;


        private SwipeRefreshLayout swipeRefreshLayout;
        ListRefrigeradorAdapter listRefriApd;


        private int isExastor = 0,isControl = 0;
        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                                 final Bundle savedInstanceState) {

            dialog = new ProgressDialog(getContext());
            dialog.setCancelable(false);

            db = new SQLiteHandler(getContext());

            //Fragment Lista Refrigerador
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                final View rootView = inflater.inflate(R.layout.fragment_refrigerador_tab1, container, false);

                swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
                lvListRefrigerador = (ListView) rootView.findViewById(R.id.lvListRefrigerador);

                refrigeradores = new ArrayList<RefrigeradorCtrl>();

                listRefriApd = new ListRefrigeradorAdapter(getContext(), refrigeradores);
                lvListRefrigerador.setAdapter(listRefriApd);

                swipeRefreshLayout.setOnRefreshListener(this);

                swipeRefreshLayout.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                swipeRefreshLayout.setRefreshing(true);

                                               listaRefrigeradores(db.getUserDetails().getId());
                                            }
                                        }
                );

                lvListRefrigerador.setOnItemClickListener(this);
                lvListRefrigerador.setOnItemLongClickListener(this);

                //cria o AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                alerta = builder.create();


                return rootView;

            //FGragment ADD Refrigerador
            }else{

                View rootView = inflater.inflate(R.layout.fragment_refrigerador_tab2, container, false);

                fab = (FloatingActionButton) rootView.findViewById(R.id.fabSalvarRefrigerador);
                checkBoxExaustor = (CheckBox) rootView.findViewById(R.id.checkBoxExaustor);
                checkBoxControl = (CheckBox) rootView.findViewById(R.id.checkBoxControl);

                editTamanho = (EditText) rootView.findViewById(R.id.editTamanho);
                editTempoUso = (EditText) rootView.findViewById(R.id.editTempoUso);
                editLotacionamento = (EditText) rootView.findViewById(R.id.editTextLotacio);


                spinnerMarca = (Spinner) rootView.findViewById(R.id.spinnerMarca);
                ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getContext(),R.layout.simple_spinner_item, db.getMacas());
                //arrayAdapter1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                spinnerMarca.setAdapter(arrayAdapter1);

                //preenchendo Spinner
                spinnerModelo = (Spinner) rootView.findViewById(R.id.spinnerModelo);
                ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getContext(),R.layout.simple_spinner_item, db.getModelo());
                //arrayAdapter1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                spinnerModelo.setAdapter(arrayAdapter2);

                //preenchendo Spinner
                spinnerTensao = (Spinner) rootView.findViewById(R.id.spinnerTensao);
                ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(getContext(),R.layout.simple_spinner_item, db.getTencaoTomada());
                //arrayAdapter1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                spinnerTensao.setAdapter(arrayAdapter3);

                //preenchendo Spinner
                spinnerLvlEcon = (Spinner) rootView.findViewById(R.id.spinnerLvlEcon);
                ArrayAdapter<String> arrayAdapter4 = new ArrayAdapter<String>(getContext(),R.layout.simple_spinner_item, db.getNvEcon());
                //arrayAdapter1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                spinnerLvlEcon.setAdapter(arrayAdapter4);

                //preenchendo Spinner
                spinnerBTU = (Spinner) rootView.findViewById(R.id.spinnerBTU);
                ArrayAdapter<String> arrayAdapter5 = new ArrayAdapter<String>(getContext(),R.layout.simple_spinner_item, db.getBTU());
                //arrayAdapter1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                spinnerBTU.setAdapter(arrayAdapter5);


                checkBoxExaustor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(checkBoxExaustor.isChecked()){
                            checkBoxExaustor.setText("Sim");
                            isExastor = 1;
                        }else {
                            checkBoxExaustor.setText("Não");
                            isExastor = 0;
                        }
                    }
                });
                checkBoxControl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(checkBoxControl.isChecked()){
                            checkBoxControl.setText("Sim");
                            isControl =1;
                        }else {
                            checkBoxControl.setText("Não");
                            isControl = 0;
                        }
                    }
                });

                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view, "Salvando Dados", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        salvarDadosAr();
                    }
                });

                return rootView;
            }

        }


        private void listaRefrigeradores(final int idCli) {

            // showing refresh animation before making http call
            swipeRefreshLayout.setRefreshing(true);

            // Tag used to cancel the request
            String tag_string_req = "req_listaServPen";
            //final List<ServPendenteCtrl> listSerPen =null;


            StringRequest strReq = new StringRequest(Request.Method.POST,
                    AppConfig.URL_GET_All_AR_CLI, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Carregando dados: " + response.toString());

                    try {
                        JSONObject jObj = new JSONObject(response);
                        boolean error = jObj.getBoolean("error");

                        // Check for error node in json
                        if (!error) {

                            JSONArray serv_penArray = jObj.getJSONArray("data");

                            Log.e("TESTE","ENTROU: "+serv_penArray.length());
                            refrigeradores.clear();
                            for (int i = 0; i < serv_penArray.length(); i++) {
                                try {
                                    JSONObject refrigeraObj = new JSONObject(serv_penArray.get(i).toString());

                                    Log.e("Serviço ENCONTRADO: ", "Entrou no for");

                                    RefrigeradorCtrl objetoRefrigerador = new RefrigeradorCtrl();

                                    objetoRefrigerador.setId_refri(refrigeraObj.getInt("id_refri"));
                                    objetoRefrigerador.setHas_control(refrigeraObj.getInt("has_control"));
                                    objetoRefrigerador.setHas_exaustor(refrigeraObj.getInt("has_exaustor"));
                                    objetoRefrigerador.setHas_timer(Integer.valueOf(refrigeraObj.getString("has_timer")));
                                    objetoRefrigerador.setLotacionamento(refrigeraObj.getString("lotacionamento"));
                                    objetoRefrigerador.setCapaci_termica(refrigeraObj.getInt("capaci_termica"));
//                                    objetoRefrigerador.setFoto1(refrigeraObj.getString("foto1"));
//                                    objetoRefrigerador.setFoto2(refrigeraObj.getString("foto2"));
//                                    objetoRefrigerador.setFoto3(refrigeraObj.getString("foto3"));
                                    objetoRefrigerador.setMarca(refrigeraObj.getInt("marca"));
                                    objetoRefrigerador.setTipo_modelo(refrigeraObj.getInt("tipo_modelo"));
                                    objetoRefrigerador.setNivel_econo(refrigeraObj.getInt("nivel_econo"));
                                    objetoRefrigerador.setTencao_tomada(refrigeraObj.getInt("tencao_tomada"));
                                    objetoRefrigerador.setId_cliente(refrigeraObj.getInt("id_cliente"));
                                    objetoRefrigerador.setTemp_uso(refrigeraObj.getInt("temp_uso"));
                                    objetoRefrigerador.setTamanho(refrigeraObj.getString("tamanho"));
                                    objetoRefrigerador.setSaida_ar(refrigeraObj.getString("saida_ar"));
                                    refrigeradores.add(objetoRefrigerador);

                                    Log.e("LISTA",""+refrigeradores.size());
                                    Log.e("Dados sqlite: ", ""+db.getAllMyServPen().size());
                                } catch (JSONException e) {
                                    Log.e(TAG, "JSON erro ao consultar dados: " + e.getMessage());
                                }
                            }
                            listRefriApd.notifyDataSetChanged();


                        } else {
                            // Error in login. Get the error message
                            String errorMsg = jObj.getString("error_list");
                            Toast.makeText(getContext(), "Erro AQUI "+errorMsg, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        // JSON error
                        e.printStackTrace();
                        Log.e("ERRORRRRR","Json error: " + e.getMessage());
                        Toast.makeText(getContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    // stopping swipe refresh
                    swipeRefreshLayout.setRefreshing(false);

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Lista Service Error: " + error.getMessage());

                    Toast.makeText(getContext(),"Sem Cadastro!!!", Toast.LENGTH_LONG).show();
                    // stopping swipe refresh

                    listRefriApd.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    // Posting parameters to login url
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id_cli", ""+idCli);
                    return params;
                }

            };

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

        }

        public void addArCliBD(final RefrigeradorCtrl arCli) {

        // Tag used to cancel the request
        String tag_string_req = "req_inserir_Refrigerador";

        dialog.setMessage("Salvando Refrigerador...");

        db = new SQLiteHandler(getContext());
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_INSERIR_DESCRI_AR, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Carregando dados: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    int lastIdAr = jObj.getInt("last_id_ar");

                    // Check for error node in json
                    if (!error) {

                         Log.e("INSERT REFRIGERADOR: ", "Ar inserido com sucesso");
                         arCli.setId_refri(lastIdAr);
                         db.addRefrigerador(arCli);

                        Log.e(TAG, "-----------------------2 ");
                        Toast.makeText(getContext(), "Dados Salvos com Sucesso!!", Toast.LENGTH_LONG).show();

                        LinearLayout focus = (LinearLayout) getView().findViewById(R.id.focusRefri);
                        focus.requestFocus();

                        dialog.dismiss();

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_list");
                        Toast.makeText(getContext(), "Erro AQUI "+errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Log.e("ERRORRRRR","Json error: " + e.getMessage());
                    Toast.makeText(getContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Erro ao salvar: " + error.getMessage());
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_cliente", ""+arCli.getId_cliente());
                params.put("has_control", ""+arCli.getHas_control());
                params.put("has_exaustor", ""+arCli.getHas_exaustor());
                params.put("capaci_termica", ""+arCli.getCapaci_termica());
                params.put("tencao_tomada", ""+arCli.getTencao_tomada());
                params.put("tipo_modelo", ""+arCli.getTipo_modelo());
                params.put("marca", ""+arCli.getMarca());
                params.put("temp_uso", ""+arCli.getTemp_uso());
                params.put("nivel_econo", ""+arCli.getNivel_econo());
                params.put("tamanho", ""+arCli.getTamanho());
                params.put("lotacionamento", ""+arCli.getLotacionamento());
                params.put("peso", "");
                params.put("saida_ar", "");
                params.put("has_timer", "");
                params.put("foto1", "");
                params.put("foto2", "");
                params.put("foto3", "");
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

        }

        private void salvarDadosAr(){
            //Cria o gerador do AlertDialog

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            //define o titulo
            builder.setTitle("Confirmação para Salvar Dados");
            //define a mensagem
            builder.setMessage("Você deseja Salvar as Informações?");
            //define um botão como positivo
            builder.setPositiveButton("Sim",new DialogInterface.OnClickListener()

            {
                public void onClick (DialogInterface arg0, int arg1){
                    //Toast.makeText(getApplicationContext(), "positivo=" + arg1, Toast.LENGTH_SHORT).show();

                    RefrigeradorCtrl arCli = new RefrigeradorCtrl();
                    arCli.setHas_control(isControl);
                    arCli.setHas_exaustor(isExastor);
                    arCli.setLotacionamento(editLotacionamento.getText().toString());
                    arCli.setCapaci_termica(spinnerBTU.getSelectedItemPosition()+1);
                    arCli.setTencao_tomada(spinnerTensao.getSelectedItemPosition()+1);
                    arCli.setTipo_modelo(spinnerModelo.getSelectedItemPosition()+1);
                    arCli.setMarca(spinnerMarca.getSelectedItemPosition()+1);
                    if(editTempoUso.getText().toString().isEmpty()) editTempoUso.setText("0");
                    arCli.setTemp_uso(Double.valueOf(editTempoUso.getText().toString()));
                    arCli.setNivel_econo(spinnerLvlEcon.getSelectedItemPosition()+1);
                    arCli.setTamanho(editTamanho.getText().toString());

                    arCli.setId_cliente(db.getUserDetails().getId());


                    addArCliBD(arCli);
                    alerta.dismiss();

                }
            });
            //define um botão como negativo.
            builder.setNegativeButton("Não",new DialogInterface.OnClickListener()

            {
                public void onClick (DialogInterface arg0, int arg1){
                    Toast.makeText(getContext(), "negativo=" + arg1, Toast.LENGTH_SHORT).show();
                    alerta.cancel();
                }
            });
            //cria o AlertDialog
            alerta = builder.create();
            //Exibe
            alerta.show();
        }

        @Override
        public void onRefresh() {
            listaRefrigeradores(db.getUserDetails().getId());
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            if((adapterView.getAdapter().getItemId(position) != -1) && !alerta.isShowing()) {
                positionREfrigerador = (int) adapterView.getAdapter().getItemId(position);
                refrigeradorSelected = refrigeradores.get(position);
                Toast.makeText(getContext(), "Refrigerador position: " + positionREfrigerador, Toast.LENGTH_LONG).show();
                Log.e("Click lista ", "Posição do click" + positionREfrigerador);
                Intent it = new Intent(getContext(), DetalherRefrigeActivity.class);
                startActivity(it);
                //getActivity().finish();
            }
        }

        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            return false;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "LISTA TODOS";
                case 1:
                    return "CADASTRAR";
            }
            return null;
        }
    }
}
