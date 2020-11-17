package com.example.organize.dao;

import com.example.organize.activity.config.ConfiguracaoFireBase;
import com.example.organize.activity.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UsuarioDao extends DaoBase implements IDao<Usuario> {

    public UsuarioDao(){

        databaseReference = firebase.getReference("usuarios");
    }

    @Override
    public void salvar(Usuario usuario) {
        databaseReference.push().setValue(usuario);
    }

    @Override
    public void delete(Usuario usuario) {

    }

    @Override
    public void update(Usuario usuario) {

    }
}
