package com.gisa.gisacore.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class CipherUtil {

    public static String encrypt(String text) {
        try {
            if(StringUtil.isBlank(text)) {

            }
            byte[] textBytes = text.getBytes(StandardCharsets.UTF_8);
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] textDigested = digest.digest(textBytes);

            BigInteger digestedAsBigInteger = new BigInteger(1, textDigested);

            return StringUtil.leftPadZero(NumberUtil.toHex(digestedAsBigInteger), 64);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
