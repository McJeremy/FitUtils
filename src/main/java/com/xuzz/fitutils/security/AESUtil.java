package com.xuzz.fitutils.security;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class AESUtil {
    private static final String KEY_ALGORITHM = "AES";
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final String DEFAULT_CHARSET = "UTF-8";

    private static final Map<String, SecretKey> keyMap = new HashMap<String, SecretKey>();

    private static SecretKey toAESKey(String key) throws NoSuchAlgorithmException {
        SecretKey k = keyMap.get(key);
        if (k == null) {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(key.getBytes());

            KeyGenerator kgen = KeyGenerator.getInstance(KEY_ALGORITHM);
            kgen.init(128, random);

            k = kgen.generateKey();

            keyMap.put(key, k);
        }
        return k;
    }

    public static String encrypt(String text, String key) {
        if (text == null) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, toAESKey(key));
            return Base64.encodeBase64String(cipher.doFinal(text.getBytes(DEFAULT_CHARSET)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String text, String key) {
        if (text == null) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, toAESKey(key));
            return new String(cipher.doFinal(Base64.decodeBase64(text)), DEFAULT_CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
