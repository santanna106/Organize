package com.example.organize.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.organize.R;
import com.example.organize.activity.config.ConfiguracaoFireBase;
import com.example.organize.activity.helper.Base64Custom;
import com.example.organize.activity.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;

public class MainActivity extends IntroActivity {

    private FirebaseAuth autenticacao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setButtonNextVisible(false);
        setButtonBackVisible(false);

        addSlide(new FragmentSlide.Builder()
                .background(R.color.white)
                .canGoBackward(false)
                .fragment(R.layout.intro_um)
                .build());


        addSlide(new FragmentSlide.Builder()
                .background(R.color.white)
                .fragment(R.layout.intro_dois)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(R.color.white)
                .fragment(R.layout.intro_tres)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(R.color.white)
                .fragment(R.layout.intro_quatro)

                .build());

        addSlide(new FragmentSlide.Builder()
                .background(R.color.white)
                .fragment(R.layout.intro_cadastro)
                .canGoForward(false)
                .build());



    }

    @Override
    protected void onStart() {
        super.onStart();
        verificarUsuarioLogado();
    }

    public void btEntrar(View view){
        startActivity(new Intent(this,LoginActivity.class));
    }

    public void btCadastrar(View view){
        startActivity(new Intent(this,CadastroActivity.class));
    }

    public void verificarUsuarioLogado(){
        autenticacao = ConfiguracaoFireBase.getFirebaseAutenticacao();
        //autenticacao.signOut();

        if(autenticacao.getCurrentUser() != null){
            abrirTelaPrincipal();
        }

    }

    public void abrirTelaPrincipal(){
        startActivity(new Intent(this,PrincipalActivity.class));
    }
}