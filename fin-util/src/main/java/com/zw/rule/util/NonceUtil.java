package com.zw.rule.util;

import org.springframework.util.StringUtils;

import java.util.Random;

/**
 * Created by Administrator on 2017/6/23 0023.
 */
public class NonceUtil {
    public static final String LOWERCHARANDNUMBER= "abcdefghijklmnopqrstuvwxyz0123456789";

    public static final String CHARANDNUMBER = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final Random random = new Random();

    public static String nonce(String source,int length) {
        if(length<1) return "";
        if(StringUtils.isEmpty(source)) source = LOWERCHARANDNUMBER;
        int size = LOWERCHARANDNUMBER.length();
        String result = "";
        for(int i=0; i<length; i++) {
            int index = random.nextInt(size);
            result += source.charAt(index);
        }

        return result;
    }
}
