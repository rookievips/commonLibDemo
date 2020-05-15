package com.chen.api.security;

import java.security.MessageDigest;

/**
 * MD5加密类
 */
public class MD5 {
    /**
     * md5加密
     *
     * @param info
     * @return
     */
    public static String getMD5(String info) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes("UTF-8"));
            byte[] encryption = md5.digest();

            StringBuilder strBuffer = new StringBuilder();
            for (byte anEncryption : encryption) {
                if (Integer.toHexString(0xff & anEncryption).length() == 1) {
                    strBuffer.append("0").append(Integer.toHexString(0xff & anEncryption));
                } else {
                    strBuffer.append(Integer.toHexString(0xff & anEncryption));
                }
            }

            return strBuffer.toString();
        } catch (Exception e) {
            return null;
        }
    }
}
