package com.example.douglas.dd_os_cliente.app;


import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Douglas on 18/09/2017.
 */

public class DataHoraNow {
    private SimpleDateFormat dateHoraFormat = new SimpleDateFormat("dd_MM_yyyy-HH_mm_ss");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
    private SimpleDateFormat horaFormat = new SimpleDateFormat("HH_mm_ss");
    private Date data = new Date();

    private Calendar cal = new Calendar() {
        @Override
        protected void computeTime() {

        }

        @Override
        protected void computeFields() {

        }

        @Override
        public void add(int i, int i1) {

        }

        @Override
        public void roll(int i, boolean b) {

        }

        @Override
        public int getMinimum(int i) {
            return 0;
        }

        @Override
        public int getMaximum(int i) {
            return 0;
        }

        @Override
        public int getGreatestMinimum(int i) {
            return 0;
        }

        @Override
        public int getLeastMaximum(int i) {
            return 0;
        }
    };

    public DataHoraNow(){}

    public String getDataHoraNow(){
        cal.setTime(data);
        Date data_atual = cal.getTime();

        String data_completa = dateHoraFormat.format(data_atual);
        Log.e("DataHoraNow","data: "+data_completa);
        return data_completa;
    }

    public String getDataNow(){
        cal.setTime(data);
        Date data_atual = cal.getTime();

        String data_completa = dateFormat.format(data_atual);
        Log.e("DataHoraNow","data: "+data_completa);
        return data_completa;
    }

    public String getHoraNow(){
        cal.setTime(data);
        Date data_atual = cal.getTime();

        String hora_atual = horaFormat.format(data_atual);
        Log.e("DataHoraNow","data: "+hora_atual);
        return hora_atual;
    }
}
