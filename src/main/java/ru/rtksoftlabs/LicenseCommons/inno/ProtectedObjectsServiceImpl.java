package ru.rtksoftlabs.LicenseCommons.inno;

import ru.rtksoftlabs.LicenseCommons.shared.ProtectedObject;
import ru.rtksoftlabs.LicenseCommons.services.ProtectedObjectsService;
import ru.rtksoftlabs.LicenseCommons.shared.ProtectedObjects;

import java.util.Map;
import java.util.TreeMap;

public class ProtectedObjectsServiceImpl implements ProtectedObjectsService {
    @Override
    public ProtectedObjects getProtectedObjects() {
        ProtectedObjects protectedObjects = new ProtectedObjects();

        ProtectedObject protectedObject1 = new ProtectedObject("App1");
        ProtectedObject protectedObject2 = new ProtectedObject("App2");

        ProtectedObject child = protectedObject1.addChild("Scripts");

        child.addChild("sc1");
        child.addChild("sc2");
        child.addChild("sc3");

        protectedObject1.addChild("Roles");

        protectedObjects.add(protectedObject1);
        protectedObjects.add(protectedObject2);

        Map<String, ProtectedObject> sortedProtectedObjects = new TreeMap<>(protectedObjects.getObjects());

        return new ProtectedObjects(sortedProtectedObjects);
    }

    @Override
    public void updateProtectedObjects() {
        // In that profile not needed to implement
    }
}
