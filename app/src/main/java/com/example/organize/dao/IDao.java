package com.example.organize.dao;

public interface IDao <T> {
    public void salvar(T o);
    public void delete(T o);
    public void update(T o);
}
