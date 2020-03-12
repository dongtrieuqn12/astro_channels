package vn.dongtrieu.astrochannels.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayUtils {
    public static <T> List<T> arrayToList (T[] srcArray) {
        List<T> result = new ArrayList<>();
        if (srcArray != null) {
            result.addAll(Arrays.asList(srcArray));
        }
        return result;
    }
}
