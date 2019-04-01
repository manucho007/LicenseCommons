package ru.rtksoftlabs.LicenseCommons.shared;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProtectedObjects {
    private Map<String, ProtectedObject> objects;

    public ProtectedObjects() {
        objects = new HashMap<>();
    }

    public ProtectedObjects(Map<String, ProtectedObject> objects) {
        this.objects = objects;
    }

    public Map<String, ProtectedObject> getObjects() {
        return objects;
    }

    public ProtectedObject addChild(String parent, String child) {
        if (objects.containsKey(parent)) {
            return objects.get(parent).addChild(child);
        }

        return objects.put(parent, new ProtectedObject(parent).addChild(child));
    }

    public ProtectedObject addChild(ProtectedObject protectedObject) {
        if (objects.containsKey(protectedObject.getData())) {
            return objects.get(protectedObject.getData()).addChilds(protectedObject);
        }

        objects.put(protectedObject.getData(), protectedObject);

        return protectedObject;
    }

    public ProtectedObject addChild(String parent, ProtectedObject protectedObject) {
        if (objects.containsKey(parent)) {
            return objects.get(parent).addChilds(protectedObject);
        }

        return objects.put(parent, protectedObject);
    }

    public ProtectedObject addChild(String parent) {
        if (objects.containsKey(parent)) {
            return objects.get(parent);
        }

        ProtectedObject protectedObject = new ProtectedObject(parent);

        objects.put(parent, protectedObject);

        return protectedObject;
    }

    public ProtectedObject add(String parent, String child) {
        return addChild(parent, child);
    }

    public ProtectedObject add(ProtectedObject protectedObject) {
        return addChild(protectedObject);
    }

    public ProtectedObject add(String parent, ProtectedObject protectedObject) {
        return addChild(parent, protectedObject);
    }

    public ProtectedObject add(String parent) {
        return addChild(parent);
    }

    public boolean find(ProtectedObject protectedObject) {
        List<String> otherList = protectedObject.returnListOfStringsWithPathToAllLeafs();

        for (Map.Entry<String, ProtectedObject> object: objects.entrySet()) {
            if (object.getValue().returnListOfStringsWithPathToAllLeafs().containsAll(otherList)) {
                return true;
            }
        }

        return false;
    }
}
