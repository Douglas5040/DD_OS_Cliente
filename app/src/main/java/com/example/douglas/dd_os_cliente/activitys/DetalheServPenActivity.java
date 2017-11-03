package com.example.douglas.dd_os_cliente.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.douglas.dd_os_cliente.R;
import com.example.douglas.dd_os_cliente.controler.RefrigeradorCtrl;
import com.example.douglas.dd_os_cliente.helper.SQLiteHandler;

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
        pDialog = new ProgressDialog(getApplicationContext());
        pDialog.setCancelable(false);

        txtData = (TextView) findViewById(R.id.dataView);
        txtHora = (TextView) findViewById(R.id.horaView);
        txtEnder = (TextView) findViewById(R.id.tvEnder);
        txtComplemento = (TextView) findViewById(R.id.tvComplem);
        txtCep = (TextView) findViewById(R.id.tvCep);
        txtDescriCli = (TextView) findViewById(R.id.tvDescriCli);
        txtDescriRefri = (TextView) findViewById(R.id.tvDEscriRefri);
        status = (TextView) findViewById(R.id.tvStatus);

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

    private void detalhesServPens() {

        Log.e("SercPen Position: ", "-->>---" + ServicesTabActivity.PlaceholderFragment.servPenPosition);


        Log.e("SercPen DATA: ", ">>>> " + ServicesTabActivity.PlaceholderFragment.servMyPen.getData_serv());
        Log.e("SercPen HORA: ", ">>>> " + ServicesTabActivity.PlaceholderFragment.servMyPen.getHora_serv());

        mToolbar.setTitle(ServicesTabActivity.PlaceholderFragment.servMyPen.getNomeCli() + ": " + ServicesTabActivity.PlaceholderFragment.servMyPen.getTipoCli());

        txtData.setText(ServicesTabActivity.PlaceholderFragment.servMyPen.getData_serv());
        txtHora.setText(ServicesTabActivity.PlaceholderFragment.servMyPen.getHora_serv());
        txtEnder.setText(ServicesTabActivity.PlaceholderFragment.servMyPen.getEnder());
        txtComplemento.setText(ServicesTabActivity.PlaceholderFragment.servMyPen.getComplemento());
        txtCep.setText(ServicesTabActivity.PlaceholderFragment.servMyPen.getCep());
        txtDescriCli.setText(ServicesTabActivity.PlaceholderFragment.servMyPen.getDescri_cli_problem());
        txtDescriRefri.setText(ServicesTabActivity.PlaceholderFragment.servMyPen.getDescri_cli_refrigera());
        status.setText(ServicesTabActivity.PlaceholderFragment.servMyPen.getStatus_serv());


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            //Intent i = new Intent(getApplicationContext(), MenuDrawerActivity.class);  //your class
            //startActivity(i);
            finish();
        }
        return true;
    }
}
