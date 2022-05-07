package com.zzw.utils.encryption;


import org.springframework.stereotype.Component;

import javax.crypto.*;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AesTest {

    static final String WAY = "AES";  //定义加密方式
    /**
     * 生成秘钥
     * @return
     */
    public static SecretKey generateKey() throws NoSuchAlgorithmException {
        //秘钥生成对象
        KeyGenerator secretGenerator = KeyGenerator.getInstance(WAY);
        //Random是一个伪随机数生成器，因为它是根据当前的时间来生成的
        //这是一个安全的随机数
        SecureRandom secureRandom = new SecureRandom();
        secretGenerator.init(secureRandom);

        SecretKey key = secretGenerator.generateKey();
        return key;
    }

    /**
     * 编码设置
     *
     */
    static Charset  charset = Charset.forName("UTF-8");

    /**
     * AES加密
     * 参数一：要进行加密的明文
     * 参数二：秘钥：统一使用：1985718124500000，当然也可以使用生成key的函数
     */
    public static byte[] encrypt(String content, SecretKey secretKey) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        return aes(content.getBytes(charset),Cipher.ENCRYPT_MODE,secretKey);
    }

    /**
     * AES解密
     * 参数一：密文
     * 参数二：秘钥
     */
    public static String decrypt(byte[] content,SecretKey secretKey) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {
        byte[] result = aes(content,Cipher.DECRYPT_MODE,secretKey);
        return new String(result,"UTF-8");
    }

    /**
     *
     * 真正执行加解密操作的函数
     * @param contentArray
     * @param mode
     * @param secretKey
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    private static byte[] aes(byte[] contentArray,int mode,SecretKey secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        //真正执行加解密操作的就是Cipher对象了
        Cipher cipher = Cipher.getInstance(WAY);
        cipher.init(mode,secretKey);  //通过mode指定是加密还是解密
        byte[] result = cipher.doFinal(contentArray);  //进行加解密操作
        return result;
    }


}
