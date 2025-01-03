package com.todo.conf;

import org.checkerframework.checker.units.qual.C;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {
    private static Configuration instance;
    // properties - структура, которая хранит ключ-значение
    private final Properties properties = new Properties();

    private Configuration() {
        // считываем properties из config.properties файла
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (inputStream == null) {
                throw new IllegalArgumentException("config.properties file not found in resources");
            }
            // складываем данные из файла config.properties в properties
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration properties", e);
        }
    }

    // создаём сущность Configuration первый раз с помощью getInstance
    private static synchronized Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    public static String getProperty (String key) {
        return getInstance().properties.getProperty(key);
    }
    public static void setProperty (String key, String value) {
        getInstance().properties.put(key, value);
    }
}
