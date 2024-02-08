package io.gs.test;

import io.gs.test.annotation.TestClassToRun;
import org.junit.jupiter.api.Test;

@TestClassToRun
public class TestTwo {
    @Test
    public void testMethTwo(){
    System.out.println("hello 2");
}
}
