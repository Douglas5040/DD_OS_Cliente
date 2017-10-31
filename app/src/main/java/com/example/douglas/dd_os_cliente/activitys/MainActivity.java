package com.example.douglas.dd_os_cliente.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.douglas.dd_os_cliente.R;
import com.example.douglas.dd_os_cliente.app.AppConfig;
import com.example.douglas.dd_os_cliente.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity  {
    private ProgressDialog dialog = null;
    private Button btnselectpic;
    private Button btnConta;
    private Button btnRefri;
    private Button btnServ;
    ImageView img;
    private static int REQCODE = 12;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnselectpic = (Button)findViewById(R.id.buttonSwitcImg);
        btnConta = (Button)findViewById(R.id.btnConta);
        btnRefri = (Button)findViewById(R.id.btnRefri);
        btnServ = (Button)findViewById(R.id.btnServ);

        img = (ImageView) findViewById(R.id.imageView);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Uploading Image...");
        dialog.setCancelable(false);

        btnselectpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Autenticar Serviço: QRCode",Toast.LENGTH_LONG).show();
            }
        });
        ;

        btnConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ContaActivity.class));
            }
        });
        ;

        btnRefri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RefrigeradorTabActivity.class));
            }
        });
        ;

        btnServ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ServicesTabActivity.class));

            }
        });

    }


}
