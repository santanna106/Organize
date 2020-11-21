package com.example.organize.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.organize.R;
import com.example.organize.activity.config.ConfiguracaoFireBase;
import com.example.organize.activity.helper.DataUtil;
import com.example.organize.activity.model.Movimentacao;
import com.example.organize.activity.model.Usuario;
import com.example.organize.dao.IObserver;
import com.example.organize.dao.MovimentacaoDao;
import com.example.organize.dao.UsuarioDao;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ReceitaActivity extends AppCompatActivity implements IObserver<Usuario> {

    private Usuario usuario;
    private TextInputEditText editDataReceita,editTipoReceita,editDescricaoReceita;
    private EditText editValorReceita;
    private Movimentacao movimentacao;
    private double ReceitaGerada,ReceitaAtualizada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receita);
        editDataReceita = findViewById(R.id.editDataReceita);
        editDescricaoReceita = findViewById(R.id.editDescricaoReceita);
        editTipoReceita = findViewById(R.id.editTipoReceita);
        editValorReceita = findViewById(R.id.editValorReceita);
        recuperarReceitaTotal();
    }

    public void recuperarReceitaTotal() {
        String idUsuario = ConfiguracaoFireBase.getFirebaseAutenticacao().getCurrentUser().getUid();
        UsuarioDao dao = new UsuarioDao();
        dao.buscar(this,idUsuario);

    }

    public Boolean validarCamposReceita(){
        String textoValor = editValorReceita.getText().toString();
        String textoData = editDataReceita.getText().toString();
        String textoCategoria = editTipoReceita.getText().toString();
        String textoDescricao = editDescricaoReceita.getText().toString();
        if(!textoValor.isEmpty()){
            if(!textoData.isEmpty()){
                if(!textoCategoria.isEmpty()){
                    if(!textoDescricao.isEmpty()){
                        return true;
                    } else {
                        Toast.makeText(ReceitaActivity.this,"Descrição não preenchida!",Toast.LENGTH_LONG).show();
                        return false;
                    }

                } else {
                    Toast.makeText(ReceitaActivity.this,"Categoria não preenchida!",Toast.LENGTH_LONG).show();
                    return false;
                }

            } else {
                Toast.makeText(ReceitaActivity.this,"Data não preenchida!",Toast.LENGTH_LONG).show();
                return false;
            }

        } else {
            Toast.makeText(ReceitaActivity.this,"Valor não preenchido!",Toast.LENGTH_LONG).show();
            return false;
        }

    }

    public void salvarReceita(View view){
        FirebaseAuth autenticacao = ConfiguracaoFireBase.getFirebaseAutenticacao();
        if(validarCamposReceita()){
            double valorRecuperado = Double.parseDouble(editValorReceita.getText().toString());
            this.movimentacao = new Movimentacao();
            this.movimentacao.setValor(valorRecuperado);
            this.movimentacao.setCategoria(editTipoReceita.getText().toString());
            this.movimentacao.setDescricao(editDescricaoReceita.getText().toString());
            this.movimentacao.setData(editDataReceita.getText().toString());
            this.movimentacao.setTipo("R");
            this.movimentacao.setIdUsuario(autenticacao.getUid());
            this.movimentacao.setMesAno(DataUtil.dataMesAno(editDataReceita.getText().toString()));

            ReceitaGerada = valorRecuperado;
            ReceitaAtualizada = this.usuario.getReceitaTotal() + ReceitaGerada;


            this.usuario.setReceitaTotal(ReceitaAtualizada);

            UsuarioDao usuairoDao = new UsuarioDao();
            usuairoDao.update(this.usuario);


            MovimentacaoDao dao = new MovimentacaoDao();

            dao.salvar(this.movimentacao);


        }

    }




    @Override
    public void onEvent(Usuario data) {
        this.usuario = data;
    }
}