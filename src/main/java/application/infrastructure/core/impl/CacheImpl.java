package application.infrastructure.core.impl;

import application.infrastructure.core.Cache;

import java.util.HashMap;
import java.util.Map;

public class CacheImpl implements Cache {

    private Map<String, Object> cache;

    public CacheImpl() {
        cache = new HashMap<>();
    }

    /*public static void main(String[] args) {
        CacheImpl cacheImpl = new CacheImpl();
        cacheImpl.put(String.class, "new Double(5)");
        System.out.println(cacheImpl.get(String.class));
        System.out.println(cacheImpl.contains(Integer.class));
    }*/

    @Override
    public boolean contains(Class<?> clazz) {
        return cache.containsKey(String.valueOf(clazz));
    }

    @Override
    public <T> T get(Class<T> clazz) {
        return (T) cache.get(String.valueOf(clazz));
    }

    @Override
    public <T> void put(Class<T> target, T value) {
        cache.put(String.valueOf(target),value);
    }
}
