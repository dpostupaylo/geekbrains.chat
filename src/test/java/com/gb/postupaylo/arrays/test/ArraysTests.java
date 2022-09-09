package com.gb.postupaylo.arrays.test;

import homework6.arrays.Main;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ArraysTests {
    Main main;

    @BeforeEach
    public void prepare(){
        main = new Main();
    }

    @Test
    public void returnArrayMethodSuccessfullTest(){
        int[] array = new int[]{1,2,4,4,2,3,4,1,7};
        int[] expectedArray = new int[]{1,7};

        int[] actualResult = main.squeezAndGiveMeAllAfterFour(array);

        Assertions.assertArrayEquals(expectedArray, actualResult);
    }

    @Test
    public void returnArrayMethodSuccesfullWithFourIsLasyElementTest(){
        int[] array = new int[]{1,2,4,4,2,3,4,1,7,4};
        int[] expectedArray = new int[]{};

        int[] actualResult = main.squeezAndGiveMeAllAfterFour(array);

        Assertions.assertArrayEquals(expectedArray, actualResult);
    }

    @Test
    public void returnArrayMethodSuccesfullWithFourIsAloneElementTest(){
        int[] array = new int[]{4};
        int[] expectedArray = new int[]{};

        int[] actualResult = main.squeezAndGiveMeAllAfterFour(array);

        Assertions.assertArrayEquals(expectedArray, actualResult);
    }

    @Test
    public void arrayIsEmptyTest(){
        int[] array = new int[]{};

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            main.squeezAndGiveMeAllAfterFour(array);
        }, "O oh");
    }

    @Test
    public void arrayIsNullTest(){
        int[] array = new int[]{};

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            main.squeezAndGiveMeAllAfterFour(null);
        }, "O oh");
    }

    @Test
    public void throwRuntimeExpetionTest(){
        int[] array = new int[]{1,2,2,3,1,7};

        Assertions.assertThrows(RuntimeException.class, () -> {
            main.squeezAndGiveMeAllAfterFour(array);
        }, "O oh");
    }
}
