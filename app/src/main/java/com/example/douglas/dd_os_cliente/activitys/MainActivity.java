package com.example.douglas.dd_os_cliente.activitys;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
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
import com.android.volley.toolbox.StringRequest;
import com.example.douglas.dd_os_cliente.R;
import com.example.douglas.dd_os_cliente.app.AppConfig;
import com.example.douglas.dd_os_cliente.app.AppController;
import com.example.douglas.dd_os_cliente.controler.ServPendenteCtrl;
import com.example.douglas.dd_os_cliente.helper.SQLiteHandler;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static com.google.zxing.BarcodeFormat.QR_CODE;

public class MainActivity extends AppCompatActivity{
    public static String TAG = MainActivity.class.getName();
    private ProgressDialog dialog = null;
    private Button btnselectpic;
    private Button btnConta;
    private Button btnRefri;
    private Button btnServ;
    private AlertDialog alerta;
    ImageView img;

    private Bitmap qrCode;
    private SQLiteHandler db;
    private static int REQCODE = 12;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnselectpic = (Button)findViewById(R.id.buttonSwitcImg);
        btnConta = (Button)findViewById(R.id.btnConta);
        btnRefri = (Button)findViewById(R.id.btnRefri);
        btnServ = (Button)findViewById(R.id.btnServ);

        img = (ImageView) findViewById(R.id.imageViewPrinc);

        //img = (ImageView) findViewById(R.id.imageView);
        dialog = new ProgressDialog(this);
        dialog.setCancelable(true);

        db = new SQLiteHandler(this);

        btnselectpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //gerar QRcode
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try{
                    BitMatrix bitMatrix = multiFormatWriter.encode(""+db.getUserDetails().getUid(), BarcodeFormat.QR_CODE, 200,200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    qrCode = barcodeEncoder.createBitmap(bitMatrix);
                    qrCode.setDensity(100);
                }catch (WriterException e){
                    Log.e(TAG, "EXCEÇÃO: "+e);
                }

                Toast.makeText(getApplicationContext(),"Autenticar Serviço: QRCode",Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                //define o titulo
                builder.setTitle("AUTENTICANDO SERVIÇO");
                //define a mensagem

                //builder.setMessage("Cadastre um Refrigerador para realizar um serviço!!!");
                ImageView img = new ImageView(MainActivity.this);
                img.setImageBitmap(qrCode);
                img.setAdjustViewBounds(true);
                builder.setView(img);
                //define um botão como positivo
                builder.setPositiveButton("OK",new DialogInterface.OnClickListener()

                {
                    public void onClick (DialogInterface arg0, int arg1){
                        listaServPen("",db.getUserDetails().getId());

                    }
                });
                alerta = builder.create();
                alerta.show();

            }
        });


        btnConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ContaActivity.class));
            }
        });


        btnRefri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RefrigeradorTabActivity.class));
            }
        });


        btnServ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ServicesTabActivity.class));

            }
        });

    }


    private void listaServPen(final String status, final int cod_cli) {

        // Tag used to cancel the request
        String tag_string_req = "req_listaServPen";
        //final List<ServPendenteCtrl> listSerPen =null;

        dialog.setTitle("Autenticando...");
        dialog.show();

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
                                dialog.dismiss();
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

}
