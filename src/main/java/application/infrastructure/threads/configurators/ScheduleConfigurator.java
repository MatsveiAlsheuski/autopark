package application.infrastructure.threads.configurators;

import application.infrastructure.configurations.ProxyConfigurator;
import application.infrastructure.core.Context;
import application.infrastructure.threads.annotations.Schedule;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.concurrent.*;

public class ScheduleConfigurator implements ProxyConfigurator {

    @Override
    public <T> T makeProxy(T object, Class<T> implementation, Context context) {
        for (Method declaredMethod : object.getClass().getDeclaredMethods()) {
            if (declaredMethod.isAnnotationPresent(Schedule.class)) {
                try {
                    if (declaredMethod.getReturnType().getName() != "void") {
                        throw new Exception("Method have not return type \"void\"!");
                    } else if (!Modifier.toString(declaredMethod.getModifiers()).startsWith("public")) {
                        throw new Exception("Method have not modifier's type \"public\"!");
                    } else {
                        return (T) Enhancer.create(implementation, (MethodInterceptor) this::invoke);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return object;
    }

    private Object invoke(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Schedule schedulesync = method.getAnnotation(Schedule.class);
        if (schedulesync != null) {

            System.out.println(method + " - сообщение из application.infrastructure.threads.configurators.ScheduleConfigurator");
            Thread thread = new Thread(() -> this.invoker(object, methodProxy, args, schedulesync.timeout(), schedulesync.delta()));
            thread.setDaemon(true);
            thread.start();
            return null;
        }
        return methodProxy.invokeSuper(object, args);
    }

    private void invoker(Object object, MethodProxy method, Object[] args, int milliseconds, int delta) {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread invokeThread = new Thread(() -> {
                        ExecutorService executorService =
                                Executors.newSingleThreadExecutor(new ThreadFactory() {
                                    @Override
                                    public Thread newThread(Runnable r) {
                                        Thread fThread = Executors.defaultThreadFactory().newThread(r);
                                        fThread.setDaemon(true);
                                        return fThread;
                                    }
                                });
                        try {
                            executorService.submit(() -> {
                                try {
                                    return method.invokeSuper(object, args);
                                } catch (Throwable throwable) {
                                }
                                return null;
                            }).get(milliseconds, TimeUnit.MILLISECONDS);
                        } catch (TimeoutException exception) {
                            executorService.shutdownNow();
                        } catch (Exception exception) {
                            executorService.shutdownNow();
                        }
                        executorService.shutdownNow();
                    });
                    invokeThread.setDaemon(true);
                    invokeThread.start();
                    Thread.currentThread().sleep(delta);
                } catch (InterruptedException e) {
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
