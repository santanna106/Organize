package com.example.organize.dao;

import com.example.organize.activity.config.ConfiguracaoFireBase;
import com.example.organize.activity.model.Movimentacao;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MovimentacaoDao extends DaoBase implements IDao<Movimentacao> {

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

    }
}
