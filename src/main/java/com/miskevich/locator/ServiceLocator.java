package com.miskevich.locator;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {
    private static ServiceLocator INSTANCE;
    private final Map<Class<?>, Object> serviceRegistry = new HashMap();

    private ServiceLocator(){

    }

    public static ServiceLocator getInstance(){
        if(INSTANCE == null){
            INSTANCE = new ServiceLocator();
        }
        return INSTANCE;
    }

    public <T> void register(Class<T> clazz, T value){
        serviceRegistry.put(clazz, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> clazz){
        return (T) serviceRegistry.get(clazz);
    }
}
