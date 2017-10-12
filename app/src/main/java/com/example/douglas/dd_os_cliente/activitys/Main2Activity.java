package com.example.douglas.dd_os_cliente.activitys;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import com.example.douglas.dd_os_cliente.R;

public class Main2Activity extends Activity implements View.OnClickListener{

    private WebView wv;
    private EditText et;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        wv = (WebView) findViewById(R.id.webView1);
        et = (EditText) findViewById(R.id.editText1);
        btn  = (Button) findViewById(R.id.button1);

        //wv.getSettings().setJavaScriptEnabled(true);
        wv.setWebViewClient(new WebViewClient());

        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        wv.loadUrl(et.getText().toString());
    }
}
