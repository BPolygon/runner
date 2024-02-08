package io.gp.poligon.test;

import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.support.descriptor.AbstractTestDescriptor;
import org.junit.platform.engine.support.descriptor.MethodSource;

import java.lang.reflect.Method;

public class MethodTestDescriptor extends AbstractTestDescriptor {
    private final Method testMethod;

    public MethodTestDescriptor(UniqueId uniqueId, TestDescriptor parent, Method testMethod) {
        super(uniqueId, testMethod.getName(), MethodSource.from(testMethod));
        this.testMethod = testMethod;
        parent.addChild(this);
    }

    @Override
    public Type getType() {
        return Type.TEST;
    }

    public Method getTestMethod() {
        return testMethod;
    }
}
