package homework6.arrays;

import java.util.Arrays;

public class Main {

    public int[] squeezAndGiveMeAllAfterFour(int[] array){
        if (array == null || array.length == 0){
            throw new IllegalArgumentException("Array is empty");
        }

        int index = lastIndexOfFoure(array);

        if (index == -1){
            throw new RuntimeException("There is array with no four");
        }

        return Arrays.copyOfRange(array, index + 1, array.length);
    }

    private int lastIndexOfFoure(int[] array){
        int index = -1;

        for (int i = 0; i < array.length; i++) {
            if (array[i] == 4)
                index = i;
        }

        return index;
    }

    public boolean isThereOneOrFour(int[] array){
        boolean oneIsExist = false;
        boolean fourIsExist = false;
        int index = 0;

        if (array == null || array.length == 0)
            return false;

        while ((!oneIsExist || !fourIsExist) && index < array.length ) {
            if (array[index] == 1)
                oneIsExist = true;

            if (array[index] == 4)
                fourIsExist = true;

            index++;
        }

        return oneIsExist && fourIsExist;
    }
}
