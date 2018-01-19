package com.hld.stockmanagerbusiness.utils;


import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;

public class Encryptor {


    public static void main(String[] args)  {
        String appId = "wx4f4bc4dec97d474b";
        String sessionKey = "tiihtNczf5v6AKRyjwEUhQ==";
        String encryptedData ="CiyLU1Aw2KjvrjMdj8YKliAjtP4gsMZM"+
                "QmRzooG2xrDcvSnxIMXFufNstNGTyaGS"+
                "9uT5geRa0W4oTOb1WT7fJlAC+oNPdbB+"+
                "3hVbJSRgv+4lGOETKUQz6OYStslQ142d"+
                "NCuabNPGBzlooOmB231qMM85d2/fV6Ch"+
                "evvXvQP8Hkue1poOFtnEtpyxVLW1zAo6"+
                "/1Xx1COxFvrc2d7UL/lmHInNlxuacJXw"+
                "u0fjpXfz/YqYzBIBzD6WUfTIF9GRHpOn"+
                "/Hz7saL8xz+W//FRAUid1OksQaQx4CMs"+
                "8LOddcQhULW4ucetDf96JcR3g0gfRK4P"+
                "C7E/r7Z6xNrXd2UIeorGj5Ef7b1pJAYB"+
                "6Y5anaHqZ9J6nKEBvB4DnNLIVWSgARns"+
                "/8wR2SiRS7MNACwTyrGvt9ts8p12PKFd"+
                "lqYTopNHR1Vf7XjfhQlVsAJdNiKdYmYV"+
                "oKlaRv85IfVunYzO0IKXsyl7JCUjCpoG"+
                "20f0a04COwfneQAGGwd5oa+T8yO5hzuy"+
                "Db/XcxxmK01EpqOyuxINew==";
        String iv = "r7BXXKkLb8qrSNn05n0qiA==";

        for(int i=0;i<100;i++){
            String str=decodeEncryptedData(encryptedData,sessionKey,iv);
            System.out.println("str:"+str);
        }
    }

    public static String decodeEncryptedData(String encryptedData,String sessionKey,String iv){

        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);

        int base = 16;
        if (keyByte.length % base != 0) {
            int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
            keyByte = temp;
        }
        try {
            Security.addProvider(new BouncyCastleProvider());

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");

            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化

            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return result;
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (InvalidParameterSpecException e) {
            e.printStackTrace();
        }
        return "";
    }




}