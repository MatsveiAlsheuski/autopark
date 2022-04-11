package application.infrastructure.configurations;

import application.infrastructure.core.Context;

public interface ProxyConfigurator {
    <T> T makeProxy(T object, Class<T> implementation, Context context);
}
