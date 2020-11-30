package com.example.organize.dao;

import com.example.organize.activity.model.Movimentacao;

public interface IObserver<T> {
    public void onEvent(T obj);
}


