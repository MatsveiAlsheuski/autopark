package application.infrastructure.config.impl;

import application.infrastructure.config.Config;
import application.infrastructure.core.Scanner;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.Set;

@AllArgsConstructor
public class JavaConfig implements Config {
    private final Scanner scanner;
    private final Map<Class<?>, Class<?>> interfaceToImplementation;

    @Override
    public <T> Class<? extends T> getImplementation(Class<T> target) {
        Set<Class<? extends T>> set = scanner.getSubTypesOf(target);
        if (set.size() != 1) {
            if (set.isEmpty())
                throw new RuntimeException("Target interface has 0 value");
            else if (set.contains(interfaceToImplementation.get(target)))
                return (Class<? extends T>) interfaceToImplementation.get(target);
            else throw new RuntimeException("Target interface has more then one value");
        }
        Class<? extends T> clazzOut = null;
        for (Class<?> clazz : set) {
            clazzOut = (Class<? extends T>) clazz;
        }
        return clazzOut;
    }

    @Override
    public Scanner getScanner() {
        return scanner;
    }

}
