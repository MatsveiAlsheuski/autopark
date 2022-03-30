package application.infrastructure.configurations.impl;

import application.infrastructure.configurations.ObjectConfigurator;
import application.infrastructure.core.Context;

import application.infrastructure.core.annotations.Property;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class PropertyObjectConfigurator implements ObjectConfigurator {

    private final Map<String, String> properties;

    @SneakyThrows
    public PropertyObjectConfigurator() {
        URL path = this.getClass().getClassLoader().getResource("applications.properties");
        if (path == null)
            throw new FileNotFoundException(String.format("File '%s' not found", "applications.properties"));
        Stream<String> lines = new BufferedReader(new InputStreamReader(path.openStream())).lines();
        properties = lines.map(line -> line.split("=")).collect(toMap(arr -> arr[0], arr -> arr[1]));
    }

    @Override
    @SneakyThrows
    public void configure(Object object, Context context) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            Property property = field.getAnnotation(Property.class);
            if (property != null) {
                String propertyKey = property.value();
                String key;
                if (propertyKey.equals("")) {
                    key = field.getName();
                } else {
                    key = propertyKey;
                }
                field.setAccessible(true);
                field.set(object,properties.get(key));
            }
        }
    }
/*
    @SneakyThrows
    @Override
    public void configure(Object object, Context context) {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            if (f.isAnnotationPresent(Proprety.class)) {
                Annotation annotation = f.getAnnotation(Proprety.class);
                Proprety prop = (Proprety) annotation;
                if (prop.value().equals("")) {
                    f.set(object, object.getClass().getSimpleName());
                } else {
                    f.set(object, properties.get(prop.value()));
                }
            }
        }
    }*/
}
