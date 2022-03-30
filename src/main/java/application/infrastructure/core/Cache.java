package application.infrastructure.core;
/** Обертка коллекции Map где
 * key -  объект типа Class<T>
 * value - тип Т*/
public interface Cache {
/** Возращает true если обьект класса есть*/
    boolean contains(Class<?> clazz);
/** Возращает объект из кеша типа Т, иначе null*/
    <T> T get(Class<T> clazz);
    /** помещает обьект в коллекцию Map где
     * key -  объект типа Class<T>
     * value - тип Т*/
    <T> void put(Class<T> clazz, T value);

}
