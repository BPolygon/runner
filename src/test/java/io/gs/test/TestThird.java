package io.gs.test;


import io.gs.test.annotation.TestMethodToRun;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


public class TestThird {

@Test
@TestMethodToRun
@Tag("integration")
    public void testMethThird(){
    System.out.println("hello 3");
}
}
