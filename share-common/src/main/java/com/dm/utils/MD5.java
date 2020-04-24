package com.dm.utils;

import com.dm.enums.MD5ProcessType;
import com.dm.exception.runtime.NoSuchTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * @author hu.yuhao
 * MD5加密（加盐）
 * */
public class MD5 {
    private static final Logger logger = LoggerFactory.getLogger(MD5.class);

    private static final char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 随机生成盐
     * */
    private static String salt() {
        StringBuilder builder = new StringBuilder(16);
        Random random = new Random();
        for (int i = 0; i < builder.capacity(); i++) {
            builder.append(hex[random.nextInt(16)]);
        }
        return builder.toString();
    }

    /**
     * 通过数据库，获取用户加密时所用盐，逆向过程
     * @param info  加盐加密后的字符串
     * @return 返回salt
     * */
    private static String getSalt(String info) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            char[] chars = info.toCharArray();
            for (int i = 0; i < chars.length; i+=3) {
                stringBuilder.append(chars[i+1]);
            }
        }catch (Exception e) {
            logger.error("获取用户盐失败，错误信息：{}", e.getMessage());
        }
        return stringBuilder.toString();
    }

    /**
     * @author hu.yuhao
     * 加盐加密
     * @param hadSalt   已经包含盐（已经加密过的数据，从中获取当时的加密盐）
     * @param inputStr  用户输入
     * @param type  操作类型
     * @return String   加密密文
     * */
    public static String MD5WithSalt(String hadSalt, String inputStr, MD5ProcessType type) {
        String result = "";
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            String salt = "";
            //login means verify the password
            if (type == MD5ProcessType.LOGIN) {
                salt = getSalt(hadSalt);
            } else if (type == MD5ProcessType.REGISTER) {
                salt = salt();
            }else {
                throw new NoSuchTypeException("type error");
            }
            md5.update((inputStr+salt).getBytes("utf-8"));
            byte[] digest = md5.digest();
            //位移加密
            String temp = MD5ByShift(digest);
            //format String

            //将盐插入加密好的密文中
            char[] chars = new char[48];
            for (int i = 0; i < 48; i+=3) {
                chars[i] = temp.charAt(i/3*2);
                chars[i+1] = salt.charAt(i/3);
                chars[i+2] = temp.charAt(i/3*2 + 1);
            }
            result = new String(chars);
        }catch (NoSuchAlgorithmException e) {
            logger.error("no such algorithm exception, check the type");
        }catch (NoSuchTypeException e) {
            logger.error("no such process type, check the type:{}", type);
        } catch (UnsupportedEncodingException e) {
            logger.error("do not support the encoding, error message:{}", e.getMessage());
        }catch (Exception e) {
            logger.error("unknown error, please check, error message:{}", e.getMessage());
        }
        return result;
    }

    private static String MD5ByShift(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        int length = bytes.length;
        for (int i = 0; i< length; i++) {
            byte b = bytes[i];
            stringBuffer.append(hex[b >>> 4 & 0xf]);    //高四位
            stringBuffer.append(hex[b & 0xf]);          //低四位
        }
        return stringBuffer.toString();
    }

    /**
     * 格式化加密
     * */
    private static String MD5ByFormatString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b:bytes) {
            stringBuilder.append(String.format("%02X", b)); //不足2，前端补0
        }
        return stringBuilder.toString();
    }
}
