package ru.rtksoftlabs.LicenseCommons.inno;

import ru.rtksoftlabs.LicenseCommons.shared.ProtectedObject;
import ru.rtksoftlabs.LicenseCommons.services.ProtectedObjectsService;

import java.util.ArrayList;
import java.util.List;

public class ProtectedObjectsServiceImpl implements ProtectedObjectsService {
    @Override
    public List<ProtectedObject> getProtectedObjects() {
        List<ProtectedObject> protectedObjects = new ArrayList<>();

        ProtectedObject protectedObject1 = new ProtectedObject("App1");
        ProtectedObject protectedObject2 = new ProtectedObject("App2");

        ProtectedObject child = protectedObject1.addChild("Scripts");

        child.addChild("sc1");
        child.addChild("sc2");
        child.addChild("sc3");

        protectedObject1.addChild("Roles");

        protectedObjects.add(protectedObject1);
        protectedObjects.add(protectedObject2);

        return protectedObjects;
    }

    @Override
    public void updateProtectedObjects() {
        // In that profile not needed to implement
    }
}
