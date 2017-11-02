package com.example.douglas.dd_os_cliente.activitys;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContaActivity extends AppCompatActivity {
    private static String TAG = ContaActivity.class.getName();
    private Toolbar mToolbar;
    private SQLiteHandler db;
    private SessionManager session;

    private EditText editNome;
    private EditText editCpfCnpj;
    private EditText editCep;
    private EditText editEnder;
    private EditText editBairro;
    private EditText editComplemento;
    private EditText editEmail;
    private EditText editCell;
    private EditText editFone;
    private Spinner spinerTipo;
    private AlertDialog alerta;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conta);

        mToolbar = (Toolbar) findViewById(R.id.tb_info_func);
        mToolbar.setTitle("Informações Conta");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // session manager
        session = new SessionManager(getApplicationContext());

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        editNome = (EditText) findViewById(R.id.editNome);
        editCpfCnpj = (EditText) findViewById(R.id.editCpfCnpj);
        editCep = (EditText) findViewById(R.id.editCep);
        editEnder = (EditText) findViewById(R.id.editEnder);
        editBairro = (EditText) findViewById(R.id.editBairro);
        editComplemento = (EditText) findViewById(R.id.editComplemento);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editCell = (EditText) findViewById(R.id.editCell);
        editFone = (EditText) findViewById(R.id.editFone);
        spinerTipo = (Spinner) findViewById(R.id.spinnerTipo);


        db = new SQLiteHandler(getApplicationContext());
        UserClienteCtrl userCli = db.getUserDetails();
        editNome.setText("" + userCli.getName());
        editCpfCnpj.setText("" + userCli.getCpf_cnpj());
        editCep.setText("" + userCli.getCep());
        editEnder.setText("" + userCli.getEnder());
        editBairro.setText("" + userCli.getBairro());
        editComplemento.setText("" + userCli.getPoint_ref());
        editEmail.setText("" + userCli.getEmail());
        editCell.setText("" + userCli.getFone1());
        editFone.setText("" + userCli.getFone2());

        editNome.setEnabled(false);
        editCpfCnpj.setEnabled(false);
        editCep.setEnabled(false);
        editEnder.setEnabled(false);
        editBairro.setEnabled(false);
        editComplemento.setEnabled(false);
        editEmail.setEnabled(false);
        editCell.setEnabled(false);
        editFone.setEnabled(false);
        spinerTipo.setEnabled(false);


        List<String> tipoCli = new ArrayList<String>();
        tipoCli.add(0, "Residencia");
        tipoCli.add(1, "Empresa");
        tipoCli.add(2, "Micro Empresa");
        tipoCli.add(3, "Instituição");
        tipoCli.add(4, "Entidade");
        tipoCli.add(5, "Órgão Público");
        tipoCli.add(6, "Universidade");

        ArrayAdapter<String> arrayAdapterTipoCli = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_spinner_item, tipoCli);
        //arrayAdapter1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinerTipo.setAdapter(arrayAdapterTipoCli);
        switch (userCli.getTipo_cad()){
            case "Residência" : spinerTipo.setSelection(0); break;
            case "Empresa" : spinerTipo.setSelection(1); break;
            case "Micro Empresa" : spinerTipo.setSelection(2); break;
            case "Instituição" : spinerTipo.setSelection(3); break;
            case "Entidade" : spinerTipo.setSelection(4); break;
            case "Órgão Público" : spinerTipo.setSelection(5); break;
            case "Universidade" : spinerTipo.setSelection(6); break;
        }
        LinearLayout focus = (LinearLayout) findViewById(R.id.focus);
        focus.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tolbar_conta, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            //Intent i = new Intent(getApplicationContext(), MenuDrawerActivity.class);  //your class
            //startActivity(i);
            finish();
        }
        if (id == R.id.action_settings) {
            item.setIcon(R.drawable.ic_salve);
            if(editNome.isEnabled()){
               UserClienteCtrl userCli = new UserClienteCtrl();
                userCli.setId(db.getUserDetails().getId());
                userCli.setName(editNome.getText().toString());
                userCli.setEmail(editEmail.getText().toString());
                userCli.setFone1(editCell.getText().toString());
                userCli.setCep(Integer.parseInt(editCep.getText().toString()));
                userCli.setCpf_cnpj(Integer.parseInt(editCpfCnpj.getText().toString()));
                userCli.setPoint_ref(editComplemento.getText().toString());
                userCli.setEnder(editEnder.getText().toString());
                userCli.setBairro(editBairro.getText().toString());
                userCli.setFone2(editFone.getText().toString());
                userCli.setTipo_cad(spinerTipo.getSelectedItem().toString());

                editarUserCLi(userCli);
            }

            editNome.setEnabled(true);
            editCpfCnpj.setEnabled(true);
            editCep.setEnabled(true);
            editEnder.setEnabled(true);
            editBairro.setEnabled(true);
            editComplemento.setEnabled(true);
            editEmail.setEnabled(true);
            editCell.setEnabled(true);
            editFone.setEnabled(true);
            spinerTipo .setEnabled(true);

            editNome.requestFocus();
            editNome.setSelection(0,editNome.getText().toString().length());

        }if (id == R.id.action_sair) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            //define o titulo
            builder.setTitle("Editando Dados...");
            //define a mensagem
            builder.setMessage("Você deseja Sair?");
            //define um botão como positivo
            builder.setPositiveButton("Sim",new DialogInterface.OnClickListener()

            {
                public void onClick (DialogInterface arg0, int arg1){
                    session.setLogin(false);

                    // Launching the login activity
                    Intent intent = new Intent(ContaActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            //define um botão como negativo.
            builder.setNegativeButton("Não",new DialogInterface.OnClickListener()

            {
                public void onClick (DialogInterface arg0, int arg1){
                    Toast.makeText(getApplicationContext(), "negativo=" + arg1, Toast.LENGTH_SHORT).show();
                }
            });
            //cria o AlertDialog
            alerta = builder.create();
            //Exibe
            alerta.show();
        }

        return true;
    }


    public void editarUserCLi(final UserClienteCtrl userCli){
        // Tag used to cancel the request
        String tag_string_req = "req_alterar_dados_user";

        pDialog.setMessage("Salvando Dados ...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_EDIT_USER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Carregando dados: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        Log.e(TAG, "Usuario dados ID: " + userCli.getId());

                       db.updateUserCli(userCli);
                        hideDialog();
                        //finish();

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_list");
                        Toast.makeText(getApplicationContext(), "Erro AQUI "+errorMsg, Toast.LENGTH_LONG).show();
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
                Log.e(TAG, "Edit User Error: " + error.getMessage());
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", ""+userCli.getId());
                params.put("nome", ""+userCli.getName());
                params.put("ender", ""+userCli.getEnder());
                params.put("bairro", ""+userCli.getBairro());
                params.put("ponto_referencia", ""+userCli.getPoint_ref());
                params.put("cep", ""+userCli.getCep());
                params.put("celular", ""+userCli.getFone1());
                params.put("fone_fixo", ""+userCli.getFone2());
                params.put("email", ""+userCli.getEmail());
                params.put("cpf_cnpj", ""+userCli.getCpf_cnpj());
                params.put("tipo", ""+userCli.getTipo_cad());

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
