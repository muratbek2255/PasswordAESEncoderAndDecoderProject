package com.example.sarsaproject;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;


@Service
public class AESService {

    byte[] sec_bytes = new byte[16];

    String transformation="AES/CBC/PKCS5Padding";

    public byte[] encrypt(SecretKeySpec spec, byte[] plainText) {

        try {
            Cipher cipher = Cipher.getInstance(transformation);
            if (transformation.contains("ECB"))
                cipher.init(Cipher.ENCRYPT_MODE, spec);
            else {
                IvParameterSpec ivSpec;
                ivSpec = new IvParameterSpec(sec_bytes);
                cipher.init(Cipher.ENCRYPT_MODE, spec,
                        ivSpec);
            }
            return cipher.doFinal(plainText);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public byte[] decrypt(SecretKeySpec spec, byte[] encryptedText) {

        try {
            Cipher cipher = Cipher.getInstance(transformation);
            if (transformation.contains("ECB"))
                cipher.init(Cipher.DECRYPT_MODE, spec);
            else {
                IvParameterSpec ivSpec;
                ivSpec = new IvParameterSpec(sec_bytes);
                cipher.init(Cipher.DECRYPT_MODE, spec,
                        ivSpec);
            }
            return cipher.doFinal(encryptedText);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public SecretKeySpec createSecretKey()
    {
        SecretKeySpec sks = null;
        byte[] bytes = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(bytes);
        try {
            MessageDigest md;
            byte[] key;
            md = MessageDigest.getInstance("SHA-1");

            key = md.digest(bytes);
            key = Arrays.copyOf(key, 16);
            sks = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) { }
        return sks;
    }

    public HashMap<String, HashMap<String, String>> encryptService(AESRequest aesRequest) {

        SecretKeySpec secretKeySpec = createSecretKey();
        String password = aesRequest.getPassword();

        byte[] enc = encrypt(secretKeySpec, password.getBytes());

        byte[] bytes = decrypt(secretKeySpec, enc);
        String plainAfter = new String(bytes);

        HashMap<String, HashMap<String, String>> map = new HashMap<>();

        map.put("AES", new HashMap() {{
            put("1.encrypt", enc);
        }});

        map.get("AES").put("2.descrypt", plainAfter);

        return map;
    }
}
