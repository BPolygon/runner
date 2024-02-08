package io.gs.test;

import io.gs.test.annotation.TestMethodToRun;
import io.gs.test.util.AnnotationLogger;
import io.gs.test.util.DynamicTestCreator;
import io.gs.test.util.ReflectionsConfigurator;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Set;

public class CustomMethodRunner {

    private static final Logger logger = LoggerFactory.getLogger(CustomMethodRunner.class);

    @TestFactory
    Collection<DynamicTest> runTaggedTests() {
        Reflections reflections = ReflectionsConfigurator.configureReflections("io.gs.test.annotation");
        Set<Method> annotatedMethods = reflections.getMethodsAnnotatedWith(TestMethodToRun.class);

        AnnotationLogger.logAnnotatedMethods(annotatedMethods, logger, TestMethodToRun.class.getSimpleName());
        return DynamicTestCreator.createDynamicTests(annotatedMethods);
    }
}
