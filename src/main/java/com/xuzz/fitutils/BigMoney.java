package com.xuzz.fitutils;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class BigMoney
{
    private static final int DEF_DIV_SCALE = 10; //这个类不能实例化

    public static String parseMoney(double target) {
        DecimalFormat df = new DecimalFormat(",###,###.00");
        df.setRoundingMode(RoundingMode.FLOOR);
        if (target < 1) {
            return "0" + df.format(target);
        }
        return df.format(target);
    }

    public static Double parseDouble(double target) {
        DecimalFormat df = new DecimalFormat("#.00");
        df.setRoundingMode(RoundingMode.FLOOR);
        BigDecimal bg = new BigDecimal(df.format(target));
        return bg.doubleValue();
    }

    public static Double parseDouble(Object target) {
        DecimalFormat df = new DecimalFormat("#.00");
        df.setRoundingMode(RoundingMode.FLOOR);
        BigDecimal bg = new BigDecimal(df.format(target));
        return bg.doubleValue();
    }

    /**
     * 加法
     * @param b1
     * @param b2
     * @return
     */
    public static Double add(Double b1, Double b2) {
        if(b1 == null) b1 =0d;
        if(b2 == null) b2 =0d;
        DecimalFormat df = new DecimalFormat("#.00");
        df.setRoundingMode(RoundingMode.FLOOR);
        BigDecimal d1 = buildBigDecimal(b1,false);
        BigDecimal d2 = buildBigDecimal(b2,false);
        BigDecimal bg = new BigDecimal(df.format(d1.add(d2)));
        return bg.doubleValue();
    }

    /**
     * 加法
     * @param b1
     * @param b2
     * @return
     */
    public static BigDecimal addReBig(Double b1, Double b2) {
        if(b1 == null) b1 =0d;
        if(b2 == null) b2 =0d;
        DecimalFormat df = new DecimalFormat("#.00");
        df.setRoundingMode(RoundingMode.FLOOR);
        BigDecimal d1 = buildBigDecimal(b1,false);
        BigDecimal d2 = buildBigDecimal(b2,false);
        BigDecimal bg = new BigDecimal(df.format(d1.add(d2)));
        return bg;
    }

    /**
     * 提供精确的减法运算。
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static BigDecimal sub(double v1,double v2){
        BigDecimal b1 =buildBigDecimal(v1,false);
        BigDecimal b2 =buildBigDecimal(v2,false);
        DecimalFormat df = new DecimalFormat("#.00");
        df.setRoundingMode(RoundingMode.FLOOR);
        BigDecimal bg = new BigDecimal(df.format(b1.subtract(b2)));
        return bg;
    }

    /**
     * 提供精确的乘法运算。
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static BigDecimal mul(double v1,double v2){
        BigDecimal b1 = buildBigDecimal(v1,false);
        BigDecimal b2 = buildBigDecimal(v2,false);
        DecimalFormat df = new DecimalFormat("#.00");
        df.setRoundingMode(RoundingMode.FLOOR);
        BigDecimal bg = new BigDecimal(df.format(b1.multiply(b2)));
        return bg;
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static BigDecimal div(double v1,double v2){
        return div(v1,v2,DEF_DIV_SCALE);
    }
    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static BigDecimal div(double v1,double v2,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = buildBigDecimal(v1,false);
        BigDecimal b2 = buildBigDecimal(v2,false);
        DecimalFormat df = new DecimalFormat("#.00");
        BigDecimal bg = new BigDecimal(df.format(b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP)));
        return bg;
    }

    /**
     * 提供精确的小数位四舍五入处理。
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static BigDecimal round(double v,int scale){
        if(scale<0){
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = buildBigDecimal(v,false);
        BigDecimal one = new BigDecimal("1");
        return b.divide(one,scale,BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 处理金额(限制了2位小数点）
     *
     * @param target
     * @return
     */
    public static BigDecimal buildBigDecimal(double target) {
//        DecimalFormat df = new DecimalFormat("#.00");
//        df.setRoundingMode(RoundingMode.FLOOR);
//        BigDecimal bg = new BigDecimal(df.format(target));
//        return bg;
        return buildBigDecimal(target,true);
    }
    /**
     * 处理金额（不限精度)
     *
     * @param target
     * @param bFixScale 是否限制小数点位数（限制时，默认限制2位）
     * @return
     */
    public static BigDecimal buildBigDecimal(double target,boolean bFixScale) {
        if(bFixScale) {
            DecimalFormat df = new DecimalFormat("#.00");
            df.setRoundingMode(RoundingMode.FLOOR);
            BigDecimal bg = new BigDecimal(df.format(target));
            return bg;
        }
        else
        {
            return new BigDecimal(Double.toString(target));
        }
    }
}