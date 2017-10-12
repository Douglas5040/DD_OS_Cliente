//package com.example.douglas.dd_os_cliente.activitys;
//
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Matrix;
//import android.graphics.drawable.BitmapDrawable;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.util.Base64;
//import android.util.Log;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.android.volley.DefaultRetryPolicy;
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.StringRequest;
//import com.example.douglas.dd_os_cliente.app.AppConfig;
//import com.example.douglas.dd_os_cliente.app.AppController;
//import com.example.douglas.dd_os_cliente.app.DataHoraNow;
//import com.example.douglas.dd_os_cliente.controler.RefrigeradorCtrl;
//import com.example.douglas.dd_os_cliente.helper.SQLiteHandler;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.util.HashMap;
//import java.util.Map;
//
//public class EditArActivity extends AppCompatActivity {
//    private static final String TAG = EditArActivity.class.getSimpleName();
//    private static final int REQUEST_CODE_FOTO1 = 1111;
//    private static final int REQUEST_CODE_FOTO2 = 2222;
//    private static final int REQUEST_CODE_FOTO3 = 3333;
//    private static final int JPEG_FILE_PREFIX = 10;
//    private static final int JPEG_FILE_SUFFIX = 10;
//    private TextView txtCli;
//    private CheckBox checkBoxOutAir;
//    private CheckBox checkBoxExaustor;
//    private CheckBox checkBoxControl;
//    private CheckBox checkBoxTimer;
//    private Spinner spinnerMarca;
//    private Spinner spinnerModelo;
//    private Spinner spinnerTensao;
//    private Spinner spinnerLvlEcon;
//    private Spinner spinnerBTU;
//    private EditText editTamanho;
//    private EditText editTempoUso;
//    private EditText editPeso;
//    private ImageButton imgBtnFoto1;
//    private ImageButton imgBtnFoto2;
//    private ImageButton imgBtnFoto3;
//    private ImageView imgViewfoto1;
//    private ImageView imgViewfoto2;
//    private Toolbar mToolbar;
//    private Toolbar mToobarBotton;
//    private SQLiteHandler db;
//    private AlertDialog alerta;
//    private String dataNow = "";
//    RefrigeradorCtrl arCli;
//    int bottonClicked = 0;
//
//    File f;
//    File imageF;
//    String mCurrentPhotoPath;
//    Bitmap bitmap = null;
//    private ProgressDialog dialog = null;
//
//    int isExastor = 0,isControl = 0,isTimer = 0;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_edit_ar);
//
//        db = new SQLiteHandler(getApplicationContext());
//        arCli = OrdemServiceActivity.arCliSpinnerOS;
//        dataNow = "";
//
//        dialog = new ProgressDialog(this);
//        dialog.setMessage("Carregando Imagem...");
//        dialog.setCancelable(false);
//
//        txtCli = (TextView) findViewById(R.id.textCli);
//        checkBoxOutAir = (CheckBox) findViewById(R.id.checkBoxOutAir);
//        checkBoxExaustor = (CheckBox) findViewById(R.id.checkBoxExaustor);
//        checkBoxControl = (CheckBox) findViewById(R.id.checkBoxControl);
//        checkBoxTimer = (CheckBox) findViewById(R.id.checkBoxTimer);
//
//        editTamanho = (EditText) findViewById(R.id.editTamanho);
//        editTempoUso = (EditText) findViewById(R.id.editTempoUso);
//        editPeso = (EditText) findViewById(R.id.editPeso);
//        imgBtnFoto1 = (ImageButton) findViewById(R.id.imgBtnFoto1);
//        imgBtnFoto2 = (ImageButton) findViewById(R.id.imgBtnFoto2);
//        imgBtnFoto3 = (ImageButton) findViewById(R.id.imgBtnFoto3);
//
//        try {
//            //Fotos
//            Bitmap bitmap1 = BitmapFactory.decodeByteArray(arCli.getFoto1(), 0, arCli.getFoto1().length);
//            imgBtnFoto1.setImageBitmap(resizeImage(this, bitmap1, 600, 500));
//            imgBtnFoto1.setAdjustViewBounds(true);
//
//            Bitmap bitmap2 = BitmapFactory.decodeByteArray(arCli.getFoto2(), 0, arCli.getFoto2().length);
//            imgBtnFoto2.setImageBitmap(resizeImage(this, bitmap2, 600, 500));
//            imgBtnFoto2.setAdjustViewBounds(true);
//
//            Bitmap bitmap3 = BitmapFactory.decodeByteArray(arCli.getFoto3(), 0, arCli.getFoto3().length);
//            imgBtnFoto3.setImageBitmap(resizeImage(this, bitmap3, 600, 500));
//            imgBtnFoto3.setAdjustViewBounds(true);
//
//        }catch (Exception e){Log.e(TAG,"ERROR : "+e);}
//        //preenchendo Spinner
//        spinnerMarca = (Spinner) findViewById(R.id.spinnerMarca);
//        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getApplicationContext(),R.layout.simple_spinner_item, db.getMacas());
//        //arrayAdapter1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
//        spinnerMarca.setAdapter(arrayAdapter1);
//
//        //preenchendo Spinner
//        spinnerModelo = (Spinner) findViewById(R.id.spinnerModelo);
//        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getApplicationContext(),R.layout.simple_spinner_item, db.getModelo());
//        //arrayAdapter1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
//        spinnerModelo.setAdapter(arrayAdapter2);
//
//        //preenchendo Spinner
//        spinnerTensao = (Spinner) findViewById(R.id.spinnerTensao);
//        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(getApplicationContext(),R.layout.simple_spinner_item, db.getTencaoTomada());
//        //arrayAdapter1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
//        spinnerTensao.setAdapter(arrayAdapter3);
//
//        //preenchendo Spinner
//        spinnerLvlEcon = (Spinner) findViewById(R.id.spinnerLvlEcon);
//        ArrayAdapter<String> arrayAdapter4 = new ArrayAdapter<String>(getApplicationContext(),R.layout.simple_spinner_item, db.getNvEcon());
//        //arrayAdapter1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
//        spinnerLvlEcon.setAdapter(arrayAdapter4);
//
//        //preenchendo Spinner
//        spinnerBTU = (Spinner) findViewById(R.id.spinnerBTU);
//        ArrayAdapter<String> arrayAdapter5 = new ArrayAdapter<String>(getApplicationContext(),R.layout.simple_spinner_item, db.getBTU());
//        //arrayAdapter1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
//        spinnerBTU.setAdapter(arrayAdapter5);
//
//
//        txtCli.setText(DetalhesMyService.servPen.getNomeCli()+" - "+DetalhesMyService.servPen.getTipoCli());
//
//        mToolbar = (Toolbar) findViewById(R.id.tb_edit_ar);
//
//        mToolbar.setTitle("EDITANDO DESCRIÇÃO DO AR-CONDICIONADO");
//        //mToolbar.setLogo(R.drawable.ic_launcher);
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//
//        mToobarBotton = (Toolbar) findViewById(R.id.inc_tb_botton_edit_ar);
//        mToobarBotton.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//
//                salvarDadosAr();
//
//
//                return true;
//            }
//        });
//        mToobarBotton.inflateMenu(R.menu.menu_botton_edit_ar);
//
//        Log.e(TAG, "----Valor do arCli: " + arCli.getId_refri());
//        if(arCli != null )retornaArCli();
//
//        checkBoxOutAir.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(checkBoxOutAir.isChecked()){
//                    checkBoxOutAir.setText("Sim");
//                }else checkBoxOutAir.setText("Não");
//            }
//        });
//        checkBoxExaustor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(checkBoxExaustor.isChecked()){
//                    checkBoxExaustor.setText("Sim");
//                    isExastor = 1;
//                }else {
//                    checkBoxExaustor.setText("Não");
//                    isExastor = 0;
//                }
//            }
//        });
//        checkBoxControl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(checkBoxControl.isChecked()){
//                    checkBoxControl.setText("Sim");
//                    isControl =1;
//                }else {
//                    checkBoxControl.setText("Não");
//                    isControl = 0;
//                }
//            }
//        });
//        checkBoxTimer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(checkBoxTimer.isChecked()){
//                    checkBoxTimer.setText("Sim");
//                    isTimer = 1;
//                }else {
//                    checkBoxTimer.setText("Não");
//                    isTimer = 0;
//                }
//            }
//        });
//        imgBtnFoto1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intentPegaFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intentPegaFoto,REQUEST_CODE_FOTO1);
//            }
//        });
//        imgBtnFoto2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intentPegaFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intentPegaFoto,REQUEST_CODE_FOTO2);
//            }
//        });
//        imgBtnFoto3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intentPegaFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intentPegaFoto,REQUEST_CODE_FOTO3);
//            }
//        });
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        Log.e("Entrou Camera","-------------------onActivityResult.....................");
//        if(resultCode == RESULT_OK){
//            Log.e("Entrou Camera","-------------------REQUEST_CODE_B.....................");
//            try{
//                if(bitmap != null){
//                    bitmap.recycle();
//                }
//                bitmap = (Bitmap) data.getExtras().get("data");
//                Log.e("Entrou Camera","-------------------CAmera.....................");
//                switch(requestCode){
//                    case REQUEST_CODE_FOTO1:  imgBtnFoto1.setImageBitmap(resizeImage(this, bitmap, 650, 500));
//                                                imgBtnFoto1.setAdjustViewBounds(true);
//                                                DataHoraNow data1 = new DataHoraNow();
//                                                dataNow = data1.getDataHoraNow();
//                        break;
//                    case REQUEST_CODE_FOTO2:  imgBtnFoto2.setImageBitmap(resizeImage(this, bitmap, 650, 500));
//                                                imgBtnFoto2.setAdjustViewBounds(true);
//                                                DataHoraNow data2 = new DataHoraNow();
//                                                dataNow = data2.getDataHoraNow();
//                        break;
//                    case REQUEST_CODE_FOTO3:  imgBtnFoto3.setImageBitmap(resizeImage(this, bitmap, 650, 500));
//                                                imgBtnFoto3.setAdjustViewBounds(true);
//                                                DataHoraNow data3 = new DataHoraNow();
//                                                dataNow = data3.getDataHoraNow();
//                        break;
//                }
//            }catch (Exception ex){
//                ex.printStackTrace();
//            }
//        }
//    }
//
//    private static Bitmap resizeImage(Context context, Bitmap bmpOriginal, float newWidth, float newHeight){
//        Bitmap novoBmp = null;
//
//        int w = bmpOriginal.getWidth();
//        int h = bmpOriginal.getHeight();
//
//        float densityFactor = context.getResources().getDisplayMetrics().density;
//        float novoW = newWidth * densityFactor;
//        float novoH = newHeight * densityFactor;
//
//        //calcula escala e percentual do tamanho original
//        float scalaW = novoW / w;
//        float scalaH = novoH / h;
//
//        //Criando uma matrix para manipulação da imagem bitmap
//        Matrix matrix = new Matrix();
//
//        //Definindo a proporção da escala para a Matrix
//        matrix.postScale(scalaW, scalaH);
//
//        //Criando novo bitmap com o novo tamanho
//        novoBmp = Bitmap.createBitmap(bmpOriginal, 0, 0, w, h, matrix, true);
//
//        return  novoBmp;
//    }
//
//
//
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == android.R.id.home) {
////            Intent i = new Intent(getApplicationContext(), ListServPendenteActivity.class);  //your class
////            startActivity(i);
//            finish();
//        }
//
//        return true;
//    }
//
//    public void editarArCliBD(final RefrigeradorCtrl arCli){
//        // Tag used to cancel the request
//        String tag_string_req = "req_inserir_Refrigerador";
//
//        db = new SQLiteHandler(getApplicationContext());
//        StringRequest strReq = new StringRequest(Request.Method.POST,
//                AppConfig.URL_ALTERAR_DESCRI_AR, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//               // Log.d(TAG, "Carregando dados: " + response.toString());
//                try {
//                    JSONObject jObj = new JSONObject(response);
//                    boolean error = jObj.getBoolean("error");
//
//                    // Check for error node in json
//                    if (!error) {
//
//                        Log.e("UPDATE REFRIGERADOR: ", "Ar Atualizado com sucesso");
//                        Log.e("Codigo Ar Return: ",""+arCli.getId_refri());
//                        db.deleteUniRefrigera(arCli.getId_refri());
//
//                        db.addRefrigerador(arCli);
//
//
//                        alerta.dismiss();
//                        Intent i = new Intent(getApplicationContext(), OrdemServiceActivity.class);  //your class
//                        startActivity(i);
//                        finish();
//
//                    } else {
//                        // Error in login. Get the error message
//                        String errorMsg = jObj.getString("error_list");
//                        Toast.makeText(getApplicationContext(), "Erro AQUI "+errorMsg, Toast.LENGTH_LONG).show();
//                    }
//                } catch (JSONException e) {
//                    // JSON error
//                    e.printStackTrace();
//                    Log.e("ERRORRRRR","Json error: " + e.getMessage());
//                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
//                }
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Lista Service Error: " + error.getMessage());
//                Toast.makeText(getApplicationContext(),
//                        "Sem Peças no Banco", Toast.LENGTH_LONG).show();
//            }
//        }) {
//
//            @Override
//            protected Map<String, String> getParams() {
//                // Posting parameters to login url
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("id_ar", ""+arCli.getId_refri());
//                params.put("peso", ""+arCli.getPeso());
//                params.put("has_control", ""+arCli.getHas_control());
//                params.put("has_exaustor", ""+arCli.getHas_exaustor());
//                params.put("saida_ar", ""+arCli.getSaida_ar());
//                params.put("capaci_termica", ""+arCli.getCapaci_termica());
//                params.put("tencao_tomada", ""+arCli.getTencao_tomada());
//                params.put("has_timer", ""+arCli.getHas_timer());
//                params.put("tipo_modelo", ""+arCli.getTipo_modelo());
//                params.put("marca", ""+arCli.getMarca());
//                params.put("temp_uso", ""+arCli.getTemp_uso());
//                params.put("nivel_econo", ""+arCli.getNivel_econo());
//                params.put("tamanho", ""+arCli.getTamanho());
//                params.put("foto1", dataNow+"_"+DetalhesMyService.servPen.getNomeCli()+"_1");
//                params.put("foto2", dataNow+"_"+DetalhesMyService.servPen.getNomeCli()+"_2");
//                params.put("foto3", dataNow+"_"+DetalhesMyService.servPen.getNomeCli()+"_3");
//                return params;
//            }
//
//
//        };
//
//        // Adding request to request queue
//        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
//
//    }
//
//    public void addArCliBD(final RefrigeradorCtrl arCli) {
////
////        // Tag used to cancel the request
////        String tag_string_req = "req_inserir_Refrigerador";
////
////        db = new SQLiteHandler(getApplicationContext());
////        StringRequest strReq = new StringRequest(Request.Method.POST,
////                AppConfig.URL_INSERIR_DESCRI_AR, new Response.Listener<String>() {
////
////            @Override
////            public void onResponse(String response) {
////                Log.d(TAG, "Carregando dados: " + response.toString());
////                try {
////                    JSONObject jObj = new JSONObject(response);
////                    boolean error = jObj.getBoolean("error");
////                    int lastIdAr = jObj.getInt("last_id_ar");
////
////                    // Check for error node in json
////                    if (!error) {
////
////                         Log.e("INSERT REFRIGERADOR: ", "Ar inserido com sucesso");
////                         arCli.setId_refri(lastIdAr);
////                         db.addRefrigerador(arCli);
////
////                        Log.e(TAG, "-----------------------2 ");
////                        alerta.dismiss();
////                        Intent i = new Intent(getApplicationContext(), OrdemServiceActivity.class);  //your class
////                        startActivity(i);
////                        finish();
////
////                    } else {
////                        // Error in login. Get the error message
////                        String errorMsg = jObj.getString("error_list");
////                        Toast.makeText(getApplicationContext(), "Erro AQUI "+errorMsg, Toast.LENGTH_LONG).show();
////                    }
////                } catch (JSONException e) {
////                    // JSON error
////                    e.printStackTrace();
////                    Log.e("ERRORRRRR","Json error: " + e.getMessage());
////                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
////                }
////            }
////        }, new Response.ErrorListener() {
////
////            @Override
////            public void onErrorResponse(VolleyError error) {
////                Log.e(TAG, "Lista Service Error: " + error.getMessage());
////                Toast.makeText(getApplicationContext(),
////                        "Sem Peças no Banco", Toast.LENGTH_LONG).show();
////            }
////        }) {
////
////            @Override
////            protected Map<String, String> getParams() {
////                // Posting parameters to login url
////                Map<String, String> params = new HashMap<String, String>();
////                params.put("id_cliente", ""+arCli.getId_cliente());
////                params.put("peso", ""+arCli.getPeso());
////                params.put("has_control", ""+arCli.getHas_control());
////                params.put("has_exaustor", ""+arCli.getHas_exaustor());
////                params.put("saida_ar", ""+arCli.getSaida_ar());
////                params.put("capaci_termica", ""+arCli.getCapaci_termica());
////                params.put("tencao_tomada", ""+arCli.getTencao_tomada());
////                params.put("has_timer", ""+arCli.getHas_timer());
////                params.put("tipo_modelo", ""+arCli.getTipo_modelo());
////                params.put("marca", ""+arCli.getMarca());
////                params.put("temp_uso", ""+arCli.getTemp_uso());
////                params.put("nivel_econo", ""+arCli.getNivel_econo());
////                params.put("tamanho", ""+arCli.getTamanho());
////                params.put("foto1", ""+arCli.getFoto1());
////                params.put("foto2", ""+arCli.getFoto2());
////                params.put("foto3", ""+arCli.getFoto3());
////                return params;
////            }
////
////        };
////
////        // Adding request to request queue
////        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
////
//   }
//
//    private void  retornaArCli()  {
//
//
//            //Preenchendo campos no Layout
//            if(arCli.getHas_exaustor() == 1) {
//                checkBoxExaustor.setChecked(true);
//                checkBoxExaustor.setText("Sim");
//                isExastor = 1;
//            }if(arCli.getHas_control() == 1) {
//                checkBoxControl.setChecked(true);
//                checkBoxControl.setText("Sim");
//                isControl = 1;
//            }if(arCli.getHas_timer() == 1) {
//                checkBoxTimer.setChecked(true);
//                checkBoxTimer.setText("Sim");
//                isTimer = 1;
//            }
//            if(""+arCli.getMarca() != "") spinnerMarca.setSelection(arCli.getMarca()-1);
//
//            if(""+arCli.getTipo_modelo() != "") spinnerModelo.setSelection(arCli.getTipo_modelo()-1);
//
//
//
//        if(""+arCli.getTencao_tomada() != "") spinnerTensao.setSelection(arCli.getTencao_tomada()-1);
//
//
//
//        if(""+arCli.getNivel_econo() != "") spinnerLvlEcon.setSelection(arCli.getNivel_econo()-1);
//
//
//
//        if(""+arCli.getCapaci_termica() != "") spinnerBTU.setSelection(arCli.getCapaci_termica()-1);
//
//
//            editTamanho.setText(arCli.getTamanho());
//            editTempoUso.setText(String.valueOf(arCli.getTemp_uso()));
//            editPeso.setText(""+arCli.getPeso());
//
//  }
//
//
//    private void salvarDadosAr(){
//        //Cria o gerador do AlertDialog
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(EditArActivity.this);
//        //define o titulo
//        builder.setTitle("Confirmação para Salvar Dados");
//        //define a mensagem
//        builder.setMessage("Você deseja Salvar as Informações?");
//        //define um botão como positivo
//        builder.setPositiveButton("Sim",new DialogInterface.OnClickListener()
//
//        {
//            public void onClick (DialogInterface arg0, int arg1){
//                //Toast.makeText(getApplicationContext(), "positivo=" + arg1, Toast.LENGTH_SHORT).show();
//
//                    arCli = new RefrigeradorCtrl();
//
//                    arCli.setPeso(Integer.parseInt(editPeso.getText().toString()));
//                    arCli.setHas_control(isControl);
//                    arCli.setHas_exaustor(isExastor);
//                    arCli.setSaida_ar("");
//                    arCli.setCapaci_termica(spinnerBTU.getSelectedItemPosition()+1);
//                    arCli.setTencao_tomada(spinnerTensao.getSelectedItemPosition()+1);
//                    arCli.setHas_timer(isTimer);
//                    arCli.setTipo_modelo(spinnerModelo.getSelectedItemPosition()+1);
//                    arCli.setMarca(spinnerMarca.getSelectedItemPosition()+1);
//                    arCli.setTemp_uso(Double.valueOf(editTempoUso.getText().toString()));
//                    arCli.setNivel_econo(spinnerLvlEcon.getSelectedItemPosition()+1);
//                    arCli.setTamanho(editTamanho.getText().toString());
//
//                    arCli.setId_cliente(DetalhesMyService.servPen.getCliente_id());
//                    arCli.setId_refri(OrdemServiceActivity.arCliSpinnerOS.getId_refri());
//
//
//                Bitmap bitmap1 = ((BitmapDrawable) imgBtnFoto1.getDrawable()).getBitmap();
//                ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
//                bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, stream1);
//                byte[] byteArray1 = stream1.toByteArray();
//                arCli.setFoto1(byteArray1);
//
//                Bitmap bitmap2 = ((BitmapDrawable) imgBtnFoto2.getDrawable()).getBitmap();
//                ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
//                bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, stream2);
//                byte[] byteArray2 = stream2.toByteArray();
//                arCli.setFoto2(byteArray2);
//
//                Bitmap bitmap3 = ((BitmapDrawable) imgBtnFoto3.getDrawable()).getBitmap();
//                ByteArrayOutputStream stream3 = new ByteArrayOutputStream();
//                bitmap3.compress(Bitmap.CompressFormat.JPEG, 100, stream3);
//                byte[] byteArray3 = stream3.toByteArray();
//                arCli.setFoto3(byteArray3);
//
//            if(!dataNow.equals("")) {
//                dialog.show();
//                uploadImgServer(bitmap1, "1");
//                uploadImgServer(bitmap2, "2");
//                uploadImgServer(bitmap3, "3");
//
//            }
//                editarArCliBD(arCli);
//                dialog.dismiss();
//
//            }
//        });
//        //define um botão como negativo.
//        builder.setNegativeButton("Não",new DialogInterface.OnClickListener()
//
//        {
//            public void onClick (DialogInterface arg0, int arg1){
//                Toast.makeText(getApplicationContext(), "negativo=" + arg1, Toast.LENGTH_SHORT).show();
//                alerta.cancel();
//            }
//        });
//        //cria o AlertDialog
//        alerta = builder.create();
//        //Exibe
//        alerta.show();
//    }
//
//
//    private void uploadImgServer(Bitmap image, String nome) {
//        JSONObject  jsonObject = new JSONObject();
//
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//        String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
//        try {
//            jsonObject.put("name", dataNow+"_"+DetalhesMyService.servPen.getNomeCli()+"_"+nome);   Log.e("Image name",""+dataNow+"_"+DetalhesMyService.servPen.getNomeCli()+"_"+nome);
//            jsonObject.put("image", encodedImage);
//        } catch (JSONException e) {
//            Log.e("JSONObject Here", e.toString());
//        }
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConfig.URL_UPLOAD_IMAGE, jsonObject,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject jsonObject) {
//                        Log.e("Message from server", jsonObject.toString());
//                        dialog.dismiss();
//                        Toast.makeText(getApplication(), "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                Log.e("Message from server", volleyError.toString());
//                dialog.dismiss();
//            }
//        });
//        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        //Volley.newRequestQueue(this).add(jsonObjectRequest);
//
//        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
//    }
//
//}
