//package io.gp.poligon.test;
//
//import org.junit.jupiter.api.extension.AfterEachCallback;
//import org.junit.jupiter.api.extension.BeforeEachCallback;
//import org.junit.jupiter.api.extension.ExtensionContext;
//import org.opentest4j.TestAbortedException;
//
//public class CustomTestExtension implements BeforeEachCallback, AfterEachCallback {
//    @Override
//    public void beforeEach(ExtensionContext context) throws Exception {
//        // Проверяем наличие аннотации @CustomTest у класса
//        if (!context.getTestClass().isPresent() ||
//                !context.getTestClass().get().isAnnotationPresent(CustomTest.class)) {
//            throw new TestAbortedException("Class is not annotated with @CustomTest");
//        }
//    }
//
//    @Override
//    public void afterEach(ExtensionContext context) throws Exception {
//        // Логика после выполнения теста, если требуется
//    }
//}
