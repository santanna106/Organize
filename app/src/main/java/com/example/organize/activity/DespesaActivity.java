package com.example.organize.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.organize.R;
import com.example.organize.activity.config.ConfiguracaoFireBase;
import com.example.organize.activity.helper.DataUtil;
import com.example.organize.activity.model.Movimentacao;
import com.example.organize.dao.MovimentacaoDao;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class DespesaActivity extends AppCompatActivity {

    private TextInputEditText campoData,campoCategoria,campoDescricao;
    private EditText campoValor;
    private Movimentacao movimentacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesa);

        campoData = findViewById(R.id.editDataDespesa);
        campoCategoria = findViewById(R.id.editCategoriaDespesa);
        campoValor = findViewById(R.id.editValorDespesa);
        campoDescricao = findViewById(R.id.editDescricaoDespesa);

        campoData.setText(DataUtil.dataAtual());

    }

    public void salvarDespesa(View view){
        FirebaseAuth autenticacao = ConfiguracaoFireBase.getFirebaseAutenticacao();

        movimentacao = new Movimentacao();
        movimentacao.setValor(Double.parseDouble(campoValor.getText().toString()));
        movimentacao.setCategoria(campoCategoria.getText().toString());
        movimentacao.setDescricao(campoDescricao.getText().toString());
        movimentacao.setData(campoData.getText().toString());
        movimentacao.setTipo("D");
        movimentacao.setIdUsuario(autenticacao.getUid());

        MovimentacaoDao dao = new MovimentacaoDao();

        dao.salvar(movimentacao);

    }
}