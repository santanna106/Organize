package com.example.organize.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.organize.R;
import com.example.organize.activity.model.Movimentacao;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jamilton Damasceno
 */

public class AdapterMovimentacaoFire extends FirebaseRecyclerAdapter<Movimentacao,AdapterMovimentacaoFire.MyViewHolder> {

    private Context context;

    public AdapterMovimentacaoFire(@NonNull FirebaseRecyclerOptions<Movimentacao> options, Context context) {
        super(options);
        this.context = context;
    }

    public AdapterMovimentacaoFire(@NonNull FirebaseRecyclerOptions<Movimentacao> options) {
        super(options);
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_movimentacao, parent, false);
        return new MyViewHolder(itemLista);
    }


    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Movimentacao model) {

        holder.categoria.setText(model.getCategoria());
        holder.titulo.setText(model.getTipo());
        holder.valor.setText(Double.toString(model.getValor()));

        if (model.getTipo() == "D" || model.getTipo().equals("D")) {
            holder.valor.setTextColor(context.getResources().getColor(R.color.colorAccentDespesa));
            holder.valor.setText("-" + model.getValor());
        }

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
