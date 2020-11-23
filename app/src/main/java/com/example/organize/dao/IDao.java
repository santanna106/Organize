package com.example.organize.dao;

import com.example.organize.activity.model.Usuario;

public interface IDao  <T> {
    public void salvar(T o);
    public void delete(T o);
    public void update(T o);
    public void buscar(IObserver<T> observer,String id);
    public void destroy();
}
