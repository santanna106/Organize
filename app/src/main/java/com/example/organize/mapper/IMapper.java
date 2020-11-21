package com.example.organize.mapper;

import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;
import java.util.Map;

public interface IMapper<T> {
    public Map<String,Object> toMap(T o);
    public T toObject(DataSnapshot snapshot, Map<String,Object> o);
}
