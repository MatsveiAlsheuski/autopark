package application.infrastructure.core.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface InitMethod {
    /** Для аннотирования метода инициализации*/
}
