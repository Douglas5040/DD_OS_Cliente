package com.example.douglas.dd_os_cliente.controler;

import java.io.Serializable;

/**
 * Created by Douglas on 06/07/2017.
 */

public class UserFuncionarioCtrl implements Serializable {

    private static final long serialVersionUID = -2229832341556924673L;
    private int id;
    private String name;
    private String email;
    private String matricula;
    private String uid;
    private String created_at;

    public UserFuncionarioCtrl(){}
    public UserFuncionarioCtrl(int id, String name, String email, String matricula, String uid, String created_at){
        this.id = id;
        this.name = name;
        this.email = email;
        this.matricula = matricula;
        this.uid = uid;
        this.created_at = created_at;
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

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
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
}
