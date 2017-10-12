package com.example.douglas.dd_os_cliente.controler;

import java.io.Serializable;

/**
 * Created by Douglas on 06/07/2017.
 */

public class ServicesCtrl implements Serializable {

    private static final long serialVersionUID = -2229832341556924673L;
    private int id_service;
    private String nome;
    private String descri;
    private String tempo;

    public ServicesCtrl(){}
    public ServicesCtrl(int id_service, String nome, String descri, String tempo){
        this.id_service = id_service;
        this.nome = nome;
        this.descri = descri;
        this.tempo = tempo;
    }

    public int getId_service() {
        return id_service;
    }

    public void setId_service(int id_service) {
        this.id_service = id_service;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }
}
