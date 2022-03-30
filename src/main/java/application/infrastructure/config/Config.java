package application.infrastructure.config;

import application.infrastructure.core.Scanner;

public interface Config {
    /** Возвращает одну реализацию интерфейса типа target
     * мы должны четко знать обьект какого класса мы должны создавать*/
    <T> Class<? extends T> getImplementation(Class<T> target);
/** возвращает объект Scanner. должен ументь делиться сканером*/
    Scanner getScanner();
}
