package com.sevadev.javaassignment.utils;

public class StringUtils {

    public static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        } else {
            return str.isEmpty();
        }
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
