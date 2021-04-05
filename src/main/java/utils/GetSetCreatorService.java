package utils;

import utils.ModelAnnotations;
import utils.GetSet;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class GetSetCreatorService {
    private static ReentrantLock lock = new ReentrantLock();
    private static HashMap<Class, GetSet> dictionary = new HashMap<>();

    public static GetSet getGettersSetters(Class clazz) {
        lock.lock();

        try {
            if (dictionary.containsKey(clazz)) return dictionary.get(clazz);
            CreateGetSetMethods(clazz);
            GetSet getSet = CreateGetSetMethods(clazz);
            dictionary.put(clazz, getSet);
            return dictionary.get(clazz);
        } finally {
            lock.unlock();
        }
    }

    private static GetSet CreateGetSetMethods(Class clazz) {
        GetSet getSet = new GetSet();
        Method[] methods = clazz.getMethods();
        List<Field> fields = Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> f.getAnnotation(ModelAnnotations.Order.class) != null)
                .sorted(Comparator.comparingInt(f -> f.getAnnotation(ModelAnnotations.Order.class).value()))
                .collect(Collectors.toList());

        for (Field field : fields) {
            for (Method method : methods) {
                if (field.getName() == "id") {
                    if (method.getName().startsWith("set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1))) {
                        getSet.setters.add(method);
                    }
                    continue;
                }
                if (method.getName().startsWith("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1))) {
                    getSet.getters.add(method);
                }
                if (method.getName().startsWith("set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1))) {
                    getSet.setters.add(method);
                }
            }
        }

        return getSet;
    }
}
