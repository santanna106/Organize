package com.example.organize.dao;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public abstract class DaoBase {
    protected static FirebaseDatabase firebase;
    protected DatabaseReference databaseReference;

    public DaoBase(){
        if(firebase == null){
            firebase = FirebaseDatabase.getInstance();
        }
    }
}
