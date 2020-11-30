package com.example.organize.dao;

import com.example.organize.activity.model.Usuario;

import java.util.List;

public interface IDao  <T> {
    public void salvar(T o);
    public void delete(T o);
    public void update(T o);

    public void destroy();
    public List<T> lista();
}
