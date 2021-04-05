package utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class HashingService {
    public String[] hashPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String[] result = new String[2];
        byte[] salt = getSalt();
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        result[0] = Base64.getEncoder().encodeToString(salt);
        result[1] = Base64.getEncoder().encodeToString(hash);
        return result;
    }

    public String hashPasswordWithoutSalt(String password, String strSalt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String result = new String();
        byte[] salt = Base64.getDecoder().decode(strSalt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        result = Base64.getEncoder().encodeToString(hash);
        return result;
    }

    private byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
}
