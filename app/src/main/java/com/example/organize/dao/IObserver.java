package com.example.organize.dao;

public interface IObserver<T> {
    void onEvent(T data);
}


