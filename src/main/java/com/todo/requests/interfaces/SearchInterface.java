package com.todo.requests.interfaces;

public interface SearchInterface {
    Object readAll();
    Object readAll(int offset, int limit);

}
