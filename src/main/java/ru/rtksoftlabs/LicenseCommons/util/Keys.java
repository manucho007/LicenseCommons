package ru.rtksoftlabs.LicenseCommons.util;

import java.security.PrivateKey;
import java.security.cert.Certificate;

public class Keys {
    private PrivateKey privateKey;
    private Certificate certificate;

    public Keys(PrivateKey privateKey, Certificate certificate) {
        this.privateKey = privateKey;
        this.certificate = certificate;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public Certificate getCertificate() {
        return certificate;
    }
}
