package org.redstom.botserver.plugins.loader.wirer;

import org.redstom.botapi.injector.Inject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public class ParametersWirer<T> {

    private final Class<T> baseClass;

    public ParametersWirer(Class<T> baseClass) {
        this.baseClass = baseClass;
    }

    public Object newInstance(Constructor<?> constructor, Map<String, Object> available) throws
        IllegalAccessException,
        InstantiationException,
        NoSuchMethodException,
        InvocationTargetException {
        return constructor.newInstance(
            autoFill(constructor.getParameters(), new Object[0], available)
        );
    }

    public Method getMethod(String name) throws NoSuchMethodException {
        Method method = null;
        for (Method baseClassMethod : baseClass.getMethods()) {
            if (baseClassMethod.getName().equals(name))
                method = baseClassMethod;
        }
        if (method == null) {
            throw new NoSuchMethodException("Cannot find a method with this name !");
        }
        return method;
    }

    public Object[] invoke(
        Method[] methods,
        Object instance,
        Object[] baseParams,
        Map<String, Object> available
    ) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<Object> returns = new ArrayList<>();
        for (Method method : methods) {
            returns.add(invoke(method, instance, baseParams, available));
        }
        return returns.toArray(Object[]::new);
    }

    public Object invoke(
        Method method,
        Object instance,
        Object[] baseParams,
        Map<String, Object> available
    ) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return method.invoke(instance, autoFill(method.getParameters(), baseParams, available));
    }

    public Object[] autoFill(Parameter[] parameters, Object[] baseParams, Map<String, Object> available) {
        Object[] values = new Object[parameters.length];
        for (int i = 0, j = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            if (parameter.isAnnotationPresent(Inject.class)) {
                Inject inject = parameter.getAnnotation(Inject.class);
                if (!available.containsKey(inject.value())) {
                    values[i] = null;
                    continue;
                }
                if (!parameter.getType().isAssignableFrom(available.get(inject.value()).getClass())) {
                    values[i] = null;
                    continue;
                }
                values[i] = available.get(inject.value());
            } else {
                if (baseParams.length <= j || !parameter.getType().isAssignableFrom(baseParams[j].getClass())) {
                    values[i] = null;
                    continue;
                }
                values[i] = baseParams[j];
                j++;
            }
        }
        return values;
    }

    public Method[] getMethods(String name) {
        return Arrays.stream(baseClass.getMethods()).filter(a -> a.getName().equals(name)).toArray(Method[]::new);
    }
}
