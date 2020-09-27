package com.ecy.show.util;


import com.ecy.show.entity.keytop.PostParameters;
import com.ecy.show.global.Constant;
import com.google.gson.Gson;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class DESUtil {
    private static String encoding = "gb2312";

    public static String encrypt(String key, String iv, String data) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key.getBytes(), "desede");
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ips);
        byte[] encryptData = cipher.doFinal(data.getBytes(encoding));
        return Base64.getEncoder().encodeToString(encryptData);
    }

    public static String decrypt(String key, String iv, String data) throws Exception {
            SecretKey secretKey = new SecretKeySpec(key.getBytes(), "desede");
            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ips);

            byte[] decryptData = cipher.doFinal(Base64.getDecoder().decode(data.getBytes(encoding)));
            return new String(decryptData, "GB2312");
    }
    public static void main(String[] args) throws Exception {
        String data = "{\"parkCode\":\"1\",\"plateNo\":\"苏AU038W\",\"cardNo\":\"\",\"entryTime\":\"2019-08-05 22:32:45\",\"leaveTime\":\"2019-08-05 22:32:45\",\"preMoney\":0,\"payMoney\":0,\"payType\":0,\"ticketMoney\":0,\"ticketTime\":0,\"freeMoney\":0,\"carType\":0,\"leavePlace\":\"172.19.8.243\",\"imgName\":\"20190805220032000_172.19.2.13_000007.jpg\"} ";
        String key = Constant.KEYTOP_APP_KEY;
        String iv = MyUtils.getNowDateyyyyMMdd();
        String encryption = encrypt(key, iv, data);
        System.out.println("加密结果："+encryption);
        String decryption = decrypt(key, iv, encryption);
        System.out.println("解密结果："+decryption);

        PostParameters keyTopCarInInfo=   new PostParameters()
                .setParkCode("123")
                .setPlateNo("苏C77889")
                .setCardNo("123123")
                .setEntryTime("2019/11/13 10:55:55")
                .setLeaveTime("2019/11/13 11:55:55")
                .setImgName("xxxxxxx.jpg")
                .setCarType(0)
                ;

        String data2=new Gson().toJson(keyTopCarInInfo);
        System.out.println("data1："+data);
        System.out.println("data2："+data2);

        String encryption2 = encrypt(key, iv, data2);
        System.out.println("加密结果："+encryption2);
        String decryption2 = decrypt(key, iv, encryption);
        System.out.println("解密结果："+decryption2);
    }
}