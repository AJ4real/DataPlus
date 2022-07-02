package me.aj4real.dataplus.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ConstructorHandle<T> {

    private static final Map<Constructor, ConstructorHandle> cache = new HashMap<>();

    private final Constructor<T> constructor;
    private ConstructorHandle(Constructor constructor) {
        this.constructor = constructor;
        cache.put(constructor, this);
    }

    public T invoke(Object... parameters) {
        constructor.setAccessible(true);
        try {
            return constructor.newInstance(parameters);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ConstructorHandle of(Constructor constructor) {
        if(cache.containsKey(constructor)) return cache.get(constructor);
        return new ConstructorHandle(constructor);
    }

}
