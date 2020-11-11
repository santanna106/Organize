package com.example.organize.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.organize.R;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;

public class MainActivity extends IntroActivity {

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

    public void btEntrar(View view){
        startActivity(new Intent(this,LoginActivity.class));
    }

    public void btCadastrar(View view){
        startActivity(new Intent(this,CadastroActivity.class));
    }
}