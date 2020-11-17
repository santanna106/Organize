package com.example.organize.dao;

import com.example.organize.activity.config.ConfiguracaoFireBase;
import com.example.organize.activity.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UsuarioDao implements IDao<Usuario> {
    private DatabaseReference firebase;

    @Override
    public void salvar(Usuario usuario) {
        firebase = ConfiguracaoFireBase.getFirebase("usuarios");
        firebase.push().setValue(usuario);
    }

    @Override
    public void delete(Usuario usuario) {

    }

    @Override
    public void update(Usuario usuario) {

    }
}
