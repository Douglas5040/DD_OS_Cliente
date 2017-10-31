package com.example.douglas.dd_os_cliente.controler;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Douglas on 06/07/2017.
 */

public class RefrigeradorCtrl implements Serializable {

    private static final long serialVersionUID = -2229832341556924673L;
    private int id_refri;
    private int peso;
    private int has_control;
    private int has_exaustor;
    private String saida_ar;
    private int capaci_termica;
    private int tencao_tomada;
    private int has_timer;
    private int tipo_modelo;
    private int marca;
    private double temp_uso;
    private int nivel_econo;
    private String tamanho;
    private String lotacionamento;
    private byte[] foto1;
    private byte[] foto2;
    private byte[] foto3;
    private int id_cliente;

    public RefrigeradorCtrl(){}
    public RefrigeradorCtrl(int id_refri, int peso, int has_control, int has_exaustor, String saida_ar, int capaci_termica, int tencao_tomada, int id_cliente,
                            int has_timer, int tipo_modelo, int marca, double temp_uso, int nivel_econo, String tamanho, String lotacionamento, byte[] foto1, byte[] foto2, byte[] foto3){
        this.id_refri = id_refri;
        this.peso = peso;
        this.has_control = has_control;
        this.has_exaustor = has_exaustor;
        this.saida_ar = saida_ar;
        this.capaci_termica = capaci_termica;
        this.tencao_tomada = tencao_tomada;
        this.has_timer = has_timer;
        this.tipo_modelo = tipo_modelo;
        this.marca = marca;
        this.temp_uso = temp_uso;
        this.nivel_econo = nivel_econo;
        this.tamanho = tamanho;
        this.lotacionamento = lotacionamento;
        this.foto1 = foto1;
        this.foto2 = foto2;
        this.foto3 = foto3;
        this.id_cliente = id_cliente;
    }

    public String getLotacionamento() {
        return lotacionamento;
    }

    public void setLotacionamento(String lotacionamento) {
        this.lotacionamento = lotacionamento;
    }

    public int getId_refri() {
        return id_refri;
    }

    public void setId_refri(int id_refri) {
        this.id_refri = id_refri;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public int getHas_control() {
        return has_control;
    }

    public void setHas_control(int has_control) {
        this.has_control = has_control;
    }

    public int getHas_exaustor() {
        return has_exaustor;
    }

    public void setHas_exaustor(int has_exaustor) {
        this.has_exaustor = has_exaustor;
    }

    public String getSaida_ar() {
        return saida_ar;
    }

    public void setSaida_ar(String saida_ar) {
        this.saida_ar = saida_ar;
    }

    public int getCapaci_termica() {
        return capaci_termica;
    }

    public void setCapaci_termica(int capaci_termica) {
        this.capaci_termica = capaci_termica;
    }

    public int getTencao_tomada() {
        return tencao_tomada;
    }

    public void setTencao_tomada(int tencao_tomada) {
        this.tencao_tomada = tencao_tomada;
    }

    public int getHas_timer() {
        return has_timer;
    }

    public void setHas_timer(int has_timer) {
        this.has_timer = has_timer;
    }

    public int getTipo_modelo() {
        return tipo_modelo;
    }

    public void setTipo_modelo(int tipo_modelo) {
        this.tipo_modelo = tipo_modelo;
    }

    public int getMarca() {
        return marca;
    }

    public void setMarca(int marca) {
        this.marca = marca;
    }

    public double getTemp_uso() {
        return temp_uso;
    }

    public void setTemp_uso(double temp_uso) {
        this.temp_uso = temp_uso;
    }

    public int getNivel_econo() {
        return nivel_econo;
    }

    public void setNivel_econo(int nivel_econo) {
        this.nivel_econo = nivel_econo;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public byte[] getFoto1() {
        return foto1;
    }

    public void setFoto1(byte[] foto1) {
        this.foto1 = foto1;
    }

    public byte[] getFoto2() {
        return foto2;
    }

    public void setFoto2(byte[] foto2) {
        this.foto2 = foto2;
    }

    public byte[] getFoto3() {
        return foto3;
    }

    public void setFoto3(byte[] foto3) {
        this.foto3 = foto3;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    @Override
    public String toString() {
        return "RefrigeradorCtrl{" +
                "id_refri=" + id_refri +
                ", peso=" + peso +
                ", has_control=" + has_control +
                ", has_exaustor=" + has_exaustor +
                ", saida_ar='" + saida_ar + '\'' +
                ", capaci_termica=" + capaci_termica +
                ", tencao_tomada=" + tencao_tomada +
                ", has_timer=" + has_timer +
                ", tipo_modelo=" + tipo_modelo +
                ", marca=" + marca +
                ", temp_uso=" + temp_uso +
                ", nivel_econo=" + nivel_econo +
                ", tamanho='" + tamanho  +
                ", lotacionamento='" + lotacionamento + '\'' +
                ", foto1=" + Arrays.toString(foto1) +
                ", foto2=" + Arrays.toString(foto2) +
                ", foto3=" + Arrays.toString(foto3) +
                ", id_cliente=" + id_cliente +
                '}';
    }
}
