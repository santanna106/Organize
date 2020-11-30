package com.example.organize.mapper;

import android.util.Log;

import com.example.organize.activity.model.Movimentacao;
import com.example.organize.activity.model.Usuario;
import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;
import java.util.Map;

public class MovimentacaoMapper implements IMapper<Movimentacao> {
    @Override
    public Map<String, Object> toMap(Movimentacao o) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", o.getId());
        result.put("idUsuario", o.getIdUsuario());
        result.put("data", o.getData());
        result.put("categoria", o.getCategoria());
        result.put("descricao", o.getDescricao());
        result.put("tipo", o.getTipo());
        result.put("valor", o.getValor());
        result.put("mesAno", o.getMesAno());

        return result;
    }

    @Override
    public Movimentacao toObject(DataSnapshot snapshot, Map<String, Object> o) {
        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setId(snapshot.getKey());
        movimentacao.setIdUsuario(o.get("idUsuario").toString());
        movimentacao.setData(o.get("data").toString());
        movimentacao.setCategoria(o.get("categoria").toString());
        movimentacao.setDescricao(o.get("descricao").toString());
        movimentacao.setTipo(o.get("tipo").toString());
        movimentacao.setValor(Double.parseDouble(o.get("valor").toString()));
        movimentacao.setMesAno(o.get("mesAno").toString());

        return movimentacao;
    }
}
