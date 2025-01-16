package com.todo.generators;

import io.qameta.allure.Step;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class TestDataGenerator {
    private static final Random RANDOM = new Random();

    //сделать генератор для множественного вызова

    private static final int MAX_STRING_LENGTH = 1;
    private static final int MIN_STRING_LENGTH = 200;

    // Mетод ThreadLocalRandom.current().nextInt(int origin, int bound) не включает верхнюю границу (bound), но включает нижнюю границу (origin),
    // т.е, если ...nextInt(1,200), то результат будет находиться в диапазоне от 1 до 199 включительно, но не включая 200,
    // поэтому bound = MAX_STRING_LENGTH+1
    private static final int randomStringLength = ThreadLocalRandom.current().nextInt(MIN_STRING_LENGTH, (MAX_STRING_LENGTH+1));

    @Step("Generate data for {clazz}")
    public static <T> T generateTestData(Class<T> clazz) {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);

                if (field.getType() == long.class || field.getType() == Long.class) {
                    field.set(instance, Math.abs(RANDOM.nextLong()));
                } else if (field.getType() == int.class || field.getType() == Integer.class) {
                    field.set(instance, Math.abs(RANDOM.nextInt()));
                } else if (field.getType() == String.class) {
//                    field.set(instance, generateRandomString(10));
                    field.set(instance, generateRandomString(randomStringLength));
                } else if (field.getType() == boolean.class || field.getType() == Boolean.class) {
                    field.set(instance, RANDOM.nextBoolean());
                }
            }
            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate test data for class: " + clazz.getName(), e);
        }
    }

//    private static String generateRandomString(int length) {
    // исправила на public, чтобы использовать для генерации в PostTodosTest
    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(characters.charAt(RANDOM.nextInt(characters.length())));
        }
        return builder.toString();
    }
}
