package com.hitwh.vblog.util;

import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.EnumError;
import org.springframework.util.DigestUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MyMd5 {

    public static String md5Encryption(String plainText) throws BusinessException {
        String result = "";
        System.out.println("text  " + plainText );
        try {
            result = DigestUtils.md5DigestAsHex(plainText.getBytes());
        }catch (Exception e){
            throw new BusinessException(EnumError.MD5_ERROR);
        }
        return result;
    }

    public static Map<String,Object> md5SaltEncryption(String plainText) throws BusinessException {
        String result = "";
        Random random = new Random(System.currentTimeMillis());
        Integer salt = random.nextInt(8999) + 1000;
        try {
            String md5salt = DigestUtils.md5DigestAsHex(salt.toString().getBytes());
            String md5pwd = DigestUtils.md5DigestAsHex(plainText.getBytes());
            result = DigestUtils.md5DigestAsHex((md5pwd + md5salt).getBytes());
        }catch (Exception e){
            throw new BusinessException(EnumError.MD5_ERROR);
        }
        System.out.println(salt);
        Map<String,Object> map = new HashMap<>();
        map.put("encryption",result);
        map.put("salt", salt);
        return map;
    }


    public static Map<String,Object> GetToken(String plainText) throws BusinessException {
        String token = "";
        long currentTime = System.currentTimeMillis();
        long expiryTime = currentTime + 21600000;
        try {
            String currentTimeMD5 = DigestUtils.md5DigestAsHex(String.valueOf(currentTime).getBytes());
            String plainTextMD5 = DigestUtils.md5DigestAsHex(plainText.getBytes());
            token = DigestUtils.md5DigestAsHex((currentTimeMD5 + plainTextMD5).getBytes());
        }catch (Exception e){
            throw new BusinessException(EnumError.MD5_ERROR);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("currentTime", currentTime);
        map.put("expiryTime",expiryTime);
        System.out.println(currentTime);
        System.out.println(expiryTime);
        return map;
    }
}
