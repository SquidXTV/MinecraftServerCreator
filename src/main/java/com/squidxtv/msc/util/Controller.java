package com.squidxtv.msc.util;

public interface Controller<T> {
    void init(T t);
    void reset();
}
