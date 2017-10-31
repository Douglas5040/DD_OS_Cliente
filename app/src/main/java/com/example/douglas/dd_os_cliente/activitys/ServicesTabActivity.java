package com.example.douglas.dd_os_cliente.activitys;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.constraint.solver.widgets.ConstraintHorizontalLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompatBase;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
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
import com.example.douglas.dd_os_cliente.controler.RefrigeradorCtrl;
import com.example.douglas.dd_os_cliente.controler.ServPendenteCtrl;
import com.example.douglas.dd_os_cliente.controler.UserClienteCtrl;
import com.example.douglas.dd_os_cliente.helper.SQLiteHandler;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.drakeet.materialdialog.MaterialDialog;

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
    public static class PlaceholderFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String TAG = PlaceholderFragment.class.getSimpleName();
        private static final int REQUEST_PERMISSIONS_CODE = 10;
        private static final String ARG_SECTION_NUMBER = "section_number";
        GoogleApiClient mGoogleApiClient;
        MaterialDialog mMaterialDialog;

        private Handler handler = new Handler();

        private FloatingActionButton fab;
        private RadioButton rbPrev;
        private RadioButton rbCorre;
        private RadioButton rbEnderMy;
        private RadioButton rbEnderOther;

        private Double locationLati = null;
        private Double locationLong = null;

        private EditText editData;
        private EditText editHora;
        private EditText editFone1;
        private EditText editFone2;
        private EditText editDEscriProb;

        private ListView lvServPen;
        public static int servPenPosition = 0;
        public static int servPenPositionList = 0;
        public List<ServPendenteCtrl> servPens;
        public ServPendenteCtrl servPen;
        private SwipeRefreshLayout swipeRefreshLayout;
        ListServPenCliAdapter listServApd;
        private ProgressDialog pDialog;

        private SQLiteHandler db;

        //variaveis de endereço service
        private String rua = null;
        private String bairro = null;
        private String cidade = null;
        private String estado = null;
        private double latidude = 0.0;
        private double longitude = 0.0;
        private String link_mapa = null;

        private EditText etCep;
        private EditText etEnder;
        private EditText etNum;
        private EditText etBairo;
        private EditText etComplemento;

        private Spinner spinnerArCli;

        private UserClienteCtrl clienteCtrl;
        private RefrigeradorCtrl refrigeradorCli;
        private List <RefrigeradorCtrl> listRefrigeradoresCli;

        String ender;
        String comple;
        String data;
        int tipoManu = 2;
        int codRefriCli = 0;


        private AlertDialog alerta;

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


            if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                final View rootView = inflater.inflate(R.layout.fragment_services_tab1, container, false);
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

                spinnerArCli = (Spinner) rootView.findViewById(R.id.spinnerRefri);



                try {
                    listRefrigeradoresCli = db.getAllArCli();
                }catch (Exception e){Log.e(TAG,"Error: "+e);}
                if(listRefrigeradoresCli != null){
                    for(int x = 0; x < listRefrigeradoresCli.size(); x++) {
                        List<String> refrigeraCli = new ArrayList<String>();
                        refrigeraCli.add(db.getNomeMaca(listRefrigeradoresCli.get(x).getMarca()) + " / " +
                                db.getNomeBTU(listRefrigeradoresCli.get(x).getCapaci_termica()) + " BTUs - " +
                                listRefrigeradoresCli.get(x).getLotacionamento());

                        ArrayAdapter<String> arrayAdapterArCli = new ArrayAdapter<String>(getContext(), R.layout.simple_spinner_item, refrigeraCli);
                        //arrayAdapter1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                        spinnerArCli.setAdapter(arrayAdapterArCli);
                    }
                    spinnerArCli.setSelection(0);
                }else{

                    ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getContext(),R.layout.simple_spinner_item, new String[]{"Nenhum Refrigerador Cadastrado"});
                    //arrayAdapter1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                    spinnerArCli.setAdapter(arrayAdapter1);
                    spinnerArCli.setSelection(0);
                }

                etCep = new EditText(getContext());
                etEnder = new EditText(getContext());
                etNum = new EditText(getContext());
                etBairo = new EditText(getContext());
                etComplemento = new EditText(getContext());

                db = new SQLiteHandler(getContext());
                clienteCtrl = db.getUserDetails();

                editFone1.setText(clienteCtrl.getFone1());
                editFone2.setText(clienteCtrl.getFone2());

                final LinearLayout layoutEnder = (LinearLayout) rootView.findViewById(R.id.layoutEnder);

                // Progress dialog
                pDialog = new ProgressDialog(getContext());
                pDialog.setCancelable(false);

                rbCorre.requestFocus();

                fab.setOnClickListener(new View.OnClickListener() {
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
                        tipoManu = 1;

                    }
                });
                rbCorre.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rbCorre.setChecked(true);
                        rbPrev.setChecked(false);
                        tipoManu = 2;
                    }
                });
                rbEnderMy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rbEnderMy.setChecked(true);
                        rbEnderOther.setChecked(false);
                        layoutEnder.removeAllViews();

                        callConnetion();

                    }
                });

                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Log.e(TAG,"Entrou fab");
                    if((rbPrev.isChecked() || rbCorre.isChecked()) && !editData.getText().toString().isEmpty() && !editHora.getText().toString().isEmpty() &&
                            (spinnerArCli.getItemAtPosition(0).toString().equals("Nenhum Refrigerador Cadastrado")) && (rbEnderOther.isChecked() || rbEnderMy.isChecked()) &&
                            !editDEscriProb.getText().toString().isEmpty()) {
                        if (locationLati == null && locationLong == null) {
                            callConnetion();
                        }
                        if (rbEnderOther.isChecked()) {
                            ender = etEnder.getText().toString() + ", " + etNum.getText().toString() +
                                    ", " + etBairo.getText().toString();
                            comple = etComplemento.getText().toString();
                            locationLati = null;
                            locationLong = null;
                        } else {
                            ender = clienteCtrl.getEnder() + ", " + clienteCtrl.getBairro();
                            comple = etComplemento.getText().toString();

                        }
                        String[] datas;
                        datas = editData.getText().toString().split("/");
                        if (datas.length > 1) {
                            data = datas[2] + "-" + datas[1] + "-" + datas[0];
                        } else {
                            data = editData.getText().toString().substring(4, 8) + "-"
                                    + editData.getText().toString().substring(2, 3) + "-"
                                    + editData.getText().toString().substring(0, 1);
                        }

                        if (listRefrigeradoresCli == null) {
                            confirmCarRefrigerador();
                        } else {
                            codRefriCli = listRefrigeradoresCli.get(spinnerArCli.getSelectedItemPosition() - 1).getId_refri();
                            addServCli(locationLati, locationLong, clienteCtrl.getId(), ender, comple, data, editHora.getText().toString(),
                                    editDEscriProb.getText().toString(), "Solicitando", tipoManu, codRefriCli);
                        }
                    }else{
                        Toast.makeText(getContext(),"Preencha Todos os Dados!!!",Toast.LENGTH_LONG).show();
                    }
                    }
                });

                rbEnderOther.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rbEnderOther.setChecked(true);
                        rbEnderMy.setChecked(false);
                        if (layoutEnder.getChildAt(1) == null) {
                            int widthContext = container.getWidth();

                            etCep.setWidth(widthContext);
                            etCep.setHint("Digite o CEP (Busca Automatica)");
                            etCep.setTextSize(13);
                            etCep.setInputType(4);
                            layoutEnder.addView(etCep, 0);

                            LinearLayout layoutHorion = new LinearLayout(getContext());
                            layoutHorion.setOrientation(LinearLayout.HORIZONTAL);
                            layoutEnder.addView(layoutHorion, 2);


                            etEnder.setWidth(widthContext-Integer.parseInt(String.valueOf(widthContext*0.25).substring(0,
                                                                            String.valueOf(widthContext*0.25).length()-2)));
                            etEnder.setHint("Digite a Rua, e Numero da Casa");
                            etEnder.setTextSize(13);
                            layoutHorion.addView(etEnder, 0);


                            etNum.setWidth(Integer.parseInt(String.valueOf(widthContext*0.25).substring(0,
                                                            String.valueOf(widthContext*0.25).length()-2)));
                            etNum.setHint("Numero");
                            etNum.setTextSize(13);
                            layoutHorion.addView(etNum, 1);


                            etBairo.setWidth(container.getWidth());
                            etBairo.setHint("Digite o Nome do Bairro");
                            etBairo.setTextSize(13);
                            layoutEnder.addView(etBairo, 1);


                            etComplemento.setWidth(container.getWidth());
                            etComplemento.setHint("Digite uma Referencia");
                            etComplemento.setTextSize(13);
                            layoutEnder.addView(etBairo, 3);

                            etCep.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                @RequiresApi(api = Build.VERSION_CODES.O)
                                @Override
                                public void onFocusChange(View view, boolean b) {
                                    if( !etCep.getText().toString().equals("") || !etCep.isEnabled()){
                                        Toast.makeText(getContext(),"Teste entrou passou AQUI",Toast.LENGTH_LONG).show();
                                        if(etBairo.getText().toString().equals("") && etEnder.getText().toString().equals(""))
                                            getEnderCep(etCep.getText().toString());

                                        etNum.setEditableFactory(new Editable.Factory());
                                        etNum.setContextClickable(true);

                                    }
                                    if(etCep.getText().toString().equals("") && !etCep.isFocused()){
                                        Toast.makeText(getContext(),"Informe o CEP",Toast.LENGTH_LONG).show();
                                        etCep.setFocusable(true);
                                        etCep.setSelected(true);
                                    }
                                }
                            });
                        }

                    }
                });


                return rootView;
            } else {
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

        private void getEnderCep(final String cep) {

            // Tag used to cancel the request
            String tag_string_req = "req_cep";
            //final List<ServPendenteCtrl> listSerPen =null;


            pDialog.setMessage("Buscando Endereço por CEP ...");
            showDialog();

            StringRequest strReq = new StringRequest(Request.Method.GET,
                    AppConfig.URL_SERVICE_CEP_VIACEP+cep+"/json/", new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Carregando dados: " + response.toString());


                    try {
                        JSONObject jObj = new JSONObject(response);

                            Log.e("TESTE", "ENTROU CEP service: " + jObj.toString());
                                try {

                                    rua = jObj.getString("logradouro");
                                    bairro = jObj.getString("bairro");
                                    cidade = jObj.getString("localidade");
                                    estado = jObj.getString("uf");

                                    etBairo.setText(bairro);
                                    etEnder.setText(rua);
                                    etCep.setFocusable(false);
                                    etCep.setSelected(false);


                                    hideDialog();

                                 } catch (JSONException e) {
                                    Log.e(TAG, "JSON erro ao consultar dados CEP: " + e.getMessage());
                                    Toast.makeText(getContext(),"CEP não Encontrado: " + e.getMessage(),Toast.LENGTH_LONG).show();
                                    hideDialog();
                                }

                    } catch (JSONException e) {
                        // JSON error
                        e.printStackTrace();
                        Log.e("ERRORRRRR", "Json error: " + e.getMessage());
                        Toast.makeText(getContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        hideDialog();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Erro Connect: " + error.getMessage());
                    hideDialog();
                }
            });

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

        }


        private void confirmCarRefrigerador(){
            //Cria o gerador do AlertDialog
            db = new SQLiteHandler(getContext());
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            //define o titulo
            builder.setTitle("Confirmação de Serviço");
            //define a mensagem
            builder.setMessage("Você deseja Realizar este Serviço?");
            //define um botão como positivo
            builder.setPositiveButton("OK",new DialogInterface.OnClickListener()

            {
                public void onClick (DialogInterface arg0, int arg1){

                    hideDialog();
                    Intent i = new Intent(getContext(), RefrigeradorActivity.class);  //your class
                    startActivity(i);
                }
            });
            //cria o AlertDialog
            alerta = builder.create();
            //Exibe
            alerta.show();
        }


        private void getEnderCep2( final String url) throws IOException {
            // Tag used to cancel the request
            String tag_string_req = "req_cep";
//            pDialog.setMessage("Buscando Endereço por CEP ...");
//            showDialog();

            URL u = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) u.openConnection();
            //configura a requesição para "get"
            connection.setRequestProperty("Request-Method","GET");
            connection.setDoInput(false);
            connection.setDoOutput(true);
            connection.connect();
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;

            connection.disconnect();
            while ((line = reader.readLine()) != null)           // Leia linha a linha
            {
                sb.append(line + "\n");
            }

            String resString = sb.toString();                    // Resultado
            Log.e("TESTE", "ENTROU CEP service: " +resString);
            in.close();

            try {
                        JSONObject jObj = new JSONObject(resString);
                        boolean status = jObj.getBoolean("status");

                        // Check for error node in json
                        if (status) {
                            JSONArray serv_cepArray = jObj.getJSONArray("result");

                            Log.e("TESTE", "ENTROU CEP service: " + serv_cepArray.length());
                            try {

                                Log.e("Serviço ENCONTRADO: ", "Entrou no for");
                                rua = serv_cepArray.getJSONObject(1).getString("logradouro");
                                bairro = serv_cepArray.getJSONObject(2).getString("bairro");
                                cidade = serv_cepArray.getJSONObject(3).getString("cidade");
                                estado = serv_cepArray.getJSONObject(4).getString("uf");
                                link_mapa = serv_cepArray.getJSONObject(1).getString("link_mapa");
                                latidude = serv_cepArray.getJSONObject(6).getDouble("lat");
                                longitude = serv_cepArray.getJSONObject(9).getDouble("lng");

                                hideDialog();

                            } catch (JSONException e) {
                                Log.e(TAG, "JSON erro ao consultar dados CEP: " + e.getMessage());
                                //hideDialog();
                            }
                        } else {
                            // Error in login. Get the error message
                            String errorMsg = jObj.getString("message");
                            Log.e(TAG, "JSON erro ao consultar dados CEP: " + errorMsg);
                            Toast.makeText(getContext(), "Erro AQUI: " + jObj.getString( "return" )+" "+errorMsg, Toast.LENGTH_LONG).show();
                            hideDialog();
                        }
                    } catch (JSONException e) {
                        // JSON error
                        e.printStackTrace();
                        Log.e("ERRORRRRR", "Json error: " + e.getMessage());
                        Toast.makeText(getContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        hideDialog();
                    }


        }

        private void showDialog() {
            if (!pDialog.isShowing())
                pDialog.show();
        }

        private void hideDialog() {
            if (pDialog.isShowing())
                pDialog.dismiss();
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

                            Log.e("TESTE", "ENTROU: " + serv_penArray.length());
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

                                    Log.e("LISTA", "" + servPens.size());
                                    Log.e("Dados sqlite: ", "" + db.getAllMyServPen().size());
                                } catch (JSONException e) {
                                    Log.e(TAG, "JSON erro ao consultar dados: " + e.getMessage());
                                }
                            }
                            listServApd.notifyDataSetChanged();


                        } else {
                            // Error in login. Get the error message
                            String errorMsg = jObj.getString("error_list");
                            Toast.makeText(getContext(), "Erro AQUI " + errorMsg, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        // JSON error
                        e.printStackTrace();
                        Log.e("ERRORRRRR", "Json error: " + e.getMessage());
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
                    params.put("cliente", "" + cod_cli);
                    return params;
                }

            };

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

        }

        private void addServCli(final Double lati, final Double longi, final int cli, final String ender, final String comple, final String data,
                                final String hora, final String descriCliPro, final String status, final int tipoManu, final int codRefriCli) {


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

                            Log.e(TAG, " SERVIÇO INSERIDO COM SUCESSO ");


                        } else {
                            // Error in login. Get the error message
                            String errorMsg = jObj.getString("error_msg");
                            Toast.makeText(getContext(), "Erro AQUI " + errorMsg, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        // JSON error
                        e.printStackTrace();
                        Log.e("ERRORRRRR", "Json error: " + e.getMessage());
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
                    params.put("lati", "" + lati);
                    params.put("longi", "" + longi);
                    params.put("cli", "" + cli);
                    params.put("ender", ender);
                    params.put("comple", comple);
                    params.put("data", data);
                    params.put("hora", hora);
                    params.put("descriCliPro", descriCliPro);
                    params.put("status", status);
                    params.put("tipoManu", ""+tipoManu);
                    params.put("codRefriCli", ""+codRefriCli);
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

        private synchronized void callConnetion() {
            Log.e(TAG, "Entrou AQUI callConnetion----- ");
            if(mGoogleApiClient != null)
            if(mGoogleApiClient.isConnected() || mGoogleApiClient.isConnecting()) mGoogleApiClient.reconnect();
            mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                    .addOnConnectionFailedListener(this)
                    .addConnectionCallbacks(this)
                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClient.connect();
        }
        private void callDialog( String message, final String[] permissions ){
            mMaterialDialog = new MaterialDialog(getContext())
                    .setTitle("Permission")
                    .setMessage(message)
                    .setPositiveButton("Ok", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_PERMISSIONS_CODE);
                            mMaterialDialog.dismiss();
                        }
                    })
                    .setNegativeButton("Cancel", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mMaterialDialog.dismiss();
                        }
                    });
            mMaterialDialog.show();
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
            Log.i(TAG, "test");
            switch( requestCode ){
                case REQUEST_PERMISSIONS_CODE:
                    for( int i = 0; i < permissions.length; i++ ){

                        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
                            if( ActivityCompat.shouldShowRequestPermissionRationale( getActivity(), Manifest.permission.ACCESS_FINE_LOCATION ) ){
                                callDialog( "É preciso a permission ACCESS_FINE_LOCATION para apresentação dos eventos locais.", new String[]{Manifest.permission.ACCESS_FINE_LOCATION} );
                            }
                        }else{
                            Location l = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                            if(l != null){
                                locationLati = l.getLatitude();
                                locationLong = l.getLongitude();
                                Log.e(TAG, "Latitude: "+l.getLatitude());
                                Log.e(TAG, "Longitude: "+l.getLongitude());
                            }else Log.e(TAG,"Location é NULL");

                        }

                    }
            }
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        //aqui é pegado as cordenadas do gps
        @Override
        public void onConnected(Bundle bundle) {

            Log.e(TAG, "Entrou AQUI onConnected----- ");


            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
                if( ActivityCompat.shouldShowRequestPermissionRationale( getActivity(), Manifest.permission.ACCESS_FINE_LOCATION ) ){
                    callDialog( "É preciso a permission ACCESS_FINE_LOCATION para apresentação dos eventos locais.", new String[]{Manifest.permission.ACCESS_FINE_LOCATION} );
                }
                else{
                    ActivityCompat.requestPermissions( getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSIONS_CODE );
                }
            }else{
                Location l = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                Log.e(TAG,"enter here -----------------");
                if(l != null){
                    locationLati = l.getLatitude();
                    locationLong = l.getLongitude();

                    Log.e(TAG, "Latitude onConnected: "+l.getLatitude());
                    Log.e(TAG, "Longitude onConnected: "+l.getLongitude());
                    l.reset();
                    //l.
                    mGoogleApiClient.disconnect();
                }else Log.e(TAG,"Location é NULL");
            }

        }

        @Override
        public void onConnectionSuspended(int i) {
            Log.i(TAG,"onConnectionSuspended: " + i);
        }

        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {
            Log.i(TAG,"ConnectionResult: " + connectionResult);

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
                    return "Pendentes";
                case 1:
                    return "Solicitar";
            }
            return null;

        }
    }
}
