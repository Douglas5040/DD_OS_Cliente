package com.example.douglas.dd_os_cliente.controler;

import java.io.Serializable;

/**
 * Created by Douglas on 04/07/2017.
 */

public class ServPendenteCtrl implements Serializable {
    private static final long serialVersionUID = -2229832341556924673L;

    private int id_serv_pen;
    private double latitude;
    private double longitude;
    private int cliente_id;
    private String lotacionamento;
    private String ender;
    private String complemento;
    private String cep;
    private String data_serv;
    private String hora_serv;
    private String descri_cli_problem;
    private String descri_tecni_problem;
    private String descri_cli_refrigera;
    private String status_serv;
    private String nomeCli;
    private String tipoCli;
    private String fone1;
    private String fone2;
    private int id_refriCli;
    private int tipoManu;

    public ServPendenteCtrl(){}
    public ServPendenteCtrl(int id_serv_pen, double latitude, double longitude, int cliente_id, String lotacionamento, String ender, String complemento, String cep, String fone1, String fone2,
                            String data_serv, int tipoManu, String hora_serv, String descri_cli_problem, int id_refriCli,  String descri_tecni_problem, String descri_cli_refrigera, String status_serv, String nomeCli, String tipoCli){

        this.id_serv_pen = id_serv_pen;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cliente_id = cliente_id;
        this.lotacionamento = lotacionamento;
        this.ender = ender;
        this.complemento = complemento;
        this.cep = cep;
        this.data_serv = data_serv;
        this.hora_serv = hora_serv;
        this.descri_cli_problem = descri_cli_problem;
        this.descri_tecni_problem = descri_tecni_problem;
        this.descri_cli_refrigera = descri_cli_refrigera;
        this.status_serv = status_serv;
        this.nomeCli = nomeCli;
        this.tipoCli = tipoCli;
        this.fone1 = fone1;
        this.fone2 = fone2;
        this.id_refriCli = id_refriCli;
        this.tipoManu = tipoManu;
    }

    public int getTipoManu() {
        return tipoManu;
    }

    public void setTipoManu(int tipoManu) {
        this.tipoManu = tipoManu;
    }

    public int getId_refriCli() {
        return id_refriCli;
    }

    public void setId_refriCli(int id_refriCli) {
        this.id_refriCli = id_refriCli;
    }

    public String getFone1() {
        return fone1;
    }

    public void setFone1(String fone1) {
        this.fone1 = fone1;
    }

    public String getFone2() {
        return fone2;
    }

    public void setFone2(String fone2) {
        this.fone2 = fone2;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNomeCli() {
        return nomeCli;
    }

    public void setNomeCli(String nomeCli) {
        this.nomeCli = nomeCli;
    }

    public String getTipoCli() {
        return tipoCli;
    }

    public void setTipoCli(String tipoCli) {
        this.tipoCli = tipoCli;
    }

    public int getId_serv_pen() {
        return id_serv_pen;
    }

    public void setId_serv_pen(int id_serv_pen) {
        this.id_serv_pen = id_serv_pen;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }

    public String getLotacionamento() {
        return lotacionamento;
    }

    public void setLotacionamento(String lotacionamento) {
        this.lotacionamento = lotacionamento;
    }

    public String getEnder() {
        return ender;
    }

    public void setEnder(String ender) {
        this.ender = ender;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getData_serv() {
        return data_serv;
    }

    public void setData_serv(String data_serv) {
        this.data_serv = data_serv;
    }

    public String getHora_serv() {
        return hora_serv;
    }

    public void setHora_serv(String hora_serv) {
        this.hora_serv = hora_serv;
    }

    public String getDescri_cli_problem() {
        return descri_cli_problem;
    }

    public void setDescri_cli_problem(String desvri_cli_problem) {
        this.descri_cli_problem = desvri_cli_problem;
    }

    public String getDescri_tecni_problem() {
        return descri_tecni_problem;
    }

    public void setDescri_tecni_problem(String descri_tecni_problem) {
        this.descri_tecni_problem = descri_tecni_problem;
    }

    public String getDescri_cli_refrigera() {
        return descri_cli_refrigera;
    }

    public void setDescri_cli_refrigera(String descri_cli_refrigera) {
        this.descri_cli_refrigera = descri_cli_refrigera;
    }

    public String getStatus_serv() {
        return status_serv;
    }

    public void setStatus_serv(String status_serv) {
        this.status_serv = status_serv;
    }
}
