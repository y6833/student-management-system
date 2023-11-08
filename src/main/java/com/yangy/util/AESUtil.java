package com.yangy.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 用于对密码加密
 */
public class AESUtil {
    private static final String ALGORITHM = "AES";

//    public static void main(String[] args) throws Exception {
//        String originalText = "Hello, World!";
//        System.out.println("原始文本： " + originalText);
//
//        // 生成密钥
//        SecretKey secretKey = generateKey();
//
//        // 加密
//        String encryptedText = encrypt(originalText, secretKey);
//        System.out.println("加密后的文本： " + encryptedText);
//
//        // 解密
//        String decryptedText = decrypt(encryptedText, secretKey);
//        System.out.println("解密后的文本： " + decryptedText);
//    }

    /**
     * 生成密钥
     * @return
     * @throws Exception
     */
    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(128); // 设置密钥长度为128位
        return keyGenerator.generateKey();
    }

    /**
     * 加密
     * @param plainText
     * @param secretKey
     * @return
     * @throws Exception
     */
    public static String encrypt(String plainText, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * 解密
     * @param encryptedText
     * @param secretKey
     * @return
     * @throws Exception
     */
    public static String decrypt(String encryptedText, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
