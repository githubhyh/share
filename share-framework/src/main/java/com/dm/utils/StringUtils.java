package com.dm.utils;

public class StringUtils {
    public static boolean isNotNull(String str) {
        if (str == null || "".equals(str)) {
            return false;
        }
        return true;
    }

    public static boolean isNotNull(Object obj) {
        if (obj == null) {
            return false;
        }
        return true;
    }
}
