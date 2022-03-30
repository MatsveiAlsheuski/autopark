package application.infrastructure.core.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Autowired {
    /**Поля должны быть заполнены автоматически
     * эту анотации нужно зарегестрировать как Object configuration*/
}
