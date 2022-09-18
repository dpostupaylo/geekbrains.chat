package com.gb.postupaylo.arrays.test;

import homework6.arrays.Main;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OneOrFourExistTests {
    private Main main;

    @BeforeEach
    public void prepare(){
        main = new Main();
    }

    @Test
    public void seccessfullTest(){
        int[] array = new int[]{4,4,4,4,1,1,4};
        Assertions.assertEquals(true, main.isThereOneOrFour(array));
    }

    @Test
    public void noOneTest(){
        int[] array = new int[]{4,4,4,4,4};
        Assertions.assertEquals(false, main.isThereOneOrFour(array));
    }

    @Test
    public void noFourTest(){
        int[] array = new int[]{1,1,1,1,1,1,1,1};
        Assertions.assertEquals(false, main.isThereOneOrFour(array));
    }

    @Test
    public void noFourAndOneTest(){
        int[] array = new int[]{5,6,7,8};
        Assertions.assertEquals(false, main.isThereOneOrFour(array));
    }

    @Test
    public void negativeVariousTest(){
        int[] array = new int[]{-5,3,4,-1,-6,7,8};
        Assertions.assertEquals(false, main.isThereOneOrFour(array));
    }

    @Test
    public void emptyArrayTest(){
        int[] array = new int[]{};
        Assertions.assertEquals(false, main.isThereOneOrFour(array));
    }

    @Test
    public void nullArrayTest(){
        Assertions.assertEquals(false, main.isThereOneOrFour(null));
    }
}
