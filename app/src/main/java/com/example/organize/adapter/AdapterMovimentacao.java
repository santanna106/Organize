package com.example.organize.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.organize.R;
import com.example.organize.activity.model.Movimentacao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jamilton Damasceno
 */

public class AdapterMovimentacao extends RecyclerView.Adapter<AdapterMovimentacao.MyViewHolder> {

    List<Movimentacao> movimentacoes;
    Context context;

    public AdapterMovimentacao( Context context) {
        // Log.i("MOVRECYCLEADAPTER",this.movimentacoes.toString());
        this.context = context;
    }

    public AdapterMovimentacao(List<Movimentacao> mov, Context context) {
        this.movimentacoes = new ArrayList<Movimentacao>();
        this.movimentacoes = mov;
       // Log.i("MOVRECYCLEADAPTER",this.movimentacoes.toString());
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_movimentacao, parent, false);
        return new MyViewHolder(itemLista);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Movimentacao movimentacao = movimentacoes.get(position);

        holder.titulo.setText(movimentacao.getDescricao());
        holder.valor.setText(String.valueOf(movimentacao.getValor()));
        holder.categoria.setText(movimentacao.getCategoria());

        if (movimentacao.getTipo() == "D" || movimentacao.getTipo().equals("D")) {
            holder.valor.setTextColor(context.getResources().getColor(R.color.colorAccentDespesa));
            holder.valor.setText("-" + movimentacao.getValor());
        }
    }


    @Override
    public int getItemCount() {
        return movimentacoes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titulo, valor, categoria;

        public MyViewHolder(View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.textAdapterTitulo);
            valor = itemView.findViewById(R.id.textAdapterValor);
            categoria = itemView.findViewById(R.id.textAdapterCategoria);
        }

    }

}
