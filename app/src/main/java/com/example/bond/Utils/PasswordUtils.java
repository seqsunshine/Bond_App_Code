package com.example.bond.Utils;

import android.util.Base64;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordUtils {

    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";

    //generates random salt
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16]; //16 bytes salt
        random.nextBytes(salt);
        return Base64.encodeToString(salt, Base64.NO_WRAP);
    }

    //hashes the password with salt using PBKDF2
    public static String hashPassword(String password, String salt) {
        char[] passwordChars = password.toCharArray();
        byte[] saltBytes = Base64.decode(salt, Base64.NO_WRAP);

        PBEKeySpec spec = new  PBEKeySpec(passwordChars, saltBytes, ITERATIONS, KEY_LENGTH);
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] hashedBytes = keyFactory.generateSecret(spec).getEncoded();
            return Base64.encodeToString(hashedBytes, Base64.NO_WRAP);
        } catch (NoSuchAlgorithmException e){
            throw new RuntimeException("Error while hashing password: " + e.getMessage(), e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

    //generates password hash, combining salt and hashed password, salt:hash
    public static String generateSecurePassword(String password) {
        String salt = generateSalt();
        String hash = hashPassword(password, salt);
        return salt + ":" + hash;
    }

    //verifies provided password, when hashed with salt, matches stored hash
    public static boolean verifyPassword(String password, String stored) {
        //stored string format should be salt:hash
        String[] parts = stored.split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Stored password must be in the format 'salt:hash'");
        }
        String salt = parts[0];
        String storedHash = parts[1];
        String computedHash = hashPassword(password, salt);
        return computedHash.equals(storedHash);
    }
}
