package application.infrastructure.core;

import application.infrastructure.config.Config;

public interface Context {
    /** получаем обьект из контекста
     * 1 смотрит в кеш и ищет там обьект по ключу type возвращает его
     * 2 если кеш такого обьекта не имеет то обращается за помощью к Config который скажет какой класс соответствует
     * ключу type, а посл пойдет к фаьрике, которая создаст обьект этого класса и вернет его в настоенном виде*
     * 3 созданный и настроенный обьект сохраняется в кеше и возвращается нам
     * return*/
    <T> T getObject(Class<T> type);
    Config getConfig();
}
