package com.gisa.gisacore.util;

import java.math.BigInteger;

public class NumberUtil {

    public static String toHex(BigInteger number) {
        return number.toString(16);
    }
}
