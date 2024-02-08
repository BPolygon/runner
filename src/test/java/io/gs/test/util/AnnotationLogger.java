package io.gs.test.util;

import org.slf4j.Logger;

import java.lang.reflect.Method;
import java.util.Set;

public class AnnotationLogger {

    public static void logAnnotatedMethods(Set<Method> annotatedMethods, Logger logger, String annotationName) {
        if (annotatedMethods.isEmpty()) {
            logger.warn("No methods annotated with @{} found.", annotationName);
        } else {
            logger.info("Methods annotated with @{}: {}", annotationName, annotatedMethods);
        }
    }
}
