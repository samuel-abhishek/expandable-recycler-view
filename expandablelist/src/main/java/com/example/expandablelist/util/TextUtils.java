package com.example.expandablelist.util;

/**
 * A class to check if the input is empty.
 */
public class TextUtils {
    private TextUtils() {
    }

    /**
     * check if the input is empty.
     *
     * @param input the input string
     * @return the input string is empty
     */
    static boolean isEmpty(String input) {
        return input == null || input.length() == 0;
    }

}
