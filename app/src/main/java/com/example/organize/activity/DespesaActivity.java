package com.example.organize.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.organize.R;
import com.example.organize.activity.config.ConfiguracaoFireBase;
import com.example.organize.activity.helper.DataUtil;
import com.example.organize.activity.model.Movimentacao;
import com.example.organize.activity.model.Usuario;
import com.example.organize.dao.IObserver;
import com.example.organize.dao.MovimentacaoDao;
import com.example.organize.dao.UsuarioDao;
import com.example.organize.mapper.UsuarioMapper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;
import java.util.Map;

public class DespesaActivity extends AppCompatActivity implements IObserver<Usuario> {

    private TextInputEditText campoData,campoCategoria,campoDescricao;
    private EditText campoValor;
    private Movimentacao movimentacao;
    private double despesaTotal;
    private double despesaGerada;
    private double despesaAtualizada;
    private TextView txtNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesa);

        campoData = findViewById(R.id.editDataDespesa);
        campoCategoria = findViewById(R.id.editCategoriaDespesa);
        campoValor = findViewById(R.id.editValorDespesa);
        campoDescricao = findViewById(R.id.editDescricaoDespesa);

        campoData.setText(DataUtil.dataAtual());

        txtNome = findViewById(R.id.txtNome);

        recuperarDespesaTotal();

    }

    public void salvarDespesa(View view){
        FirebaseAuth autenticacao = ConfiguracaoFireBase.getFirebaseAutenticacao();
        if(validarCamposDespesa()){
            double valorRecuperado = Double.parseDouble(campoValor.getText().toString());
            movimentacao = new Movimentacao();
            movimentacao.setValor(valorRecuperado);
            movimentacao.setCategoria(campoCategoria.getText().toString());
            movimentacao.setDescricao(campoDescricao.getText().toString());
            movimentacao.setData(campoData.getText().toString());
            movimentacao.setTipo("D");
            movimentacao.setIdUsuario(autenticacao.getUid());
            movimentacao.setMesAno(DataUtil.dataMesAno(campoData.getText().toString()));

            despesaGerada = valorRecuperado;
            despesaAtualizada = despesaTotal + despesaGerada;



            MovimentacaoDao dao = new MovimentacaoDao();

            dao.salvar(movimentacao);


        }

    }

    public Boolean validarCamposDespesa(){
        String textoValor = campoValor.getText().toString();
        String textoData = campoData.getText().toString();
        String textoCategoria = campoCategoria.getText().toString();
        String textoDescricao = campoDescricao.getText().toString();
        if(!textoValor.isEmpty()){
            if(!textoData.isEmpty()){
                if(!textoCategoria.isEmpty()){
                    if(!textoDescricao.isEmpty()){
                        return true;
                    } else {
                        Toast.makeText(DespesaActivity.this,"Descrição não preenchida!",Toast.LENGTH_LONG).show();
                        return false;
                    }

                } else {
                    Toast.makeText(DespesaActivity.this,"Categoria não preenchida!",Toast.LENGTH_LONG).show();
                    return false;
                }

            } else {
                Toast.makeText(DespesaActivity.this,"Data não preenchida!",Toast.LENGTH_LONG).show();
                return false;
            }

        } else {
            Toast.makeText(DespesaActivity.this,"Valor não preenchido!",Toast.LENGTH_LONG).show();
            return false;
        }

    }

    public void recuperarDespesaTotal() {
        String idUsuario = ConfiguracaoFireBase.getFirebaseAutenticacao().getCurrentUser().getUid();
        UsuarioDao dao = new UsuarioDao();
        dao.buscar(this,idUsuario);

        //

    }

    public void atualizarDespesa(double despesaAtualizada){
        String idUsuario = ConfiguracaoFireBase.getFirebaseAutenticacao().getCurrentUser().getUid();
        UsuarioDao dao = new UsuarioDao();
        dao.buscar(this,idUsuario);

    }

    @Override
    public void onEvent(Usuario data) {
        txtNome.setText(data.getNome());
    }
}