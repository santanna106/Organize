package com.example.organize.dao;

import android.util.Log;

import com.example.organize.activity.model.Movimentacao;
import com.example.organize.activity.model.Usuario;
import com.example.organize.mapper.UsuarioMapper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Map;

public class UsuarioDao extends DaoBase <Usuario> implements IUsuarioDao<Usuario>  {

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


    public void buscar(IObserver<Usuario> observer, String id) {

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
    public void buscar(IObserverMovimentaUsuario<Usuario, Movimentacao> observer, String id) {

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
                        observer.onEventLoadUsuario(usuarioEncontrado);


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

    @Override
    public List<Usuario> lista() {
        return null;
    }


}
