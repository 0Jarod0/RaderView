package com.example.haha.algorithm;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 哈希算法（Hash）又称摘要算法
 * 相同的输入一定得到相同的输出；
 * 不同的输入大概率得到不同的输出
 * 常见的：MD5 SHA-1 RipeMD-160 SHA-256 SHA-512
 * 哈希算法可用于验证数据完整性，具有防篡改检测的功能
 * MD5为了防止彩虹篡改 可加盐进行防止
 */
public class DigestAlgorithm {
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        // 反复调用update输入数据:
        messageDigest.update("Hello".getBytes("UTF-8"));
        messageDigest.update("World".getBytes("UTF-8"));
        byte[] result = messageDigest.digest(); // 16 bytes: 68e109f0f40ca72a15e05cc22786f8e6
        System.out.println(new BigInteger(1, result).toString(16));
    }
}
