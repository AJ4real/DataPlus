package me.aj4real.dataplus.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MethodHandle<T> {
    private static final Map<Method, MethodHandle> cache = new HashMap<>();

    private final Method method;
    private MethodHandle(Method constructor) {
        this.method = constructor;
        cache.put(constructor, this);
    }

    public T invoke(T object, Object... parameters) {
        method.setAccessible(true);
        try {
            return (T) method.invoke(object, parameters);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static MethodHandle of(Method constructor) {
        if(cache.containsKey(constructor)) return cache.get(constructor);
        return new MethodHandle(constructor);
    }
}
