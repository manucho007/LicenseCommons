package ru.rtksoftlabs.LicenseCommons.shared;

import java.security.NoSuchAlgorithmException;
import java.time.Instant;

public class CheckAccessResult {
    private boolean access;
    private Instant timestamp;
    private byte[] hash;

    public CheckAccessResult() {
    }

    public CheckAccessResult(String protectedObject, boolean access, Instant timestamp) throws NoSuchAlgorithmException {
        this.access = access;
        this.timestamp = timestamp;
        this.hash = ResponseSignUtil.generateHash(protectedObject, access, timestamp);
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
}
