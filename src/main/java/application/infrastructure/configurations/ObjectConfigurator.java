package application.infrastructure.configurations;

import application.infrastructure.core.Context;

public interface ObjectConfigurator {
    /** настраивает  object требует context в помощ. Настройка определятеся в классе реализаторе
     * Однако известно, что наши конфигураторы будут работать с аннотациями. Аннотации сообщают
     * инфраструктуре о том, что требуется сделать*/
    void configure(Object object, Context context);
}
