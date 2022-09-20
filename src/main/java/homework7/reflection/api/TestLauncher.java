package homework7.reflection.api;

import homework7.reflection.api.annotations.AfterSuite;
import homework7.reflection.api.annotations.BeforeSuite;
import homework7.reflection.api.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class TestLauncher {
    private Method[] methods;

    public void start(Class<?> object) {
        methods = object.getMethods();

        Method beforeMethod = getMethodByAnnotation(BeforeSuite.class);
        Method afterMethod = getMethodByAnnotation(AfterSuite.class);

        List<Method> sortedTests = getAllMethodsByAnnotation(Test.class)
                .stream()
                .sorted(Collections.reverseOrder(Comparator.comparingInt(o -> o.getAnnotation(Test.class).priority())))
                .toList();

        sortedTests.forEach(method -> {
            try {
                beforeMethod.invoke(object.getDeclaredConstructor().newInstance());
                method.invoke(object.getDeclaredConstructor().newInstance());
                afterMethod.invoke(object.getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private <T extends Annotation> Method getMethodByAnnotation(Class<T> annotationClass) {
        Method returnMethod = null;

        for (Method method : methods) {
            T annotation = method.getAnnotation(annotationClass);
            if (annotation != null)
                if (returnMethod == null) {
                    returnMethod = method;
                } else {
                    throw new RuntimeException(String.format("BeforeSuite annotation should be in only one instance of %d", annotationClass));
                }
        }

        return returnMethod;
    }

    private <T extends Annotation> List<Method> getAllMethodsByAnnotation(Class<T> annotationClass) {
        List<Method> returnMethods = new ArrayList<>();

        for (Method method : methods) {
            T annotation = method.getAnnotation(annotationClass);
            if (annotation != null)
                returnMethods.add(method);
        }

        return returnMethods;
    }
}
