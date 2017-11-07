package com.example.douglas.dd_os_cliente.activitys;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.douglas.dd_os_cliente.R;
import com.example.douglas.dd_os_cliente.app.AppConfig;
import com.example.douglas.dd_os_cliente.app.AppController;
import com.example.douglas.dd_os_cliente.controler.RefrigeradorCtrl;
import com.example.douglas.dd_os_cliente.controler.ServPendenteCtrl;
import com.example.douglas.dd_os_cliente.helper.SQLiteHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetalheServPenActivity extends AppCompatActivity {
    private static final String TAG = DetalheServPenActivity.class.getSimpleName();
    private TextView txtData;
    private TextView txtHora;
    private TextView txtEnder;
    private TextView txtComplemento;
    private TextView txtCep;
    private TextView txtDescriTec;
    private TextView txtDescriCli;
    private TextView txtDescriRefri;
    private TextView tvTipoManu;
    private Button btnFone1;
    private Button btnFone2;
    private TextView status;
    private ImageView imgViewFoto1;
    private ImageView imgViewFoto2;
    private ImageView imgViewFoto3;
    private SQLiteHandler db;
    private Toolbar mToolbar;
    private Toolbar mToobarBotton;
    private AlertDialog alerta;
    private String urlFoto1 = null;
    private String urlFoto2 = null;
    private String urlFoto3 = null;
    private Handler handler = new Handler();
    RefrigeradorCtrl refriCli = null;
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_serv_pen);

        db = new SQLiteHandler(getApplicationContext());
        refriCli = new RefrigeradorCtrl();
        //alerta = new AlertDialog.Builder(getApplicationContext()).create();


        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        txtData = (TextView) findViewById(R.id.dataView);
        txtHora = (TextView) findViewById(R.id.horaView);
        txtEnder = (TextView) findViewById(R.id.tvEnder);
        txtComplemento = (TextView) findViewById(R.id.tvComplem);
        txtCep = (TextView) findViewById(R.id.tvCep);
        txtDescriCli = (TextView) findViewById(R.id.tvDescriCli);
        txtDescriRefri = (TextView) findViewById(R.id.tvDEscriRefri);
        status = (TextView) findViewById(R.id.tvStatus);
        tvTipoManu = (TextView)findViewById(R.id.tvTipoManu);

//        imgViewFoto1 = (ImageView) findViewById(R.id.imageView1FotoServPen);
//        imgViewFoto2 = (ImageView) findViewById(R.id.imageView2FotoServPen);
//        imgViewFoto3 = (ImageView) findViewById(R.id.imageView3FotoServPen);


        mToolbar = (Toolbar) findViewById(R.id.tb_detal_serv);
        mToolbar.setTitle("Lista de Serviços Pendentes");
        //mToolbar.setLogo(R.drawable.ic_launcher);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        detalhesServPens();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tolbar_detalhe_service, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            //Intent i = new Intent(getApplicationContext(), MenuDrawerActivity.class);  //your class
            //startActivity(i);
            finish();
        }if (id == R.id.action_cancel_service) {
            confirmCancelServ(ServicesTabActivity.PlaceholderFragment.servMyPen);
        }
        return true;
    }

    private void detalhesServPens() {

        Log.e("SercPen Position: ", "-->>---" + ServicesTabActivity.PlaceholderFragment.servPenPosition);


        Log.e("SercPen DATA: ", ">>>> " + ServicesTabActivity.PlaceholderFragment.servMyPen.getData_serv());
        Log.e("SercPen HORA: ", ">>>> " + ServicesTabActivity.PlaceholderFragment.servMyPen.getHora_serv());

        mToolbar.setTitle("Serviço");

        txtData.setText(ServicesTabActivity.PlaceholderFragment.servMyPen.getData_serv());
        txtHora.setText(ServicesTabActivity.PlaceholderFragment.servMyPen.getHora_serv().substring(0,5));
        txtEnder.setText(ServicesTabActivity.PlaceholderFragment.servMyPen.getEnder());
        txtComplemento.setText(ServicesTabActivity.PlaceholderFragment.servMyPen.getComplemento());
        txtCep.setText(ServicesTabActivity.PlaceholderFragment.servMyPen.getCep());
        txtDescriCli.setText(ServicesTabActivity.PlaceholderFragment.servMyPen.getDescri_cli_problem());
        txtDescriRefri.setText(ServicesTabActivity.PlaceholderFragment.servMyPen.getDescri_cli_refrigera());
        status.setText(ServicesTabActivity.PlaceholderFragment.servMyPen.getStatus_serv());
        if(ServicesTabActivity.PlaceholderFragment.servMyPen.getTipoManu() == 1)tvTipoManu.setText("Preventiva");
        else tvTipoManu.setText("Corretiva");
    }
    private void updateStatus(final int id_serv, final String new_status) {
        Log.d(TAG, ">>>>>>>: "+id_serv +" -- "+new_status );
        // Tag used to cancel the request
        String tag_string_req = "req_new_status";


        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_UPDATE_STATUS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Update Status Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {

                        Log.e("Update Status: ", "Status Atualizado com sucesso!!!" );

                        Intent it = new Intent(getApplicationContext(), ServicesTabActivity.class);
                        startActivity(it);
                        finish();
                    } else {
                        // Error in login. Get the error message
                        Log.e("Errro in new Status: ", "Erro ao atualizar novo status!!!" );
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "New Status Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Erro ao ATUALIZAÇÃO DO STATUS", Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_serv", String.valueOf(id_serv));
                params.put("newStatus", new_status);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void confirmCancelServ(final ServPendenteCtrl servPen){
        //Cria o gerador do AlertDialog
        db = new SQLiteHandler(getApplicationContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //define o titulo
        builder.setTitle("Confirmar Cancelamento de Serviço");
        //define a mensagem
        builder.setMessage("Você deseja Cancelar este Serviço?");
        //define um botão como positivo
        builder.setPositiveButton("Sim",new DialogInterface.OnClickListener()

        {
            public void onClick (DialogInterface arg0, int arg1){
                //Toast.makeText(getApplicationContext(), "positivo=" + arg1, Toast.LENGTH_SHORT).show();
                updateStatus(servPen.getId_serv_pen(), "Cancelado");
                db.deleteMyServPen(servPen.getId_serv_pen());


            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("Não",new DialogInterface.OnClickListener()

        {
            public void onClick (DialogInterface arg0, int arg1){
                //Toast.makeText(getApplicationContext(), "negativo=" + arg1, Toast.LENGTH_SHORT).show();
            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }
}
