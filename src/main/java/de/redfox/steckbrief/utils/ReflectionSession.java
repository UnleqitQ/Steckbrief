package de.redfox.steckbrief.utils;

import java.lang.reflect.Field;

public class ReflectionSession {
    Object instance;

    public ReflectionSession(Object instance) {
        this.instance = instance;
    }

    public ReflectionSession setField(String fieldName, Object value) {
        try {
            Field field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(instance, value);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        return this;
    }
}
