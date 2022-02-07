package com.gisa.gisacore.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class CipherUtil {

    public static String encrypt32(String text) {
        return encrypt(text, 32);
    }

    public static String encrypt64(String text) {
        return encrypt(text, 64);
    }

    private static String encrypt(String text, Integer size) {
        try {
            if(StringUtil.isBlank(text)) {

            }
            byte[] textBytes = text.getBytes(StandardCharsets.UTF_8);
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] textDigested = digest.digest(textBytes);

            BigInteger digestedAsBigInteger = new BigInteger(1, textDigested);

            return StringUtil.leftPadZero(NumberUtil.toHex(digestedAsBigInteger), size);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
