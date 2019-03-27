package ru.rtksoftlabs.LicenseCommons.services;

import ru.rtksoftlabs.LicenseCommons.util.SignedLicenseContainer;

import java.io.IOException;

public interface ZipLicenseService {
    void zipLicense(SignedLicenseContainer signedLicenseContainer) throws IOException;
    void unzipLicense(SignedLicenseContainer signedLicenseContainer) throws IOException;
}
