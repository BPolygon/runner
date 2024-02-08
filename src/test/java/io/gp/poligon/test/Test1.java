package io.gp.poligon.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

//@ExtendWith(CustomTestExtension.class)
@CustomTest
public class Test1 {
    @Test
    public void testMethod() {
        System.out.println("TEST1");
    }
}
