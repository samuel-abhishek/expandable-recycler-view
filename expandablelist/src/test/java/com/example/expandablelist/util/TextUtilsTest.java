package com.example.expandablelist.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class TextUtilsTest {


    @Test
    public void testIsEmptyWithNull() {
        boolean val1= TextUtils.isEmpty(null);
        assertTrue(val1);
    }

    @Test
    public void testIsEmptyWithEmpty() {
        boolean val1= TextUtils.isEmpty("");
        assertTrue(val1);
    }

    @Test
    public void testIsEmptyWithNotNull() {
        boolean val1= TextUtils.isEmpty("a12b%&c");
        assertFalse(val1);
    }

}