package com.zw.jiguangNew.api.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

public class CryptoTools {
    /**
     * 加密算法的参数接口，IvParameterSpec是它的一个实现、
     */
    private AlgorithmParameterSpec iv = null;
    private Key key = null;

    public CryptoTools(String _DESkey, String _vi) throws Exception {
        // 设置密钥参数
        DESedeKeySpec  keySpec = new DESedeKeySpec(_DESkey.getBytes("UTF-8"));
        // 设置向量
        iv = new IvParameterSpec(_vi.getBytes("UTF-8"));
        // 获得密钥工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("desede");
        // 得到密钥对象
        key = keyFactory.generateSecret(keySpec);
    }

    private static CryptoTools cryptoTools= null;

    public static synchronized CryptoTools getCryptoTools(String key,String vi) throws Exception{
        if(cryptoTools==null){
            cryptoTools = new CryptoTools(key,vi);
        }
        return cryptoTools;
    }

    /**
     * CBC加密
     * @return Base64编码的密文
     */

    public String encode(String data) throws Exception {
        // 得到加密对象Cipher
        Cipher enCipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        // 得到加密对象Cipher
        enCipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] pasByte = enCipher.doFinal(data.getBytes("utf-8"));
        //Base64 字符串
        BASE64Encoder base64Encoder = new BASE64Encoder();
        return base64Encoder.encode(pasByte);
    }
    /**
     * CBC解密
     * @return 明文
     */

    public String decode(String data) throws Exception {
        // 得到加密对象Cipher
        Cipher deCipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        // 设置工作模式为加密模式，给出密钥和向量
        deCipher.init(Cipher.DECRYPT_MODE, key, iv);
        //Base64 字符串
        BASE64Decoder base64Decoder = new BASE64Decoder();
        byte[] pasByte = deCipher.doFinal(base64Decoder.decodeBuffer(data));

        return new String(pasByte, "UTF-8");
    }
    public static void main(String[] args) {
        try {
            String test = "byxtest";
            CryptoTools des = new CryptoTools("byxtest81234567890145796","BYXTEST01");

            System.out.println("加密前的字符："+test);
            System.out.println("加密后的字符："+des.encode(test));
            System.out.println("解密后的字符："+des.decode(des.encode(test)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}