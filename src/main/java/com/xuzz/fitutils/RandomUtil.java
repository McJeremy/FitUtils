package com.xuzz.fitutils;

import java.util.Random;

public class RandomUtil {
    private static String base = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static String numBase = "0123456789";

    /**
     * 随机产生固定位数字符串
     *
     * @param length 生成字符串的长度
     * @return
     */
    public static String getRandomString(int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 随机产生固定位数字
     *
     * @param length
     * @return
     */
    public static String getRandomNum(int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(numBase.length());
            sb.append(numBase.charAt(number));
        }
        return sb.toString();
    }

}
