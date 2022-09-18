package homework7.reflection.api;

import homework7.reflection.api.annotations.AfterSuite;
import homework7.reflection.api.annotations.BeforeSuite;
import homework7.reflection.api.annotations.Test;

public class TestClass {

    @BeforeSuite
    public void preparedata(){
        System.out.println("Hi! I'm being executed before each test");
    }

    @Test
    public void firstTest(){
        System.out.println("Hi! I'm just test");
    }

    @Test
    public void secondTest(){
        System.out.println("Hi! I'm just test number two");
    }

    @Test(priority = 10)
    public void testWithPriority(){
        System.out.println("Hi! I should be first");
    }


    @AfterSuite
    public void tearDown(){
        System.out.println("Hi! I'm being executed after each test");
    }
}
