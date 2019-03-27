package ru.rtksoftlabs.LicenseCommons.services;

import ru.rtksoftlabs.LicenseCommons.shared.ProtectedObject;

import java.util.List;

public interface ProtectedObjectsService {
    List<ProtectedObject> getProtectedObjects();
    void updateProtectedObjects();
}
