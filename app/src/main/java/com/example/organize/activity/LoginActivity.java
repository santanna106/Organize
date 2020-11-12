package com.example.organize.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.organize.R;
import com.example.organize.activity.config.ConfiguracaoFireBase;
import com.example.organize.activity.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class LoginActivity extends AppCompatActivity {

    private EditText campoEmail,campoSenha;
    private Button btnEntrar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login );

        campoEmail = findViewById(R.id.editLogin);
        campoSenha = findViewById(R.id.editSenhaLogin);

        btnEntrar = findViewById(R.id.btnEntrar);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textoEmail = campoEmail.getText().toString();
                String textoSenha = campoSenha.getText().toString();

                if(!textoEmail.isEmpty()){
                    if(!textoSenha.isEmpty()){
                        usuario = new Usuario();
                        usuario.setEmail(textoEmail);
                        usuario.setSenha(textoSenha);
                        validarLogin();
                    } else {
                        Toast.makeText(LoginActivity.this,"Preencha a senha!",Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(LoginActivity.this,"Preencha o email!",Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public void validarLogin(){
        autenticacao = ConfiguracaoFireBase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(usuario.getEmail(),usuario.getSenha())
          .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {

              if(task.isSuccessful()){
                 abrirTelaPrincipal();
              } else {
                  String excecao = "";
                  try {
                      throw task.getException();
                  }catch(FirebaseAuthInvalidUserException e){
                      excecao = "Usuário não cadastrado";
                  }catch(FirebaseAuthInvalidCredentialsException e) {
                      excecao = "Email e Senha não correspodem a um usuário cadastrado!";
                  }catch (Exception e){
                      excecao = "Erro ao fazer o login: " + e.getMessage();
                      e.printStackTrace();
                  }
                  Toast.makeText(LoginActivity.this,excecao,Toast.LENGTH_LONG).show();
              }
          }
        });
    }

    public void abrirTelaPrincipal(){
        startActivity(new Intent(this,PrincipalActivity.class));
        finish();
    }
}