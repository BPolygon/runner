package io.gs.test;

import io.gs.test.annotation.TestClassToRun;
import io.gs.test.util.DynamicTestCreator;
import io.gs.test.util.ReflectionsConfigurator;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.reflections.Reflections;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomClassRunner {

    @TestFactory
    Collection<DynamicTest> runTaggedTests() {
        Reflections reflections = ReflectionsConfigurator.configureReflections("io.gs.test.annotation");
        Set<Class<?>> classesWithTestClassToRunAnnotation = reflections.getTypesAnnotatedWith(TestClassToRun.class);

        return classesWithTestClassToRunAnnotation.stream()
                .flatMap(clazz -> reflections.getMethodsAnnotatedWith(Test.class).stream()
                        .filter(method -> method.getDeclaringClass().equals(clazz)))
                .collect(Collectors.toSet())
                .stream()
                .map(DynamicTestCreator::createDynamicTest)
                .collect(Collectors.toList());
    }
}
