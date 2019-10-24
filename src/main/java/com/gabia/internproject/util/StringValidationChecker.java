package com.gabia.internproject.util;

public class StringValidationChecker {
    private static final String DEFAULT_MESSAGE = "Received an invalid parameter";

    public static void checkNotNull(Object object, String errorMsg) {
        check(object != null, errorMsg);
    }
    public static void checkEmptyString(String string, String errorMsg) {
        check(hasText(string), errorMsg);
    }
    public static boolean hasText(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        str=str.replaceAll("\\s+","");
        if(str.isEmpty()){
            return false;
        }
        return true;
    }
    private static void check(boolean requirements, String error) {
        if (!requirements) {
            throw new IllegalArgumentException(hasText(error) ? error : DEFAULT_MESSAGE);
        }
    }
}
