package application.infrastructure.core.impl;

import application.infrastructure.core.Scanner;
import org.reflections.Reflections;
import application.vehicle.technical.Fixer;


import java.util.Set;

public class ScannerImpl implements Scanner {

    private Reflections reflections;

    public ScannerImpl(String packageName) {
       //reflections = new Reflections();
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

    public static void main(String[] args) {
        Scanner scanner = new ScannerImpl("application");
        Set<Class<? extends Fixer>> set = scanner.getSubTypesOf(Fixer.class);
        System.out.println(set.size());

        Reflections reflections = new Reflections();
        Set<Class<? extends Fixer>> set1 = reflections.getSubTypesOf(Fixer.class);
        System.out.println(set1.size());
        for (Class<?> clazz : set1) {
            System.out.println(clazz);
        }
    }
}
