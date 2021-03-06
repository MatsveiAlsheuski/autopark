package application.infrastructure.core.impl;

import application.infrastructure.core.Scanner;
import org.reflections.Reflections;
import application.vehicle.technical.Fixer;


import java.util.Set;

public class ScannerImpl implements Scanner {

    private Reflections reflections;

    public ScannerImpl(String packageName) {
        reflections = new Reflections(packageName);
    }

    @Override
    public <T> Set<Class<? extends T>> getSubTypesOf(Class<T> type) {

        return reflections.getSubTypesOf(type);
    }

    @Override
    public Reflections getReflections()
    {
        return reflections;
    }

}
