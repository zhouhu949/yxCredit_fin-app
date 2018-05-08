package com.zw.security.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年07月20日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) zw.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:Administrator <br>
 *         email: <br>
 * @version <strong>zw有限公司-运营平台</strong><br>
 *          <br>
 *          <strong>修改历史:</strong><br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 *          <br>
 *          <br>
 */
public class AmountToBigUtil {

        private static final String UNIT = "万千佰拾亿千佰拾万千佰拾元角分";

        private static final String DIGIT = "零壹贰叁肆伍陆柒捌玖";

        private static final double MAX_VALUE = 9999999999999.99D;

        public static String change(double v) {

            if (v < 0 || v > MAX_VALUE){

                return "参数非法!";

            }

            long l = Math.round(v * 100);

            if (l == 0){

                return "零元整";

            }

            String strValue = l + "";

            // i用来控制数

            int i = 0;

            // j用来控制单位

            int j = UNIT.length() - strValue.length();

            String rs = "";

            boolean isZero = false;

            for (; i < strValue.length(); i++, j++) {

                char ch = strValue.charAt(i);

                if (ch == '0') {

                    isZero = true;

                    if (UNIT.charAt(j) == '亿' || UNIT.charAt(j) == '万' || UNIT.charAt(j) == '元') {

                        rs = rs + UNIT.charAt(j);

                        isZero = false;

                    }

                } else {

                    if (isZero) {

                        rs = rs + "零";

                        isZero = false;

                    }

                    rs = rs + DIGIT.charAt(ch - '0') + UNIT.charAt(j);

                }

            }

            if (!rs.endsWith("分")) {

                rs = rs + "整";

            }

            rs = rs.replaceAll("亿万", "亿");

            return rs;

        }



        public static void main(String[] args){
//            Date d = new Date();
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
////            String num = sdf.format(d);

            System.out.println(AmountToBigUtil.change(12356789.9845));
//            Random random = new Random();
//            random.doubles(1);
//            String num1 = num.substring(0,8);
//            String num2 = num.substring(8,14);
//            int num3 = (int)((Math.random() * 9 + 1) * 10);
//            String num4 = num1+"-"+num2+"-"+num3;
//            System.out.println(num4);
        }

}

