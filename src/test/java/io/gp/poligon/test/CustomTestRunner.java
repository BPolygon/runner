package io.gp.poligon.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.engine.descriptor.ClassTestDescriptor;
import org.junit.platform.commons.util.AnnotationUtils;
import org.junit.platform.engine.*;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.engine.support.descriptor.*;
import org.junit.platform.launcher.*;
import org.junit.platform.launcher.core.*;
import org.junit.platform.engine.discovery.ClassSelector;
import java.lang.reflect.Method;
import java.util.List;


public class CustomTestRunner implements TestEngine {

    @Override
    public String getId() {
        return "custom-test-runner";
    }

    @Override
    public TestDescriptor discover(EngineDiscoveryRequest discoveryRequest, UniqueId uniqueId) {
        EngineDescriptor engineDescriptor = new EngineDescriptor(uniqueId, "Custom Test Engine");

        discoveryRequest.getSelectorsByType(ClassSelector.class).stream()
                .map(ClassSelector::getJavaClass)
                .filter(clazz -> clazz.isAnnotationPresent(CustomTest.class)) // Фильтрация классов с аннотацией @CustomTest
                .forEach(clazz -> addTestClass(engineDescriptor, clazz));

        return engineDescriptor;
    }


    private void addTestClass(EngineDescriptor engineDescriptor, Class<?> testClass) {
        UniqueId classUniqueId = engineDescriptor.getUniqueId().append("class", testClass.getName());
        AbstractTestDescriptor classDescriptor = new AbstractTestDescriptor(classUniqueId, testClass.getSimpleName(), ClassSource.from(testClass)) {
            @Override
            public Type getType() {
                return Type.CONTAINER;
            }
        };
        engineDescriptor.addChild(classDescriptor);

        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                UniqueId methodUniqueId = classDescriptor.getUniqueId().append("method", method.getName());
                MethodTestDescriptor methodDescriptor = new MethodTestDescriptor(methodUniqueId, classDescriptor, method);
                classDescriptor.addChild(methodDescriptor);
            }
        }
    }


    @Override
    public void execute(ExecutionRequest executionRequest) {
        TestDescriptor engineDescriptor = executionRequest.getRootTestDescriptor();
        EngineExecutionListener listener = executionRequest.getEngineExecutionListener();

        listener.executionStarted(engineDescriptor);
        for (TestDescriptor classDescriptor : engineDescriptor.getChildren()) {
            executeTestClass(listener, classDescriptor);
        }
        listener.executionFinished(engineDescriptor, TestExecutionResult.successful());
    }

    private void executeTestClass(EngineExecutionListener listener, TestDescriptor classDescriptor) {
        // Убираем приведение типа к ClassTestDescriptor
        listener.executionStarted(classDescriptor);

        // Исполнение каждого тестового метода
        for (TestDescriptor methodDescriptor : classDescriptor.getChildren()) {
            executeTestMethod(listener, methodDescriptor);
        }

        listener.executionFinished(classDescriptor, TestExecutionResult.successful());
    }

    private void executeTestMethod(EngineExecutionListener listener, TestDescriptor methodDescriptor) {
        MethodTestDescriptor testMethodDescriptor = (MethodTestDescriptor) methodDescriptor;
        Method method = testMethodDescriptor.getTestMethod();

        // Проверяем, что класс метода аннотирован @CustomTest
        if (!method.getDeclaringClass().isAnnotationPresent(CustomTest.class)) {
            return; // Пропускаем выполнение метода, если его класс не аннотирован @CustomTest
        }

        listener.executionStarted(methodDescriptor);
        try {
            Class<?> testClass = method.getDeclaringClass();
            Object testInstance = testClass.getDeclaredConstructor().newInstance();
            method.invoke(testInstance);
            listener.executionFinished(methodDescriptor, TestExecutionResult.successful());
            System.out.println("Method executed: " + method.getName());
        } catch (Exception e) {
            listener.executionFinished(methodDescriptor, TestExecutionResult.failed(e));
        }
    }


}
