package homework7.reflection.api;

import homework7.reflection.api.annotations.AfterSuite;
import homework7.reflection.api.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        TestLauncher launcher = new TestLauncher();
        launcher.start(TestClass.class);
/*
        Annotation[] annotations = clazz.getAnnotationsByType(Test.class);
        System.out.println(annotations.length);

        boolean isTypeAnnotationPresent =
                clazz.getPackage().isAnnotationPresent(Test.class);
        System.out.println(isTypeAnnotationPresent);

// Target: Type
        boolean isTypeAnnotationPresent1 =
                clazz.isAnnotationPresent(Test.class);
        System.out.println(isTypeAnnotationPresent1);

// Target: Method
        try{
        Method method = clazz.getMethod("tearDown");
        boolean isMethodAnnotationPresent =
                method.isAnnotationPresent(AfterSuite.class);
            System.out.println(isMethodAnnotationPresent);
        }catch (NoSuchMethodException ex){
            System.out.println(ex.getMessage());
        }*/
    }
}
