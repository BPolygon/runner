package io.gs.test.util;

import org.junit.jupiter.api.DynamicTest;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.stream.Collectors;

public class DynamicTestCreator {

    public static DynamicTest createDynamicTest(Method method) {
        return DynamicTest.dynamicTest(method.getName(), () -> invokeTestMethod(method));
    }

    private static void invokeTestMethod(Method method) {
        try {
            method.invoke(method.getDeclaringClass().getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            throw new RuntimeException("Error invoking test method: " + method.getName(), e);
        }
    }

    public static Collection<DynamicTest> createDynamicTests(Collection<Method> methods) {
        return methods.stream()
                .map(DynamicTestCreator::createDynamicTest)
                .collect(Collectors.toList());
    }
}
