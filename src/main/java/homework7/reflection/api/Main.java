package homework7.reflection.api;

import homework7.reflection.api.annotations.AfterSuite;
import homework7.reflection.api.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        TestLauncher launcher = new TestLauncher();
        launcher.start(TestClass.class);
    }
}
