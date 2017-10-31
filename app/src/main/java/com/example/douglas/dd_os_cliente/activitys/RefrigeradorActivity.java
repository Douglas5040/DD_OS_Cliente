package com.example.douglas.dd_os_cliente.activitys;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.douglas.dd_os_cliente.R;

public class RefrigeradorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refrigerador);

        Intent i = new Intent(getApplicationContext(), ServicesTabActivity.class);  //your class

        startActivity(i);
    }
}
