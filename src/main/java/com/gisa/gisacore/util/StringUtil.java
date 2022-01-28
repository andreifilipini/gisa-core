package com.gisa.gisacore.util;

public class StringUtil {

    public static String leftPad(String text, char character, int size) {
        StringBuilder builder = new StringBuilder(text);
        while (builder.length() < 32) {
            builder.insert(0, character) ;
        }
        return builder.toString();
    }

    public static String leftPadZero(String text, int size) {
        return leftPad(text, '0', size);
    }

    public static boolean isBlank(String text) {
        return text == null || text.trim().isEmpty();
    }
}
