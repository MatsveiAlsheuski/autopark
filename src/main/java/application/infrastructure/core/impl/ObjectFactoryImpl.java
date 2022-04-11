package application.infrastructure.core.impl;

import application.infrastructure.configurations.ObjectConfigurator;
import application.infrastructure.configurations.ProxyConfigurator;
import application.infrastructure.core.Context;
import application.infrastructure.core.ObjectFactory;
import application.infrastructure.core.annotations.InitMethod;
import lombok.SneakyThrows;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ObjectFactoryImpl implements ObjectFactory {
    private final Context context;
    private final List<ObjectConfigurator> objectConfigurators = new ArrayList<>();
    private final List<ProxyConfigurator> proxyConfigurators = new ArrayList<>();

    @SneakyThrows
    public ObjectFactoryImpl(Context context) {
        this.context = context;
        Set<Class<? extends ObjectConfigurator>> set =
                context.getConfig().getScanner().getSubTypesOf(ObjectConfigurator.class);
        for (Class<?> clazz : set) {
            objectConfigurators.add((ObjectConfigurator) clazz.newInstance());
        }
        for (Class<? extends ProxyConfigurator> aClass : context.getConfig().getScanner().getReflections().getSubTypesOf(ProxyConfigurator.class)) {
            proxyConfigurators.add(aClass.newInstance());
        }
    }

    private <T> T create(Class<T> implementation) throws Exception {
        return implementation.newInstance();
    }

    private <T> void configure(T object) {
        for (ObjectConfigurator configurator : objectConfigurators) {
            configurator.configure(object, context);
        }
    }

    private <T> void initialize(Class<?> implementation, T object) throws Exception {
        for (Method method : implementation.getMethods()) {
            if (method.isAnnotationPresent(InitMethod.class)) {
                method.invoke(object);
            }
        }
    }

    private <T> T makeProxy(Class<T> implClass, T object){
        for (ProxyConfigurator proxyConfigurator : proxyConfigurators) {
            object = proxyConfigurator.makeProxy(object,implClass,context);
        }
        return object;
    }


    @Override
    @SneakyThrows
    public <T> T createObject(Class<T> implementation) {
        T object = create(implementation);
        configure(object);
        initialize(implementation, object);
        object = makeProxy(implementation, object);
        return object;
    }
}
