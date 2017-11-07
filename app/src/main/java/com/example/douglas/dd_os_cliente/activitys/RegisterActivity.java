package com.example.douglas.dd_os_cliente.activitys;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Request.Method;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends Activity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnSeguinte;
    private Button btnLinkToLogin;
    private EditText inputFullName;
    private EditText inputEmail;
    private EditText inputCpfCnpj;
    private EditText inputPassword;
    private Spinner spinnerRegTipoCli;
    private List<String> tipoCli;

    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    public static String name;
    public static String email;
    public static String cpf_cnpj;
    public static String password;
    public static String tipoCliente;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputFullName = (EditText) findViewById(R.id.editRegName);
        inputEmail = (EditText) findViewById(R.id.editRegEmail);
        inputCpfCnpj = (EditText) findViewById(R.id.editRegCpfCnpj);
        inputPassword = (EditText) findViewById(R.id.editRegpassword);
        spinnerRegTipoCli = (Spinner) findViewById(R.id.spinnerRegTipoCli);
        btnSeguinte = (Button) findViewById(R.id.btnSeguinte);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreenReg);

        tipoCli = new ArrayList<String>();
        tipoCli.add(0, "Residencia");
        tipoCli.add(1, "Empresa");
        tipoCli.add(2, "Micro Empresa");
        tipoCli.add(3, "Instituição");
        tipoCli.add(4, "Entidade");
        tipoCli.add(5, "Órgão Público");
        tipoCli.add(6, "Universidade");

        ArrayAdapter<String> arrayAdapterTipoCli = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_spinner_item, tipoCli);
        //arrayAdapter1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinnerRegTipoCli.setAdapter(arrayAdapterTipoCli);
        spinnerRegTipoCli.setSelection(0);


        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(RegisterActivity.this,
                    LoginActivity.class);
            startActivity(intent);
            finish();
        }

        // Register Button Click event
        btnSeguinte.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
               name  = inputFullName.getText().toString().trim();
               email  = inputEmail.getText().toString().trim();
               cpf_cnpj  = inputCpfCnpj.getText().toString().trim();
               password  = inputPassword.getText().toString().trim();
               tipoCliente = spinnerRegTipoCli.getSelectedItem().toString().trim();

                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    verifyUser(email, cpf_cnpj);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Preencha todos os Dados!!!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void verifyUser(final String email, final String cpf_cnpj) {
        // Tag used to cancel the request
        String tag_string_req = "req_verify_cliente";

        pDialog.setMessage("Processando ...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_VERIFY_USER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

                        Toast.makeText(getApplicationContext(), "Informe seu Endereço!!!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(),RegisterActivity2.class));
                        hideDialog();
                    } else {
                        Toast.makeText(getApplicationContext(), "CPF/CNPJ e Email já Registrados!!!", Toast.LENGTH_LONG).show();
                        inputCpfCnpj.requestFocus();
                        inputCpfCnpj.setSelection(0, inputCpfCnpj.getText().length());
                        hideDialog();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    hideDialog();

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Tente Novamente", Toast.LENGTH_LONG).show();
                hideDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("cpf_cnpj", ""+cpf_cnpj);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
