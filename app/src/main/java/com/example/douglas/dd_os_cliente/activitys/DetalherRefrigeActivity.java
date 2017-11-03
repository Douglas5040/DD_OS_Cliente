package com.example.douglas.dd_os_cliente.activitys;

import android.app.ProgressDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.douglas.dd_os_cliente.R;
import com.example.douglas.dd_os_cliente.app.AppConfig;
import com.example.douglas.dd_os_cliente.app.AppController;
import com.example.douglas.dd_os_cliente.controler.RefrigeradorCtrl;
import com.example.douglas.dd_os_cliente.helper.SQLiteHandler;
import com.example.douglas.dd_os_cliente.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetalherRefrigeActivity extends AppCompatActivity {
    private static String TAG = DetalherRefrigeActivity.class.getName();

    private Toolbar mToolbar;
    private SQLiteHandler db;
    private SessionManager session;

    private CheckBox checkBoxExaustor;
    private CheckBox checkBoxControl;
    private CheckBox checkSaidaAr;
    private CheckBox checkTimer;

    private Spinner spinnerMarca;
    private Spinner spinnerModelo;
    private Spinner spinnerTensao;
    private Spinner spinnerLvlEcon;
    private Spinner spinnerBTU;

    private EditText editTamanho;
    private EditText editTempoUso;
    private EditText editPeso;
    private EditText editLotacionamento;

    private AlertDialog alerta;
    private ProgressDialog pDialog;
    boolean isExastor = false, isControl = false, isTimer = false;
    private RefrigeradorCtrl refrigerador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_refrige);

        mToolbar = (Toolbar) findViewById(R.id.tb_detalhes_ar);
        mToolbar.setTitle("Refrigerador");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        checkBoxExaustor = (CheckBox) findViewById(R.id.checkBoxExaustor);
        checkBoxControl = (CheckBox) findViewById(R.id.checkBoxControl);
        checkSaidaAr = (CheckBox) findViewById(R.id.checkBoxOutAir);
        checkTimer = (CheckBox) findViewById(R.id.checkBoxTimer);

        editTamanho = (EditText) findViewById(R.id.editTamanho);
        editTempoUso = (EditText) findViewById(R.id.editTempoUso);
        editLotacionamento = (EditText) findViewById(R.id.detalheLotacionamento);
        editPeso = (EditText) findViewById(R.id.editPeso);

        db = new SQLiteHandler(getApplicationContext());

        spinnerMarca = (Spinner) findViewById(R.id.spinnerMarca);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getApplicationContext(),R.layout.simple_spinner_item, db.getMacas());
        //arrayAdapter1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinnerMarca.setAdapter(arrayAdapter1);

        //preenchendo Spinner
        spinnerModelo = (Spinner) findViewById(R.id.spinnerModelo);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getApplicationContext(),R.layout.simple_spinner_item, db.getModelo());
        //arrayAdapter1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinnerModelo.setAdapter(arrayAdapter2);

        //preenchendo Spinner
        spinnerTensao = (Spinner) findViewById(R.id.spinnerTensao);
        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(getApplicationContext(),R.layout.simple_spinner_item, db.getTencaoTomada());
        //arrayAdapter1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinnerTensao.setAdapter(arrayAdapter3);

        //preenchendo Spinner
        spinnerLvlEcon = (Spinner) findViewById(R.id.spinnerLvlEcon);
        ArrayAdapter<String> arrayAdapter4 = new ArrayAdapter<String>(getApplicationContext(),R.layout.simple_spinner_item, db.getNvEcon());
        //arrayAdapter1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinnerLvlEcon.setAdapter(arrayAdapter4);

        //preenchendo Spinner
        spinnerBTU = (Spinner) findViewById(R.id.spinnerBTU);
        ArrayAdapter<String> arrayAdapter5 = new ArrayAdapter<String>(getApplicationContext(),R.layout.simple_spinner_item, db.getBTU());
        //arrayAdapter1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinnerBTU.setAdapter(arrayAdapter5);


        checkSaidaAr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkSaidaAr.isChecked()){
                    checkSaidaAr.setText("Sim");
                }else checkSaidaAr.setText("Não");
            }
        });
        checkBoxExaustor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBoxExaustor.isChecked()){
                    checkBoxExaustor.setText("Sim");
                    isExastor = true;
                }else {
                    checkBoxExaustor.setText("Não");
                    isExastor = false;
                }
            }
        });
        checkBoxControl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBoxControl.isChecked()){
                    checkBoxControl.setText("Sim");
                    isControl =true;
                }else {
                    checkBoxControl.setText("Não");
                    isControl = false;
                }
            }
        });
        checkTimer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkTimer.isChecked()){
                    checkTimer.setText("Sim");
                    isTimer = true;
                }else {
                    checkTimer.setText("Não");
                    isTimer = false;
                }
            }
        });

        //Setando Valores nas View

        refrigerador  = new RefrigeradorCtrl();
        refrigerador = RefrigeradorTabActivity.PlaceholderFragment.refrigeradorSelected;

        if(refrigerador.getHas_exaustor() == 1) checkBoxExaustor.setChecked(true);
        else checkBoxExaustor.setChecked(false);

        if(refrigerador.getHas_control() == 1) checkBoxControl.setChecked(true);
        else checkBoxControl.setChecked(false);

        if(refrigerador.getHas_timer() == 1) checkTimer.setChecked(true);
        else checkTimer.setChecked(false);

        checkSaidaAr.setChecked(false);

            spinnerMarca.setSelection(refrigerador.getMarca() - 1);
            spinnerModelo.setSelection(refrigerador.getTipo_modelo() - 1);
            spinnerTensao.setSelection(refrigerador.getTencao_tomada() - 1);
            spinnerLvlEcon.setSelection(refrigerador.getNivel_econo() - 1);
            spinnerBTU.setSelection(refrigerador.getCapaci_termica() - 1);

            editTamanho.setText(refrigerador.getTamanho());
            editTempoUso.setText("" + refrigerador.getTemp_uso());
            editPeso.setText("" + refrigerador.getPeso());
            editLotacionamento.setText("" + refrigerador.getLotacionamento());

        checkBoxExaustor.setEnabled(false);
        checkBoxControl.setEnabled(false);
        checkTimer.setEnabled(false);
        checkSaidaAr.setEnabled(false);
        spinnerMarca.setEnabled(false);
        spinnerModelo.setEnabled(false);
        spinnerTensao.setEnabled(false);
        spinnerLvlEcon.setEnabled(false);
        spinnerBTU.setEnabled(false);
        editTamanho.setEnabled(false);
        editTempoUso.setEnabled(false);
        editPeso.setEnabled(false);
        editLotacionamento.setEnabled(false);


        LinearLayout focus = (LinearLayout) findViewById(R.id.focus_detal_ar);
        focus.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tolbar_detalhe_refri, menu);

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
        if (id == R.id.action_editar_refri) {
            checkBoxExaustor.setEnabled(true);
            checkBoxControl.setEnabled(true);
            checkTimer.setEnabled(true);
            checkSaidaAr.setEnabled(true);
            spinnerMarca.setEnabled(true);
            spinnerModelo.setEnabled(true);
            spinnerTensao.setEnabled(true);
            spinnerLvlEcon.setEnabled(true);
            spinnerBTU.setEnabled(true);
            editTamanho.setEnabled(true);
            editTempoUso.setEnabled(true);
            editPeso.setEnabled(true);
            editLotacionamento.setEnabled(true);


        }if (id == R.id.action_salvar_refri) {
            if(checkBoxExaustor.isEnabled() || spinnerMarca.isEnabled() || editLotacionamento.isEnabled()) {
                RefrigeradorCtrl arCli = new RefrigeradorCtrl();

                arCli.setId_refri(refrigerador.getId_refri());
                arCli.setPeso(Integer.parseInt(editPeso.getText().toString()));
                if (isControl) arCli.setHas_control(1);
                else arCli.setHas_control(0);
                if (isExastor) arCli.setHas_exaustor(1);
                else arCli.setHas_exaustor(0);
                if (isTimer) arCli.setHas_timer(1);
                else arCli.setHas_timer(0);
                arCli.setSaida_ar("");
                arCli.setCapaci_termica(spinnerBTU.getSelectedItemPosition() + 1);
                arCli.setTencao_tomada(spinnerTensao.getSelectedItemPosition() + 1);
                arCli.setTipo_modelo(spinnerModelo.getSelectedItemPosition() + 1);
                arCli.setMarca(spinnerMarca.getSelectedItemPosition() + 1);
                arCli.setTemp_uso(Double.valueOf(editTempoUso.getText().toString()));
                arCli.setNivel_econo(spinnerLvlEcon.getSelectedItemPosition() + 1);
                arCli.setTamanho(editTamanho.getText().toString());
                arCli.setId_cliente(db.getUserDetails().getId());
                arCli.setLotacionamento(editLotacionamento.getText().toString());

                editarArCliBD(arCli);
            }else Toast.makeText(getApplicationContext(),"NEM UMA MODIFICAÇÃO FEITA!!!", Toast.LENGTH_LONG).show();
        }

        return true;
    }


    public void editarArCliBD(final RefrigeradorCtrl arCli){
        // Tag used to cancel the request
        String tag_string_req = "req_inserir_Refrigerador";

        pDialog.setTitle("Salvando Refrigerador");
        showDialog();

        db = new SQLiteHandler(getApplicationContext());
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_ALTERAR_DESCRI_AR, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // Log.d(TAG, "Carregando dados: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {

                        Log.e("UPDATE REFRIGERADOR: ", "Ar Atualizado com sucesso");
                        Log.e("Codigo Ar Return: ",""+arCli.getId_refri());
                        db.deleteUniRefrigera(arCli.getId_refri());

                        db.addRefrigerador(arCli);


                        hideDialog();
                        finish();

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_list");
                        Toast.makeText(getApplicationContext(), " "+errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Log.e("ERRORRRRR","Json error: " + e.getMessage());
                    Toast.makeText(getApplicationContext(), "Erro ao Salvar dados " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Lista Service Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        ""+error, Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_ar", ""+arCli.getId_refri());
                params.put("peso", ""+arCli.getPeso());
                params.put("has_control", ""+arCli.getHas_control());
                params.put("has_exaustor", ""+arCli.getHas_exaustor());
                params.put("saida_ar", ""+arCli.getSaida_ar());
                params.put("capaci_termica", ""+arCli.getCapaci_termica());
                params.put("tencao_tomada", ""+arCli.getTencao_tomada());
                params.put("has_timer", ""+arCli.getHas_timer());
                params.put("tipo_modelo", ""+arCli.getTipo_modelo());
                params.put("marca", ""+arCli.getMarca());
                params.put("temp_uso", ""+arCli.getTemp_uso());
                params.put("nivel_econo", ""+arCli.getNivel_econo());
                params.put("tamanho", ""+arCli.getTamanho());
                params.put("lotacionamento", ""+arCli.getLotacionamento());
                params.put("foto1", "");
                params.put("foto2", "");
                params.put("foto3", "");
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
