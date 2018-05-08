package com.zw.base.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年07月04日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) Vbill Co.,Ltd.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:wangmin <br>
 *         email: <br>
 * @version <strong>zw有限公司-运营平台</strong><br>
 *          <br>
 *          <strong>修改历史:</strong><br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 *          <br>
 *          <br>
 */
public class ArithUtil {
    private static final int DEF_DIV_SCALE = 2;

    public ArithUtil() {
    }

    public static double add(double v1, double v2) {
        if(v1 == 0.0D && v2 == 0.0D) {
            return 0.0D;
        } else {
            BigDecimal b1 = new BigDecimal(Double.toString(v1));
            BigDecimal b2 = new BigDecimal(Double.toString(v2));
            return b1.add(b2).doubleValue();
        }
    }

    public static double add(Double[] ints) {
        double sum = 0.0D;
        if(ints != null && ints.length != 0) {
            Double[] var3 = ints;
            int var4 = ints.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Double temp = var3[var5];
                BigDecimal b1 = new BigDecimal(Double.toString(temp.doubleValue()));
                BigDecimal b2 = new BigDecimal(Double.toString(sum));
                sum = b1.add(b2).doubleValue();
            }

            return sum;
        } else {
            return sum;
        }
    }

    public static double sub(double v1, double v2) {
        if(v1 == 0.0D && v2 == 0.0D) {
            return 0.0D;
        } else {
            BigDecimal b1 = new BigDecimal(Double.toString(v1));
            BigDecimal b2 = new BigDecimal(Double.toString(v2));
            return b1.subtract(b2).doubleValue();
        }
    }

    public static double mul(double v1, double v2) {
        if(v1 == 0.0D && v2 == 0.0D) {
            return 0.0D;
        } else {
            BigDecimal b1 = new BigDecimal(Double.toString(v1));
            BigDecimal b2 = new BigDecimal(Double.toString(v2));
            return b1.multiply(b2).doubleValue();
        }
    }

    public static double div(double v1, double v2) {
        return v1 == 0.0D && v2 == 0.0D?0.0D:div(v1, v2, 2);
    }

    public static double div(double v1, double v2, int scale) {
        if(v1 == 0.0D && v2 == 0.0D) {
            return 0.0D;
        } else if(scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        } else {
            BigDecimal b1 = new BigDecimal(Double.toString(v1));
            BigDecimal b2 = new BigDecimal(Double.toString(v2));
            return b1.divide(b2, scale, 4).doubleValue();
        }
    }

    public static double round(double v, int scale) {
        if(scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        } else {
            BigDecimal b = new BigDecimal(Double.toString(v));
            BigDecimal one = new BigDecimal("1");
            return b.divide(one, scale, 4).doubleValue();
        }
    }

    public static String getTwoNumber(double v) {
        DecimalFormat df = new DecimalFormat("##0.00");
        return df.format(v);
    }

    public static int getInteg(double num) {
        String s = round(num, 2) + "";
        int index = s.indexOf(46);
        String l = s.substring(0, index);
        return Integer.parseInt(l);
    }

    public static String toPenny(double money) {
        String amt = mul(money, 100.0D) + "";
        BigDecimal decimal = new BigDecimal(amt);
        amt = decimal.toPlainString();
        int index = amt.lastIndexOf(".");
        if(index > 0) {
            amt = amt.substring(0, index);
        }

        return amt;
    }

    public static String formatDouble(double data) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(data);
    }

    public static double getDouble(double amt) {
        BigDecimal b = BigDecimal.valueOf(amt);
        return b.setScale(2, 4).doubleValue();
    }
}
