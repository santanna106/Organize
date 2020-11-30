package com.example.organize.dao;

import com.example.organize.activity.model.Movimentacao;

public interface IUsuarioDao<Usuario> extends IDao<Usuario> {
    public void buscar(IObserverMovimentaUsuario<Usuario, Movimentacao> observer, String id);
}
