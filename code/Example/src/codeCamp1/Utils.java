package codeCamp1;

public class Utils {

    /** A parameterized method to compute the maximum of the array */
    public static <T extends Comparable<T>>  T  findMax(T[] array) {
        // FILL IN CODE
        if (array.length == 0)
            throw new IllegalArgumentException("Invalid parameter");
        T max = array[0];
        for (T elem : array) {
            if (elem.compareTo(max) > 0)
                max = elem;
        }
        return max;

    }

}

