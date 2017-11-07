package com.example.douglas.dd_os_cliente.controler;

import java.io.Serializable;

/**
 * Created by Douglas on 06/07/2017.
 */

public class UserClienteCtrl implements Serializable {

    private static final long serialVersionUID = -2229832341556924673L;
    private int id;
    private String name;
    private int cpf_cnpj;
    private String email;
    private String uid;
    private String tipo_cad;
    private String fone1;
    private String fone2;
    private String ender;
    private String bairro;
    private String point_ref;
    private String senha;
    private int cep;
    private String created_at;
    private String update_at;

    public UserClienteCtrl() {
    }

    public UserClienteCtrl(int id, String name, String email, int cpf_cnpj, String uid, String created_at, String fone1, String fone2,
                           String ender, String senha, String bairro, String point_ref, int cep, String update_at) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cpf_cnpj = cpf_cnpj;
        this.uid = uid;
        this.created_at = created_at;
        this.update_at = update_at;
        this.fone1 = fone1;
        this.fone2 = fone2;
        this.ender = ender;
        this.bairro = bairro;
        this.point_ref = point_ref;
        this.cep = cep;
        this.ender = ender;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCpf_cnpj() {
        return cpf_cnpj;
    }

    public void setCpf_cnpj(int cpf_cnpj) {
        this.cpf_cnpj = cpf_cnpj;
    }

    public String getTipo_cad() {
        return tipo_cad;
    }

    public void setTipo_cad(String tipo_cad) {
        this.tipo_cad = tipo_cad;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
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

    public String getEnder() {
        return ender;
    }

    public void setEnder(String ender) {
        this.ender = ender;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getPoint_ref() {
        return point_ref;
    }

    public void setPoint_ref(String point_ref) {
        this.point_ref = point_ref;
    }

    public int getCep() {
        return cep;
    }

    public void setCep(int cep) {
        this.cep = cep;
    }
}

