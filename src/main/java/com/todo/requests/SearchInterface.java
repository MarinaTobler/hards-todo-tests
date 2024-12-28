package com.todo.requests;

public interface SearchInterface {
    Object readAll();
    Object readAll(int offset, int limit);

}
