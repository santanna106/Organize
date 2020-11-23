package com.example.organize.dao;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.organize.activity.config.ConfiguracaoFireBase;
import com.example.organize.activity.model.Usuario;
import com.example.organize.mapper.UsuarioMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static android.content.ContentValues.TAG;

public class UsuarioDao extends DaoBase <Usuario> implements IUsuarioDao<Usuario> {

    private Usuario usuario;


    public UsuarioDao(){

        databaseReference = firebase.getReference("usuarios");
        Log.i("EVETO ","Evento Iniciado");
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
        databaseReference.child(usuario.getId()).setValue(usuario);
    }

    @Override
    public void buscar(IObserver<Usuario> observer,String id) {
        //Usuario usuarioEncontrado = null;

        eventListenerUsuario =  databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot usuarioSnapshot : dataSnapshot.getChildren()) {

                    Map<String, Object> map = (Map<String, Object>) usuarioSnapshot.getValue();
                    Log.d("KEY: ",usuarioSnapshot.getKey());
                    Log.d("KEY: ",map.toString());
                    UsuarioMapper uMapper = new UsuarioMapper();
                    Usuario usuarioEncontrado = uMapper.toObject(usuarioSnapshot,map);

                    if(usuarioEncontrado.getIdUsuario().equals(id)){
                        observer.onEvent(usuarioEncontrado);


                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Log.w("ERROR", "Failed to read value.", databaseError.toException());
            }
        });






    }

    @Override
    public void destroy() {
        if(eventListenerUsuario != null){
            databaseReference.removeEventListener(eventListenerUsuario);
            Log.i("EVETO ","Evento Removido");
        }
    }


}
