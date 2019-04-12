package ru.rtksoftlabs.LicenseCommons.shared;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;

public class ResponseSignUtil {
    public static byte[] generateHash(String protectedObject, boolean access, Instant timestamp) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");

        String saltString = "RtKDec78";

        StringBuilder stringToEncrypt = new StringBuilder(protectedObject.replace("/", ""));

        stringToEncrypt.append(access);

        stringToEncrypt.append(timestamp.toString());

        stringToEncrypt.append(saltString);

        messageDigest.update(stringToEncrypt.toString().getBytes());

        return Base64.getEncoder().encode(messageDigest.digest());
    }
}
