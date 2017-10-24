package com.example.douglas.dd_os_cliente.activitys;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.constraint.solver.widgets.ConstraintHorizontalLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.douglas.dd_os_cliente.R;
import com.example.douglas.dd_os_cliente.adapters.ListServPenCliAdapter;
import com.example.douglas.dd_os_cliente.app.AppConfig;
import com.example.douglas.dd_os_cliente.app.AppController;
import com.example.douglas.dd_os_cliente.controler.ServPendenteCtrl;
import com.example.douglas.dd_os_cliente.helper.SQLiteHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServicesTabActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_tab);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Serviços");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_services_tab, menu);
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
    public static class PlaceholderFragment extends Fragment implements  SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String TAG = PlaceholderFragment.class.getSimpleName();
        private static final String ARG_SECTION_NUMBER = "section_number";

        private FloatingActionButton fab;
        private RadioButton rbPrev;
        private RadioButton rbCorre;
        private RadioButton rbEnderMy;
        private RadioButton rbEnderOther;

        private EditText editData;
        private EditText editHora;
        private EditText editFone1;
        private EditText  editFone2;
        private EditText editDEscriProb;

        private ListView lvServPen;
        private SQLiteHandler db;
        public static int servPenPosition = 0;
        public static int servPenPositionList = 0;
        public List<ServPendenteCtrl> servPens;
        public ServPendenteCtrl servPen;
        private SwipeRefreshLayout swipeRefreshLayout;
        ListServPenCliAdapter listServApd;

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
        public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                                 Bundle savedInstanceState) {
            if(getArguments().getInt(ARG_SECTION_NUMBER) == 1){
                View rootView = inflater.inflate(R.layout.fragment_services_tab1, container, false);
                fab = (FloatingActionButton) rootView.findViewById(R.id.fabSolicitarServ);
                rbPrev = (RadioButton) rootView.findViewById(R.id.rbPreven);
                rbCorre = (RadioButton) rootView.findViewById(R.id.rbCorre);
                rbEnderMy = (RadioButton) rootView.findViewById(R.id.rbEnderMy);
                rbEnderOther = (RadioButton) rootView.findViewById(R.id.rbEnderOther);
                editData = (EditText) rootView.findViewById(R.id.editData);
                editHora = (EditText) rootView.findViewById(R.id.editHora);
                editFone1 = (EditText) rootView.findViewById(R.id.editFone1);
                editFone2 = (EditText) rootView.findViewById(R.id.editFone2);
                editDEscriProb = (EditText) rootView.findViewById(R.id.editDescriProb);

                final LinearLayout layoutEnder = (LinearLayout) rootView.findViewById(R.id.layoutEnder);

                rbCorre.requestFocus();

                fab.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view, "Solicitando Serviço", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
                rbPrev.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rbCorre.setChecked(false);
                        rbPrev.setChecked(true);
                    }
                });
                rbCorre.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rbCorre.setChecked(true);
                        rbPrev.setChecked(false);
                    }
                });
                rbEnderMy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rbEnderMy.setChecked(true);
                        rbEnderOther.setChecked(false);
                        layoutEnder.removeAllViews();
                    }
                });
                rbEnderOther.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rbEnderOther.setChecked(true);
                        rbEnderMy.setChecked(false);

                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addServCli( enviar dados dos campos do XML pelos parametros);
                    }
                });
                    if(layoutEnder.getChildAt(1) == null) {
                        EditText etEnder = new EditText(getContext());
                        etEnder.setWidth(container.getWidth());
                        etEnder.setHint("Digite a Rua, e Numero da Casa");
                        etEnder.setTextSize(13);

                        layoutEnder.addView(etEnder, 0);

                        EditText etBairo = new EditText(getContext());
                        etBairo.setWidth(container.getWidth());
                        etBairo.setHint("Digite o Nome do Bairro");
                        etBairo.setTextSize(13);
                        layoutEnder.addView(etBairo, 1);

                        EditText etCep = new EditText(getContext());
                        etCep.setWidth(container.getWidth());
                        etCep.setHint("Digite o CEP");
                        etCep.setTextSize(13);
                        layoutEnder.addView(etCep, 2);
                    }
                    }
                });



                return rootView;
            }else{
                View rootView = inflater.inflate(R.layout.fragment_services_tab2, container, false);

//                swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
//                lvServPen = (ListView) rootView.findViewById(R.id.lvServiceSilicit);
//
//                db = new SQLiteHandler(getContext());
//
//                servPens = new ArrayList<ServPendenteCtrl>();
//
//                listServApd = new ListServPenCliAdapter(getContext(), servPens);
//                lvServPen.setAdapter(listServApd);
//
//                Log.e("BOTAO","ENTROU AQUI: "+ servPens.size());
//
//                swipeRefreshLayout.setOnRefreshListener(this);
//
//                /**
//                 * Showing Swipe Refresh animation on activity create
//                 * As animation won't start on onCreate, post runnable is used
//                 */
//                //listaServPen();
//                swipeRefreshLayout.post(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                swipeRefreshLayout.setRefreshing(true);
//                                                listaServPen("",db.getUserDetails().getId());
//                                            }
//                                        }
//                );
//
//                lvServPen.setOnItemClickListener(this);
                return rootView;
            }
        }

        private void listaServPen(final String status, final int cod_cli) {

            // showing refresh animation before making http call
            swipeRefreshLayout.setRefreshing(true);

            // Tag used to cancel the request
            String tag_string_req = "req_listaServPen";
            //final List<ServPendenteCtrl> listSerPen =null;



            StringRequest strReq = new StringRequest(Request.Method.POST,
                    AppConfig.URL_SERV_PEN_CLI, new Response.Listener<String>() {

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
                            servPens.clear();
                            for (int i = 0; i < serv_penArray.length(); i++) {
                                try {
                                    JSONObject serv_penObj = new JSONObject(serv_penArray.get(i).toString());

                                    Log.e("Serviço ENCONTRADO: ", "Entrou no for");

                                    ServPendenteCtrl objetoServPen = new ServPendenteCtrl();

                                    objetoServPen.setId_serv_pen(serv_penObj.getInt("uid"));
                                    objetoServPen.setLatitude(Double.valueOf(serv_penObj.getString("latitude")));
                                    objetoServPen.setLongitude(Double.valueOf(serv_penObj.getString("longitude")));
                                    objetoServPen.setCliente_id(Integer.valueOf(serv_penObj.getString("cliente")));
                                    objetoServPen.setLotacionamento(serv_penObj.getString("lotacionamento"));
                                    objetoServPen.setEnder(serv_penObj.getString("ender"));
                                    objetoServPen.setComplemento(serv_penObj.getString("complemento"));
                                    objetoServPen.setCep(serv_penObj.getString("cep"));
                                    objetoServPen.setData_serv(serv_penObj.getString("data_serv"));
                                    objetoServPen.setHora_serv(serv_penObj.getString("hora_serv"));
                                    objetoServPen.setDescri_cli_problem(serv_penObj.getString("descriCliProblem"));
                                    objetoServPen.setDescri_tecni_problem(serv_penObj.getString("descriTecniProblem"));
                                    objetoServPen.setDescri_cli_refrigera(serv_penObj.getString("descriCliRefrigera"));
                                    objetoServPen.setStatus_serv(serv_penObj.getString("statusServ"));
                                    objetoServPen.setNomeCli(serv_penObj.getString("nome"));
                                    objetoServPen.setTipoCli(serv_penObj.getString("tipo"));
                                    objetoServPen.setFone1(serv_penObj.getString("fone1"));
                                    objetoServPen.setFone2(serv_penObj.getString("fone2"));
                                    objetoServPen.setId_refriCli(serv_penObj.getInt("id_refriCli"));

                                    servPens.add(objetoServPen);

                                    Log.e("LISTA",""+servPens.size());
                                    Log.e("Dados sqlite: ", ""+db.getAllMyServPen().size());
                                } catch (JSONException e) {
                                    Log.e(TAG, "JSON erro ao consultar dados: " + e.getMessage());
                                }
                            }
                            listServApd.notifyDataSetChanged();


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
                    ServPendenteCtrl objetoServPen = new ServPendenteCtrl();
                    objetoServPen.setTipoCli(":SEM SERVIÇOS PENDENTES NO MOMENTO");
                    objetoServPen.setData_serv("");
                    objetoServPen.setHora_serv("");
                    objetoServPen.setEnder("");
                    objetoServPen.setNomeCli("  ");
                    objetoServPen.setId_serv_pen(-1);

                    servPens.add(objetoServPen);
                    // stopping swipe refresh

                    listServApd.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    // Posting parameters to login url
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("status", status);
                    params.put("cliente", ""+cod_cli);
                    return params;
                }

            };

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

        }

        private void addServCli(final Double lati, final Double longi, final int cli, final String ender, final String comple, final String data,
                                final String hora, final String descriCliPro, final String status, final String tipoManu, final String codRefriCli){


                // Tag used to cancel the request
                String tag_string_req = "req_addServPen";

                StringRequest strReq = new StringRequest(Request.Method.POST,
                        AppConfig.URL_INSERIR_SERV_PEN_CLI, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Inserindo dados: " + response.toString());

                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");

                            // Check for error node in json
                            if (!error) {

                                Log.e(TAG," SERVIÇO INSERIDO COM SUCESSO ");


                            } else {
                                // Error in login. Get the error message
                                String errorMsg = jObj.getString("error_msg");
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
                        Log.e(TAG, "ERROR AO SALVAR SERVIÇO: " + error.getMessage());

                        //hideDialog();
                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() {
                        // Posting parameters to login url
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("lati", ""+lati);
                        params.put("longi", ""+longi);
                        params.put("cli", ""+cli);
                        params.put("ender", ender);
                        params.put("comple", comple);
                        params.put("data", data);
                        params.put("hora", hora);
                        params.put("descriCliPro", descriCliPro);
                        params.put("status", status);
                        params.put("tipoManu", tipoManu);
                        params.put("codRefriCli", codRefriCli);
                        return params;
                    }

                };

                // Adding request to request queue
                AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


        }

        @Override
        public void onRefresh() {

        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

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
                    return "Solicitar";
                case 1:
                    return "Pendentes";
            }
            return null;
        }
    }
}
