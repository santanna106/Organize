package com.example.organize.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.organize.activity.config.ConfiguracaoFireBase;
import com.example.organize.activity.model.Usuario;
import com.example.organize.dao.IObserver;
import com.example.organize.dao.UsuarioDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.organize.R;
import com.google.firebase.auth.FirebaseAuth;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.DecimalFormat;

public class PrincipalActivity extends AppCompatActivity implements IObserver<Usuario> {

    private MaterialCalendarView calendarView;
    private TextView textoSaudacao, textoSaldo;
    private FirebaseAuth autenticacao;
    private Usuario usuario;
    private double receitaTotal;
    private double despesaTotal;
    private double resumo;
    private UsuarioDao usuarioDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Organize");
        setSupportActionBar(toolbar);

        calendarView = findViewById(R.id.calendarView);
        textoSaudacao = findViewById(R.id.textSaudacao);
        textoSaldo = findViewById(R.id.textSaldo);
        configuraCalendarView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperarUsuario();

    }

    public void adicionarDespesa(View view){

        startActivity(new Intent(this,DespesaActivity.class));

    }

    public void configuraCalendarView(){
        CharSequence meses[] = {"Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"};
        calendarView.setTitleMonths(meses);

        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

            }
        });
    }


    public void adicionarReceita(View view){

        startActivity(new Intent(this,ReceitaActivity.class));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuSair:
                autenticacao = ConfiguracaoFireBase.getFirebaseAutenticacao();
                autenticacao.signOut();
                startActivity(new Intent(this,MainActivity.class));
                finish();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onEvent(Usuario data) {
        this.usuario = data;
        this.textoSaudacao.setText("Olá, " + this.usuario.getNome());
        DecimalFormat decimalFormat = new DecimalFormat("0.##");
        String resultadoFormatado = "R$ " + decimalFormat.format(this.usuario.getReceitaTotal() - this.usuario.getDespesaTotal());
        this.textoSaldo.setText(resultadoFormatado);
    }

    public void recuperarUsuario() {
        String idUsuario = ConfiguracaoFireBase.getFirebaseAutenticacao().getCurrentUser().getUid();
        usuarioDao = new UsuarioDao();
        usuarioDao.buscar(this,idUsuario);

    }

    @Override
    protected void onStop() {
        super.onStop();
        usuarioDao.destroy();

    }
}