package com.example.organize.dao;

import android.util.Log;

import com.example.organize.activity.model.Movimentacao;
import com.example.organize.activity.model.Usuario;
import com.example.organize.adapter.AdapterMovimentacao;
import com.example.organize.mapper.MovimentacaoMapper;
import com.example.organize.mapper.UsuarioMapper;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MovimentacaoDao extends DaoBase<Movimentacao> implements IMovimentacaoDao<Movimentacao> {
    List<Movimentacao> movimentacoes;
    public List<Movimentacao> getMovimentacoes() {
        return movimentacoes;
    }

    public void setMovimentacoes(List<Movimentacao> movimentacoes) {
        this.movimentacoes = movimentacoes;
    }



    public MovimentacaoDao() {

        databaseReference = firebase.getReference("movimentacao");
    }

    @Override
    public void salvar(Movimentacao movimentacao) {
        databaseReference.push().setValue(movimentacao);
    }


    @Override
    public void delete(Movimentacao o) {

    }

    @Override
    public void update(Movimentacao o) {
        databaseReference.child(o.getId()).setValue(o);
    }


    @Override
    public void destroy() {
        if (eventListenerUsuario != null) {
            databaseReference.removeEventListener(eventListenerUsuario);
        }

    }

    @Override
    public List<Movimentacao> lista() {
        return null;
    }

    @Override
    public void buscar(IObserverMovimentaUsuario<Usuario, Movimentacao> observer, String id) {

    }


    @Override
    public void lista(IObserverMovimentaUsuario<Usuario, Movimentacao> observer, AdapterMovimentacao adapter) {

        eventListenerMovimentacao = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot movimentacaoSnapshot : dataSnapshot.getChildren()) {

                    Map<String, Object> map = (Map<String, Object>) movimentacaoSnapshot.getValue();
                    //Log.i("KEY: ", movimentacaoSnapshot.getKey());
                    //Log.i("KEY: ", map.toString());
                    MovimentacaoMapper mMapper = new MovimentacaoMapper();
                    Movimentacao movimentacao = mMapper.toObject(movimentacaoSnapshot, map);
                    movimentacoes.add(movimentacao);
                    //Log.i("MovimentoMapper: ",movimentacao.toString());
                }
                //Log.i("MOVRECYCLE LISTA: ",movimentacoes.toString());
                // observer.onEventLoadListaMovimentacao(movimentacoes);
                //adapter.notifyDataSetChanged();
                // Log.i("MOVRECYCLE: LISTA ", movimentacoes.toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Log.w("ERROR", "Failed to read value.", databaseError.toException());
            }
        });


    }

    @Override
    public void lista(IObserverMovimentaUsuario<Usuario, Movimentacao> observer) {
        eventListenerMovimentacao = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                movimentacoes = new ArrayList<Movimentacao>();
                for (DataSnapshot movimentacaoSnapshot : dataSnapshot.getChildren()) {

                    Map<String, Object> map = (Map<String, Object>) movimentacaoSnapshot.getValue();
                    //Log.i("KEY: ", movimentacaoSnapshot.getKey());
                    //Log.i("KEY: ", map.toString());
                    MovimentacaoMapper mMapper = new MovimentacaoMapper();
                    Movimentacao movimentacao = mMapper.toObject(movimentacaoSnapshot, map);
                    movimentacoes.add(movimentacao);
                    //Log.i("MovimentoMapper: ",movimentacao.toString());
                }
                //Log.i("MOVRECYCLE LISTA: ",movimentacoes.toString());
                // observer.onEventLoadListaMovimentacao(movimentacoes);
                //adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Log.w("ERROR", "Failed to read value.", databaseError.toException());
            }
        });


    }





}
