package com.example.organize.activity.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfiguracaoFireBase {
    private static FirebaseAuth autenticacao;
    private static DatabaseReference firebase;

    public static FirebaseAuth getFirebaseAutenticacao(){

        if(autenticacao == null){
            autenticacao = FirebaseAuth.getInstance();
        }

        return autenticacao;

    }

    public static DatabaseReference getFirebase(String nmRef){
        if(firebase == null){
            firebase = FirebaseDatabase.getInstance().getReference(nmRef);

        }

        return firebase;
    }

}
