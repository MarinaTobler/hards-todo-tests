package com.todo.storages;

import com.todo.models.Todo;

import java.util.HashMap;
import java.util.HashSet;

public class TestDataStorage {
    // единственный экземпляр TestDataStorage; чтобы доступ был отовсюду он д.б. static:
    private static TestDataStorage instance;
    private HashMap<Long, Todo> storage;

    private TestDataStorage() {
        // вначале создаём пустую мапу:
        storage = new HashMap<>();
    }

    public static TestDataStorage getInstance() {
        if (instance == null) {
            instance = new TestDataStorage();
        }
        return instance;
    }

    public void addData(Todo todo) {
        // для того, чтобы быстро по id потом искать созданную сущность
        storage.put(todo.getId(), todo);
    }

    public HashMap<Long, Todo> getStorage() {
        return storage;
    }

    // необязательно, тк garbage коллектор удаляет тоже
    public void clean() {
        storage = new HashMap<>();
    }
}
