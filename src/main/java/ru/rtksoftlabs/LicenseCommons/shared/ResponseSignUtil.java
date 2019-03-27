package ru.rtksoftlabs.LicenseCommons.shared;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;

public class ResponseSignUtil {
    private boolean access;
    private Instant timestamp;
    private byte[] hash;

    public ResponseSignUtil(String protectedObject, boolean access, Instant timestamp) throws NoSuchAlgorithmException {
        this.access = access;
        this.timestamp = timestamp;
        this.hash = generateHash(protectedObject, access, timestamp);
    }

    public boolean isAccess() {
        return access;
    }

    public void setAccess(boolean access) {
        this.access = access;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

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
