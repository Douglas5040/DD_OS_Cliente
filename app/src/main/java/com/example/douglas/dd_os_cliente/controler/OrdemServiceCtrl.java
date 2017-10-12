package com.example.douglas.dd_os_cliente.controler;

import java.io.Serializable;

/**
 * Created by Douglas on 06/07/2017.
 */

public class OrdemServiceCtrl implements Serializable {

    private static final long serialVersionUID = -2229832341556924673L;
    private int id_os;
    private int id_cliente;
    private String matri_func;
    private String tipo_manu;
    private String obs;
    private String data;
    private String horaINI;
    private String horaFIN;

    public OrdemServiceCtrl(){}
    public OrdemServiceCtrl(int id_os, int id_cliente, String matri_func, String tipo_manu, String obs, String horaFIN, String data, String horaINI){
        this.id_os = id_os;
        this.id_cliente = id_cliente;
        this.matri_func = matri_func;
        this.tipo_manu = tipo_manu;
        this.obs = obs;
        this.horaFIN = horaFIN;
        this.horaINI = horaINI;
        this.data = data;
    }


    public int getId_os() {
        return id_os;
    }

    public void setId_os(int id_os) {
        this.id_os = id_os;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getMatri_func() {
        return matri_func;
    }

    public void setMatri_func(String matri_func) {
        this.matri_func = matri_func;
    }

    public String getTipo_manu() {
        return tipo_manu;
    }

    public void setTipo_manu(String tipo_manu) {
        this.tipo_manu = tipo_manu;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHoraINI() {
        return horaINI;
    }

    public void setHoraINI(String horaINI) {
        this.horaINI = horaINI;
    }

    public String getHoraFIN() {
        return horaFIN;
    }

    public void setHoraFIN(String horaFIN) {
        this.horaFIN = horaFIN;
    }
}
