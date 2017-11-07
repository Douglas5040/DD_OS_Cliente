package com.example.douglas.dd_os_cliente.activitys;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.douglas.dd_os_cliente.R;
import com.example.douglas.dd_os_cliente.app.AppConfig;
import com.example.douglas.dd_os_cliente.app.AppController;
import com.example.douglas.dd_os_cliente.controler.PecsCtrl;
import com.example.douglas.dd_os_cliente.controler.RefrigeradorCtrl;
import com.example.douglas.dd_os_cliente.controler.ServPendenteCtrl;
import com.example.douglas.dd_os_cliente.controler.ServicesCtrl;
import com.example.douglas.dd_os_cliente.controler.UserClienteCtrl;
import com.example.douglas.dd_os_cliente.helper.SQLiteHandler;
import com.example.douglas.dd_os_cliente.helper.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private Button btnLogin;
    private Button btnLinkToRegister;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    private static final int tempoRepetir = 5*1000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);




        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                // Check for empty data in the form
                if (!email.isEmpty() && !password.isEmpty()) {
                    // login user
                    checkLogin(email, password);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Por favor entre com os dados!", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });

        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });

    }


    private void checkLogin(final String login, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Entrando ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    Log.e(TAG, "Login entrou ----------------: ");
                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);
                        UserClienteCtrl userCli = new UserClienteCtrl();
                        // Now store the user in SQLite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");

                        userCli.setId(user.getInt("id_cli"));
                        userCli.setName(user.getString("name"));
                        userCli.setCpf_cnpj(user.getInt("cpf_cnpj"));
                        userCli.setEmail(user.getString("email"));
                        userCli.setEnder(user.getString("ender"));
                        userCli.setBairro(user.getString("bairro"));
                        userCli.setPoint_ref(user.getString("ponto_referencia"));
                        userCli.setCep(user.getInt("cep"));
                        userCli.setFone1(user.getString("celular"));
                        userCli.setFone2(user.getString("fone_fixo"));
                        userCli.setTipo_cad(user.getString("tipo"));
                        userCli.setCreated_at(user.getString("created_at"));
                        userCli.setUpdate_at(user.getString("updated_at"));


                        // Inserting row in users table
                        db.deleteUsers();
                        db.addUser(userCli);

                        pDialog.setMessage("Sincronizando Dados...");
                        listaPecs();
                        listaMarcas();
                        listaModelos();
                        listaBtus();
                        listaNvEcon();
                        listaTencao();
                        listaServPen("",userCli.getId());
                        listaRefrigeradores(userCli.getId(),"CADASTRADO");

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Log.e(TAG, "Login erro: "+errorMsg);

                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                        hideDialog();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Log.e(TAG, "Login erro: "+e);
                    Log.e(TAG, "Login erro: "+e.getMessage());
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    hideDialog();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Erro ao realizar login", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", login);
                params.put("cpf_cnpj", login);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }



    private void listaServPen(final String status, final int cod_cli) {

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

                    db.deleteAllMyServPen();
                    // Check for error node in json
                    if (!error) {
                        JSONArray serv_penArray = jObj.getJSONArray("data");

                        Log.e("TESTE", "ENTROU: " + serv_penArray.length());
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

                                if(!objetoServPen.getStatus_serv().equals("Cancelado"))db.addMyServPen(objetoServPen);
                                Log.e("Dados sqlite: ", "" + db.getAllMyServPen().size());
                            } catch (JSONException e) {
                                Log.e(TAG, "JSON erro ao consultar dados: " + e.getMessage());
                            }
                        }


                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_list");
                        Toast.makeText(getApplicationContext(), "Menssagem " + errorMsg, Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Log.e("ERRORRRRR", "Json error: " + e.getMessage());
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Lista Service Error: " + error.getMessage());
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

    private void listaPecs() {

        // Tag used to cancel the request
        String tag_string_req = "req_listaPecs";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_GET_PECS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Carregando dados: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {

                        JSONArray pecsArray = jObj.getJSONArray("data");

                        Log.e("TESTE","ENTROU: "+pecsArray.length());
                        db = new SQLiteHandler(getApplicationContext());
                        db.deletePecs();
                        for (int i = 0; i < pecsArray.length(); i++) {
                            try {
                                JSONObject serv_penObj = new JSONObject(pecsArray.get(i).toString());

                                Log.e("Serviço ENCONTRADO: ", "Entrou no for");

                                PecsCtrl objetoPecs = new PecsCtrl();

                                objetoPecs.setId_pc(serv_penObj.getInt("id_pc"));
                                objetoPecs.setNome(serv_penObj.getString("nome"));
                                objetoPecs.setModelo(serv_penObj.getString("modelo"));
                                objetoPecs.setMarca(serv_penObj.getString("marca"));

                                db.addPecs(objetoPecs);

                            } catch (JSONException e) {
                                Log.e(TAG, "JSON erro ao consultar dados: " + e.getMessage());
                            }
                        }


                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_list");
                        Toast.makeText(getApplicationContext(), "Erro AQUI "+errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Log.e("ERRORRRRR","Json error: " + e.getMessage());
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Lista Service Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Sem Peças no Banco", Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    private void listaMarcas() {

        // Tag used to cancel the request
        String tag_string_req = "req_listMARCAS";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_GET_MARCA_AR, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Carregando dados: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {

                        JSONArray marcasArray = jObj.getJSONArray("data");

                        Log.e("TESTE","ENTROU MARCAS: "+marcasArray.length());
                        db = new SQLiteHandler(getApplicationContext());
                        db.deleteMarcas();
                        for (int i = 0; i < marcasArray.length(); i++) {
                            try {
                                JSONObject marcaObj = new JSONObject(marcasArray.get(i).toString());

                                Log.e("Maca encontrada: ", "Entrou no for da Marca");

                                db.addMarca(marcaObj.getInt("id_marca"), marcaObj.getString("marca"));

                            } catch (JSONException e) {
                                Log.e(TAG, "JSON erro ao consultar dados: " + e.getMessage());
                            }
                        }


                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_list");
                        Toast.makeText(getApplicationContext(), "Erro AQUI "+errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Log.e("ERRORRRRR","Json error: " + e.getMessage());
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Lista Service Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Sem Marcas no Banco", Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    private void listaModelos() {

        // Tag used to cancel the request
        String tag_string_req = "req_listModelo";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_GET_MODELO_AR, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Carregando dados: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {

                        JSONArray modeloArray = jObj.getJSONArray("data");

                        Log.e("TESTE","ENTROU: "+modeloArray.length());
                        db = new SQLiteHandler(getApplicationContext());
                        db.deleteModelos();
                        for (int i = 0; i < modeloArray.length(); i++) {
                            try {
                                JSONObject modeloObj = new JSONObject(modeloArray.get(i).toString());

                                Log.e("Serviço ENCONTRADO: ", "Entrou no for");

                                db.addModelo(modeloObj.getInt("id_modelo"), modeloObj.getString("modelo"));

                            } catch (JSONException e) {
                                Log.e(TAG, "JSON erro ao consultar dados: " + e.getMessage());
                            }
                        }


                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_list");
                        Toast.makeText(getApplicationContext(), "Erro AQUI "+errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Log.e("ERRORRRRR","Json error: " + e.getMessage());
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Lista Service Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Sem Modelos no Banco", Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    private void listaBtus() {

        // Tag used to cancel the request
        String tag_string_req = "req_listBTUs";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_GET_BTUS_AR, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Carregando dados: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {

                        JSONArray btusArray = jObj.getJSONArray("data");

                        Log.e("TESTE","ENTROU: "+btusArray.length());
                        db = new SQLiteHandler(getApplicationContext());
                        db.deleteBTU();
                        for (int i = 0; i < btusArray.length(); i++) {
                            try {
                                JSONObject btuObj = new JSONObject(btusArray.get(i).toString());

                                Log.e("Serviço ENCONTRADO: ", "Entrou no for");

                                db.addBtu(btuObj.getInt("id_capaci_termi"), btuObj.getString("capaci_termica"));

                            } catch (JSONException e) {
                                Log.e(TAG, "JSON erro ao consultar dados: " + e.getMessage());
                            }
                        }


                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_list");
                        Toast.makeText(getApplicationContext(), "Erro AQUI "+errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Log.e("ERRORRRRR","Json error: " + e.getMessage());
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Lista Service Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Sem BTUs no Banco", Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    private void listaNvEcon() {

        // Tag used to cancel the request
        String tag_string_req = "req_listNvEcon";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_GET_NV_ECON_AR, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Carregando dados: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {

                        JSONArray nvEconArray = jObj.getJSONArray("data");

                        Log.e("TESTE","ENTROU: "+nvEconArray.length());
                        db = new SQLiteHandler(getApplicationContext());
                        db.deleteNvEcon();
                        for (int i = 0; i < nvEconArray.length(); i++) {
                            try {
                                JSONObject nvEconObj = new JSONObject(nvEconArray.get(i).toString());

                                Log.e("Serviço ENCONTRADO: ", "Entrou no for");

                                db.addNvEcon(nvEconObj.getInt("id_nv_econ"), nvEconObj.getString("nivel_econo"));

                            } catch (JSONException e) {
                                Log.e(TAG, "JSON erro ao consultar dados: " + e.getMessage());
                            }
                        }


                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_list");
                        Toast.makeText(getApplicationContext(), "Erro AQUI "+errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Log.e("ERRORRRRR","Json error: " + e.getMessage());
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Lista Service Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Sem Marcas no Banco", Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    private void listaTencao() {

        // Tag used to cancel the request
        String tag_string_req = "req_listTencao";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_GET_TENCAO_AR, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Carregando dados: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {

                        JSONArray tencaoArray = jObj.getJSONArray("data");

                        Log.e("TESTE","ENTROU: "+tencaoArray.length());
                        db = new SQLiteHandler(getApplicationContext());
                        db.deleteTencao();
                        for (int i = 0; i < tencaoArray.length(); i++) {
                            try {
                                JSONObject tencaoObj = new JSONObject(tencaoArray.get(i).toString());

                                Log.e("Serviço ENCONTRADO: ", "Entrou no for");

                                db.addTencaoTomada(tencaoObj.getInt("id_tensao"), tencaoObj.getString("tencao_tomada"));

                            } catch (JSONException e) {
                                Log.e(TAG, "JSON erro ao consultar dados: " + e.getMessage());
                            }
                        }
                        // Launch main activity
                        //System.out.println("teste......");
                        Intent intent = new Intent(LoginActivity.this,
                                MainActivity.class);
                        startActivity(intent);
                        //System.out.println("teste111......");
                        finish();

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_list");
                        Toast.makeText(getApplicationContext(), "Erro AQUI "+errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Log.e("ERRORRRRR","Json error: " + e.getMessage());
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Lista Service Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Sem Marcas no Banco", Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }


    private void listaRefrigeradores(final int idCli, final String status) {

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
                    db.deleteReifigera();
                    if (!error) {

                        JSONArray serv_penArray = jObj.getJSONArray("data");
                        Log.e("TESTE","ENTROU: "+serv_penArray.length());
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
                                    objetoRefrigerador.setFoto1(null);
                                    objetoRefrigerador.setFoto2(null);
                                    objetoRefrigerador.setFoto3(null);
                                objetoRefrigerador.setMarca(refrigeraObj.getInt("marca"));
                                objetoRefrigerador.setTipo_modelo(refrigeraObj.getInt("tipo_modelo"));
                                objetoRefrigerador.setNivel_econo(refrigeraObj.getInt("nivel_econo"));
                                objetoRefrigerador.setTencao_tomada(refrigeraObj.getInt("tencao_tomada"));
                                objetoRefrigerador.setId_cliente(refrigeraObj.getInt("id_cliente"));
                                objetoRefrigerador.setTemp_uso(refrigeraObj.getInt("temp_uso"));
                                objetoRefrigerador.setTamanho(refrigeraObj.getString("tamanho"));
                                objetoRefrigerador.setSaida_ar(refrigeraObj.getString("saida_ar"));

                                db.addRefrigerador(objetoRefrigerador);

                                Log.e("LISTA"," "+i+refrigeraObj.getInt("marca"));
                                Log.e("Dados sqlite: ", ""+db.getAllMyServPen().size());


                                hideDialog();
                            } catch (JSONException e) {
                                Log.e(TAG, "JSON erro ao consultar dados: " + e.getMessage());

                                hideDialog();

                            }
                        }


                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_list");
                        Toast.makeText(getApplicationContext(), "Menssagem "+errorMsg, Toast.LENGTH_LONG).show();

                        hideDialog();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Log.e("ERRORRRRR","Json error: " + e.getMessage());
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();

                    hideDialog();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Lista Service Error: " + error.getMessage());

                Toast.makeText(getApplicationContext(),"Sem Cadastro!!!", Toast.LENGTH_LONG).show();

                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_cli", ""+idCli);
                params.put("status", ""+status);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
