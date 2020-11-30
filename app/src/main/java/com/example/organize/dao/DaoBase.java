package com.example.organize.dao;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public abstract class DaoBase<T> implements IDao<T>  {
    protected static FirebaseDatabase firebase;
    protected DatabaseReference databaseReference;
    protected ValueEventListener eventListenerUsuario;
    protected ValueEventListener eventListenerMovimentacao;

    public static FirebaseDatabase getDatabase(){
        if(firebase == null){
            firebase = FirebaseDatabase.getInstance();
        }


        return firebase;

    }
    public DaoBase(){
        if(firebase == null){
            firebase = FirebaseDatabase.getInstance();
        }
    }
}
