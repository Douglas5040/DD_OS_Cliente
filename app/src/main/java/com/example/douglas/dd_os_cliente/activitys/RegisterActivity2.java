package com.example.douglas.dd_os_cliente.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.douglas.dd_os_cliente.R;
import com.example.douglas.dd_os_cliente.app.AppConfig;
import com.example.douglas.dd_os_cliente.app.AppController;
import com.example.douglas.dd_os_cliente.controler.UserClienteCtrl;
import com.example.douglas.dd_os_cliente.helper.SQLiteHandler;
import com.example.douglas.dd_os_cliente.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity2 extends AppCompatActivity {
    private static final String TAG = RegisterActivity2.class.getSimpleName();

    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    private EditText edit_cep;
    private EditText edit_ender;
    private EditText edit_bairro;
    private EditText edit_point_ref;
    private EditText edit_fone1;
    private EditText edit_fone2;

    private Button btnRgister;
    private Button btnLinkLogin;

    UserClienteCtrl cliente;
    private String cpf_cnpj;
    private String nome;
    private String email;
    private String senha;
    private String tipoCli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        edit_cep = (EditText) findViewById(R.id.edit_cep);
        edit_ender = (EditText) findViewById(R.id.edit_ender);
        edit_bairro = (EditText) findViewById(R.id.edit_bairro);
        edit_point_ref = (EditText) findViewById(R.id.edit_pont_ref);
        edit_fone1 = (EditText) findViewById(R.id.editFone1);
        edit_fone2 = (EditText) findViewById(R.id.editFone2);

        btnRgister = (Button) findViewById(R.id.btnRegister);
        btnLinkLogin = (Button) findViewById(R.id.btnLinkToRegister2Screen);

        cliente = new UserClienteCtrl();

        cliente.setCpf_cnpj(Integer.parseInt(RegisterActivity.cpf_cnpj));
        cliente.setName(RegisterActivity.name);
        cliente.setEmail(RegisterActivity.email);
        cliente.setSenha( RegisterActivity.password);
        cliente.setTipo_cad(RegisterActivity.password);

        btnRgister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cliente.setCep(Integer.parseInt(edit_cep.getText().toString().trim()));
                cliente.setEnder(edit_ender.getText().toString().trim());
                cliente.setBairro(edit_bairro.getText().toString().trim());
                cliente.setPoint_ref(edit_point_ref.getText().toString().trim());
                cliente.setFone1(edit_fone1.getText().toString().trim());
                cliente.setFone2(edit_fone2.getText().toString().trim());


                String cep = edit_cep.getText().toString().trim();
                String ender = edit_ender.getText().toString().trim();
                String bairro = edit_bairro.getText().toString().trim();
                String ponto_ref = edit_point_ref.getText().toString().trim();
                String fone1 = edit_fone1.getText().toString().trim();
                String fone2 = edit_fone2.getText().toString().trim();

                if(!cep.isEmpty() && !ender.isEmpty() && !bairro.isEmpty() && !ponto_ref.isEmpty() && !fone1.isEmpty() && !fone2.isEmpty()){
                    registerUser(cliente);
                }else Toast.makeText(getApplicationContext(),"Preencha todos os Dados!!!",Toast.LENGTH_LONG).show();
            }
        });
        btnLinkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

    }

    private void registerUser(final UserClienteCtrl cliente) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registrando ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER_CLI, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        cliente.setCreated_at(user.getString("created_at"));
                        cliente.setId(user.getInt("id_cli"));
                        cliente.setUpdate_at("");

                        // Inserting row in users table
                        db.addUser(cliente);

                        Toast.makeText(getApplicationContext(), "Cadastro Realizado com Sucesso!!!", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(
                                RegisterActivity2.this,
                                LoginActivity.class);
                        startActivity(intent);
                        hideDialog();
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", cliente.getName());
                params.put("email", cliente.getEmail());
                params.put("password", cliente.getSenha());
                params.put("cpf_cnpj", ""+cliente.getCpf_cnpj());
                params.put("ender", cliente.getEnder());
                params.put("ponto_ref", cliente.getPoint_ref());
                params.put("cep", ""+cliente.getCep());
                params.put("bairro", cliente.getBairro());
                params.put("fone1", cliente.getFone1());
                params.put("fone2", cliente.getFone2());
                params.put("tipo", cliente.getTipo_cad());

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
