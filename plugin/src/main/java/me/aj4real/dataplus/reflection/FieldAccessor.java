package me.aj4real.dataplus.reflection;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class FieldAccessor<T> {

    private static final Unsafe unsafe;

    static {
        Unsafe finalUnsafe;
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            finalUnsafe = (Unsafe) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            finalUnsafe = null;
        }
        unsafe = finalUnsafe;
    }

    private static final Map<Field, FieldAccessor> cache = new HashMap<>();

    private final Field field;

    private FieldAccessor(Field field) {
        this.field = field;
        cache.put(field, this);
    }

    public static <C> FieldAccessor<C> of(Field field) {
        FieldAccessor<C> get = cache.get(field);
        return get != null ? get : new FieldAccessor<C>(field);
    }

    public Field getField() {
        return this.field;
    }

    public T get(Object control) {
        field.setAccessible(true);
        try {
            return (T) field.get(control);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean set(Object control, T newValue) {
        field.setAccessible(true);
        Object object = newValue;
        try {
            field.set(control, object);
            return true;
        } catch (Exception e) {
            try {
                long offset = 0;
                if(Modifier.isStatic(field.getModifiers())) {
                    offset = unsafe.staticFieldOffset(field);
                } else {
                    offset = unsafe.objectFieldOffset(field);
                }
                unsafe.putObject(control, offset, object);
                return true;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return false;
    }
    public String toString() {
        return this.field.toString();
    }
}
