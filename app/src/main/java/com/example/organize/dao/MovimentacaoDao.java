package com.example.organize.dao;

import androidx.annotation.NonNull;

import com.example.organize.activity.config.ConfiguracaoFireBase;
import com.example.organize.activity.model.Movimentacao;
import com.example.organize.activity.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MovimentacaoDao extends DaoBase<Movimentacao> implements IMovimentacaoDao<Movimentacao> {

   public MovimentacaoDao(){

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
    public void buscar(IObserver<Movimentacao> observer, String id) {

    }




}
