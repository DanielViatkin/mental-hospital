package com.epam.hospital.util;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Hasher {
    private static final int iterations = 5;
    private static final int keyLength = 512;
    private static final String ALGORITHM_NAME = "PBKDF2WithHmacSHA512";

    public String hashString(String line, byte[] salt) {
        char[] passwordChars = line.toCharArray();
        byte[] hashedBytes;
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM_NAME);
            PBEKeySpec spec = new PBEKeySpec(passwordChars, salt, iterations, keyLength);
            SecretKey key = skf.generateSecret(spec);
            hashedBytes = key.getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        return Hex.encodeHexString(hashedBytes);
    }

}
