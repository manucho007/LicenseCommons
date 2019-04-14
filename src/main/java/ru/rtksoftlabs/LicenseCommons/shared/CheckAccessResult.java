package ru.rtksoftlabs.LicenseCommons.shared;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class CheckAccessResult {
    private boolean access;
    private Date timestamp;
    private byte[] hash;

    public CheckAccessResult() {
    }

    public CheckAccessResult(String protectedObject, boolean access, Date timestamp) throws NoSuchAlgorithmException {
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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }
}
