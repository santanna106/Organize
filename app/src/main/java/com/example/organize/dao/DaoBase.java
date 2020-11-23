package com.example.organize.dao;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public abstract class DaoBase<T>  {
    protected static FirebaseDatabase firebase;
    protected DatabaseReference databaseReference;
    protected ValueEventListener eventListenerUsuario;

    public DaoBase(){
        if(firebase == null){
            firebase = FirebaseDatabase.getInstance();
        }
    }
}
