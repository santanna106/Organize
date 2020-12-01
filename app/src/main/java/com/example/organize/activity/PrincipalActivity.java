package com.example.organize.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.organize.activity.config.ConfiguracaoFireBase;
import com.example.organize.activity.model.Movimentacao;
import com.example.organize.activity.model.Usuario;
import com.example.organize.adapter.AdapterMovimentacao;
import com.example.organize.adapter.AdapterMovimentacaoFire;
import com.example.organize.dao.DaoBase;
import com.example.organize.dao.IObserverMovimentaUsuario;
import com.example.organize.dao.MovimentacaoDao;
import com.example.organize.dao.UsuarioDao;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.organize.R;
import com.example.organize.mapper.MovimentacaoMapper;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PrincipalActivity extends AppCompatActivity implements IObserverMovimentaUsuario<Usuario,Movimentacao> {

    private MaterialCalendarView calendarView;
    private TextView textoSaudacao, textoSaldo;
    private FirebaseAuth autenticacao;
    private Usuario usuario;
    private double receitaTotal;
    private double despesaTotal;
    private double resumo;
    private UsuarioDao usuarioDao;
    private MovimentacaoDao movimentacaoDao;
    private Movimentacao movimentacao;
    private ExecutorService executor;
    private IObserverMovimentaUsuario<Usuario,Movimentacao> obj;



    private RecyclerView recyclerMovimentos;
    private AdapterMovimentacao adapterMovimentacao;
    private AdapterMovimentacaoFire adapterMovimentacaoFire;
    private List<Movimentacao> movimentacoes;

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


        recyclerMovimentos = (RecyclerView) findViewById(R.id.recyclerMovimento);
        recyclerMovimentos.setLayoutManager(new LinearLayoutManager(this));
        //RecyclerView rvContacts = (RecyclerView) findViewById(R.id.recyclerMovimento);


        configuraCalendarView();

        MovimentacaoDao dao = new MovimentacaoDao();
        dao.lista(this);
       // movimentacoes = FirebaseDatabase.getInstance().getReference().child("movimentacao");

        FirebaseRecyclerOptions<Movimentacao> options =
                new FirebaseRecyclerOptions.Builder<Movimentacao>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("movimentacao"), Movimentacao.class)
                        .build();

        adapterMovimentacaoFire = new AdapterMovimentacaoFire(options,this);

        recyclerMovimentos.setAdapter(adapterMovimentacaoFire);
        swipe();

        /*
        FirebaseDatabase firebase = DaoBase.getDatabase();
        DatabaseReference reference = firebase.getReference("movimentacao");
        movimentacoes = new ArrayList<Movimentacao>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot movimentacaoSnapshot : dataSnapshot.getChildren()) {

                    Map<String, Object> map = (Map<String, Object>) movimentacaoSnapshot.getValue();
                    //Log.i("KEY: ", movimentacaoSnapshot.getKey());
                    //Log.i("KEY: ", map.toString());
                    MovimentacaoMapper mMapper = new MovimentacaoMapper();
                    Movimentacao movimentacao = mMapper.toObject(movimentacaoSnapshot, map);
                    movimentacoes.add(movimentacao);

                }


                //Log.i("MOVRECYCLE LISTA: ",movimentacoes.toString());
                // observer.onEventLoadListaMovimentacao(movimentacoes);
                //adapter.notifyDataSetChanged();
                // Log.i("MOVRECYCLE: LISTA ", movimentacoes.toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Log.w("ERROR", "Failed to read value.", databaseError.toException());
            }
        });*/

    }

    @Override
    protected void onStart() {
        super.onStart();

        recuperarUsuario();
        adapterMovimentacaoFire.startListening();
        //recuperarMovimentacoes();

    }

    public Future<List<Movimentacao>> getLista() {
        executor = Executors.newCachedThreadPool();
        return executor.submit(() -> {
            MovimentacaoDao movimentacaoDao = new MovimentacaoDao();
            movimentacaoDao.lista(this,this.adapterMovimentacao);
            Thread.sleep(1000);
            Log.i("EXECUTANDO THREAD: ",this.movimentacoes.toString());
            return this.movimentacoes;
        });
    }

    public void recuperarMovimentacoes()  {


        adapterMovimentacao = new AdapterMovimentacao(movimentacoes,this);
        adapterMovimentacao.notifyDataSetChanged();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerMovimentos.setLayoutManager(layoutManager);
        recyclerMovimentos.setHasFixedSize(true);
        recyclerMovimentos.setAdapter(adapterMovimentacao);



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



    public void recuperarUsuario() {
        String idUsuario = ConfiguracaoFireBase.getFirebaseAutenticacao().getCurrentUser().getUid();
        usuarioDao = new UsuarioDao();
        usuarioDao.buscar(this,idUsuario);

    }

    @Override
    protected void onStop() {
        super.onStop();
        usuarioDao.destroy();
        //movimentacaoDao.destroy();
        adapterMovimentacaoFire.stopListening();

    }

    @Override
    public void onEventLoadUsuario(Usuario usuario) {
        this.usuario = usuario;
        this.textoSaudacao.setText("Olá, " + this.usuario.getNome());
        DecimalFormat decimalFormat = new DecimalFormat("0.##");
        String resultadoFormatado = "R$ " + decimalFormat.format(this.usuario.getReceitaTotal() - this.usuario.getDespesaTotal());
        this.textoSaldo.setText(resultadoFormatado);
    }

    @Override
    public void onEventLoadMovimentacao(Movimentacao movimentacao) {

    }

    @Override
    public void onEventLoadListaMovimentacao(List<Movimentacao> m) {
        this.movimentacoes = m;

    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
    }

    public void swipe(){
        ItemTouchHelper.SimpleCallback itemToch = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.ACTION_STATE_IDLE;
                int swaipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;

                return makeMovementFlags(dragFlags,swaipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                excluirMovimentacao(viewHolder);


            }
        };

        new ItemTouchHelper(itemToch).attachToRecyclerView(recyclerMovimentos);
    }

    public void excluirMovimentacao(RecyclerView.ViewHolder viewHolder){
        AlertDialog.Builder builder = new  AlertDialog.Builder(PrincipalActivity.this);
        builder.setTitle("Deleta Movimentação");
        builder.setMessage("Você tem certeza que quer deletar essa movimentação?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int posicao = viewHolder.getAdapterPosition();
                adapterMovimentacaoFire.delete(posicao);
                adapterMovimentacaoFire.notifyItemRemoved(posicao);
                //viewHolder.get
               // movimentacao= movimentacoes.get(posicao);

                //Log.i("MOVENC",movimentacao.toString());
                //MovimentacaoDao dao = new MovimentacaoDao();
                //dao.delete(movimentacao);
                //adapterMovimentacaoFire.notifyItemRemoved(posicao);
                //movimentacoes.remove(posicao);

            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(PrincipalActivity.this,"Cancelado",Toast.LENGTH_LONG).show();
                adapterMovimentacaoFire.notifyDataSetChanged();
            }
        });

        builder.show();
    }
}

