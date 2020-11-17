package com.example.organize.activity.model;

import com.example.organize.activity.config.ConfiguracaoFireBase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Usuario {
    private String idUsuario;
    private String nome;
    private String email;
    private String senha;
    private double receitaTotal = 0.00;
    private double despesaTotal = 0.00;

    public double getReceitaTotal() {
        return receitaTotal;
    }

    public void setReceitaTotal(double receitaTotal) {
        this.receitaTotal = receitaTotal;
    }

    public double getDespesaTotal() {
        return despesaTotal;
    }

    public void setDespesaTotal(double despesaTotal) {
        this.despesaTotal = despesaTotal;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuario() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

     public void salvar(){
        DatabaseReference firebase = ConfiguracaoFireBase.getFirebase("usuarios");
        firebase.setValue(this.getIdUsuario());
         DatabaseReference usersRef =firebase.child(this.getIdUsuario());
         usersRef.setValue(this);



    }
}
