package ru.rtksoftlabs.LicenseCommons.shared;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ProtectedObjects {
    private Map<String, ProtectedObject> objects;

    public ProtectedObjects() {
        objects = new HashMap<>();
    }

    public ProtectedObjects(Map<String, ProtectedObject> objects) {
        if (objects == null) {
            throw new RuntimeException("null value not allowed");
        }

        this.objects = objects;
    }

    public Map<String, ProtectedObject> getObjects() {
        return objects;
    }

    private ProtectedObject putObject(String parent, ProtectedObject protectedObject) {
        if (parent == null || protectedObject == null || protectedObject.getData() == null) {
            throw new RuntimeException("null value not allowed");
        }

        if (parent.equals("") || protectedObject.getData().equals("")) {
            throw new RuntimeException("empty String value not allowed");
        }

        if (!parent.equals(protectedObject.getData())) {
            throw new RuntimeException("parent and protectedObject.data should be equal");
        }

        return objects.put(parent, protectedObject);
    }

    public ProtectedObject addChild(String parent, String childData) {
        return addChild(parent, childData, null);
    }

    public ProtectedObject addChild(String parent, String childData, String childName) {
        if (objects.containsKey(parent)) {
            return objects.get(parent).addChild(childData, childName);
        }

        return putObject(parent, new ProtectedObject(childData, childName));
    }

    public ProtectedObject addChild(ProtectedObject protectedObject) {
        if (objects.containsKey(protectedObject.getData())) {

            ProtectedObject existProtectedObject = objects.get(protectedObject.getData());

            if (existProtectedObject.getName() == null) {
                existProtectedObject.setName(protectedObject.getName());
            }
            else if ((protectedObject.getName() != null) && (!existProtectedObject.getName().equals(protectedObject.getName()))) {
                existProtectedObject.setName(protectedObject.getName());
            }

            return objects.get(protectedObject.getData()).addChilds(protectedObject);
        }

        putObject(protectedObject.getData(), protectedObject);

        return protectedObject;
    }

    public ProtectedObject addChild(String parent, ProtectedObject protectedObject) {
        if (objects.containsKey(parent)) {
            return objects.get(parent).addChilds(protectedObject);
        }

        return putObject(parent, protectedObject);
    }

    public ProtectedObject addChild(String parent) {
        if (objects.containsKey(parent)) {
            return objects.get(parent);
        }

        ProtectedObject protectedObject = new ProtectedObject(parent);

        putObject(parent, protectedObject);

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
        ProtectedObject findedProtectedObject = objects.get(protectedObject.getData());

        if (findedProtectedObject != null) {
            List<String> otherList = protectedObject.returnListOfStringsWithPathToAllLeafs();

            if (findedProtectedObject.returnListOfStringsWithPathToAllLeafs().containsAll(otherList)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProtectedObjects that = (ProtectedObjects) o;

        if(objects.size() == that.getObjects().size() && objects.equals(that.getObjects())) {
            for (String key: objects.keySet()) {
                if (!objects.get(key).returnListOfStringsWithPathToAllLeafsWithNames().containsAll(that.getObjects().get(key).returnListOfStringsWithPathToAllLeafsWithNames())) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(objects);
    }
}
