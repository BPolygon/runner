package io.gs.test;


import io.gs.test.annotation.TestClassToRun;
import io.gs.test.annotation.TestMethodToRun;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@TestClassToRun
public class TestOne {

@Test
@TestMethodToRun
@Tag("regress")
    public void testMethOneA(){
    System.out.println("hello a1");

}
    @Test
    public void testMethOneB(){
        System.out.println("hello b1");
    }
}


