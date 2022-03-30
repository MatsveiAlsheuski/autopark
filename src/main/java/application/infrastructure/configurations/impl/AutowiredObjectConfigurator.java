package application.infrastructure.configurations.impl;

import application.infrastructure.configurations.ObjectConfigurator;
import application.infrastructure.core.Context;
import application.infrastructure.core.annotations.Autowired;
import lombok.SneakyThrows;

import java.lang.reflect.Field;

public class AutowiredObjectConfigurator implements ObjectConfigurator {

    @Override
    @SneakyThrows
    public void configure(Object object, Context context) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            Autowired autowired = field.getDeclaredAnnotation(Autowired.class);//.getAnnotation(Autowired.class);
            if (autowired != null){
                field.setAccessible(true);
                field.set(object, context.getObject(field.getType()));
            }
        }
    }
}
