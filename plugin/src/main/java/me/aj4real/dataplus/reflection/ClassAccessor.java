package me.aj4real.dataplus.reflection;

import com.google.common.collect.Lists;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class ClassAccessor<T> {
    private static final Map<Class, ClassAccessor> cache = new HashMap<>();
    private final Class<T> clazz;
    public ClassAccessor(Class<T> clazz) {
        this.clazz = clazz;
        cache.put(clazz, this);
    }

    public static <T> ClassAccessor of(Class<T> clazz) {
        ClassAccessor<T> get = cache.get(clazz);
        return get != null ? get : new ClassAccessor(clazz);
    }

    public Map<String, Set<MethodHandle>> getMethods() {
        HashSet<Method> fields = new HashSet<>();
        fields.addAll(Lists.newArrayList(clazz.getDeclaredMethods()));
        fields.addAll(Lists.newArrayList(clazz.getMethods()));
        Map<String, Set<MethodHandle>> ret = new HashMap<>();
        for (Method m : fields) {
            ret.computeIfAbsent(m.getName(), f -> new HashSet<>()).add(MethodHandle.of(m));
        }
        return ret;
    }
    public Map<String, FieldAccessor> getFields() {
        HashSet<Field> fields = new HashSet<>();
        fields.addAll(Lists.newArrayList(clazz.getDeclaredFields()));
        fields.addAll(Lists.newArrayList(clazz.getFields()));
        Map<String, FieldAccessor> ret = new HashMap<>();
        for (Field f : fields) {
            ret.put(f.getName(), FieldAccessor.of(f));
        }
        return ret;
    }
    public FieldAccessor lookupField(String name) {
        try {
            return FieldAccessor.of(clazz.getDeclaredField(name));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<FieldAccessor> lookupField(Class type, String typeParameter) {
        String finalTypeParameter = type.getCanonicalName() + typeParameter;
        List<FieldAccessor> fields = Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> f.getGenericType().getTypeName().startsWith(finalTypeParameter))
                .map(f -> FieldAccessor.of(f))
                .collect(Collectors.toList());
        return fields;
    }
    public List<FieldAccessor> lookupField(Class type) {
        List<FieldAccessor> fields = Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> f.getGenericType().getTypeName().startsWith(type.getCanonicalName()))
                .map(f -> FieldAccessor.of(f))
                .collect(Collectors.toList());
        return fields;
    }
    public List<FieldAccessor> lookupField(Class type, Class... parameters) {
        String lookfor = type.getCanonicalName();
        if(parameters != null) {
            lookfor = lookfor + "<";
            for(Class c : parameters) {
                lookfor = lookfor + c.getCanonicalName() + ",";
            }
            lookfor = lookfor.substring(0, lookfor.length() - 1) + ">";
        }
        String finalLookfor = lookfor;
        List<FieldAccessor> fields = Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> f.getGenericType().getTypeName().startsWith(finalLookfor))
                .map(f -> FieldAccessor.of(f))
                .collect(Collectors.toList());
        return fields;
    }
    public String toString() {
        return this.clazz.toString();
    }
}
