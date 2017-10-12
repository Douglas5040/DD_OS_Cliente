package com.example.douglas.dd_os_cliente.controler;

import java.io.Serializable;

/**
 * Created by Douglas on 06/07/2017.
 */

public class PecsCtrl implements Serializable {

    private static final long serialVersionUID = -2229832341556924673L;
    private int id_pc;
    private String nome;
    private String modelo;
    private String marca;

    public PecsCtrl(){}
    public PecsCtrl(int id_pc, String nome, String modelo, String marca){
        this.id_pc = id_pc;
        this.nome = nome;
        this.modelo = modelo;
        this.marca = marca;
    }

    public int getId_pc() {
        return id_pc;
    }

    public void setId_pc(int id_pc) {
        this.id_pc = id_pc;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
}
