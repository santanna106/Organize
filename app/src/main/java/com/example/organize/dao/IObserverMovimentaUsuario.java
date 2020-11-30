package com.example.organize.dao;

import java.util.List;

public interface IObserverMovimentaUsuario<U,M> {
    public void onEventLoadUsuario(U u);
    public void onEventLoadMovimentacao(M m);
    public void onEventLoadListaMovimentacao(List<M> m);
}
