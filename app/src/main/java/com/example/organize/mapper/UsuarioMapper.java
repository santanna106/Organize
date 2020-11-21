package com.example.organize.mapper;

import com.example.organize.activity.model.Usuario;
import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;
import java.util.Map;

public class UsuarioMapper implements IMapper<Usuario> {
    @Override
    public Map<String, Object> toMap(Usuario o) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", o.getId());
        result.put("idUsuario", o.getIdUsuario());
        result.put("nome", o.getNome());
        result.put("despesaTotal", o.getDespesaTotal());
        result.put("receitaTotal", o.getReceitaTotal());

        return result;
    }



    @Override
    public Usuario toObject(DataSnapshot snapshot, Map<String, Object> o) {
        Usuario usuario = new Usuario();
        usuario.setId(snapshot.getKey());
        usuario.setIdUsuario(o.get("idUsuario").toString());
        usuario.setNome(o.get("nome").toString());
        usuario.setReceitaTotal(Double.parseDouble(o.get("receitaTotal").toString()));
        usuario.setDespesaTotal(Double.parseDouble(o.get("despesaTotal").toString()));

        return usuario;
    }
}
