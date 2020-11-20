package com.example.organize.mapper;

import java.util.HashMap;
import java.util.Map;

public interface IMapper<T> {
    public Map<String,Object> toMap(T o);
    public T toObject(Map<String,Object> o);
}
