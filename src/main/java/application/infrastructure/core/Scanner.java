package application.infrastructure.core;


import org.reflections.Reflections;

import java.util.Set;

public interface Scanner {

    /**
     * Возвращает можество Set всех реализаций указаного интерфейса или класса type
     */
    <T> Set<Class<? extends T>> getSubTypesOf(Class<T> type);

    Reflections getReflections();
}