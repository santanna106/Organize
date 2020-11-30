package com.example.organize.dao;

import com.example.organize.activity.model.Movimentacao;
import com.example.organize.activity.model.Usuario;
import com.example.organize.adapter.AdapterMovimentacao;

import java.util.List;

public interface IMovimentacaoDao<T> extends IDao<T> {

    public void buscar(IObserverMovimentaUsuario<Usuario, Movimentacao> observer, String id);
    public void lista(IObserverMovimentaUsuario<Usuario, Movimentacao> observer, AdapterMovimentacao adapter);
    public void lista(IObserverMovimentaUsuario<Usuario, Movimentacao> observer);


}
