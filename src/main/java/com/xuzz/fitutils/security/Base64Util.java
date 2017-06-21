package com.xuzz.fitutils.security;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

public class Base64Util {
    /**
     * 将字符串为BASE64字符串
     *
     * @param str
     * @return
     */
    public static String encodeToString(String str) {
        try {

            return encode(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * 将BASE64字符串恢复为二进制数据
     *
     * @param str
     * @return
     */
    public static String decodeToString(String str) {
        try {
            return new String(decode(str));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将二进制数据编码为BASE64字符串
     *
     * @param binaryData
     * @return
     */
    public static String encode(byte[] binaryData) {
        try {
            return new String(Base64.encodeBase64(binaryData), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * 将BASE64字符串恢复为二进制数据
     *
     * @param base64String
     * @return
     */
    public static byte[] decode(String base64String) {
        try {
            return Base64.decodeBase64(base64String.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
