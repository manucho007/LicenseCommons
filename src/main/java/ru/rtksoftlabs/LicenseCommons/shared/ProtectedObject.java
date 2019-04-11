package ru.rtksoftlabs.LicenseCommons.shared;

import java.util.*;

public class ProtectedObject {
    private List<String> listOfStringsWithPathToAllLeafs;

    private String data;

    private LinkedHashSet<ProtectedObject> children;

    public ProtectedObject() {
        // needed for json mapper
    }

    public ProtectedObject(String data) {
        if (data == null) {
            throw new RuntimeException("null value not allowed");
        }

        if (data.equals("")) {
            throw new RuntimeException("empty String value not allowed");
        }

        this.data = data;
        this.children = new LinkedHashSet<>();
    }

    public String getData() {
        return data;
    }

    public Set<ProtectedObject> getChildren() {
        return children;
    }

    public ProtectedObject addChild(String child) {
        ProtectedObject childNode = new ProtectedObject(child);

        if (!this.children.add(childNode)) {
            ProtectedObject protectedObject = this.children.stream().filter(p -> p.getData().equals(child)).findFirst().get();

            return protectedObject;
        }

        return childNode;
    }

    public ProtectedObject addChild(ProtectedObject protectedObject) {
        this.children.add(protectedObject);

        return protectedObject;
    }

    public ProtectedObject addChilds(ProtectedObject protectedObject) {
        ProtectedObject cumulativeProtectedObject = protectedObject;

        for (ProtectedObject childProtectedObjects: protectedObject.getChildren()) {
            cumulativeProtectedObject = addChild(childProtectedObjects.getData());

            cumulativeProtectedObject.addChilds(childProtectedObjects);
        }

        return cumulativeProtectedObject;
    }

    public List<String> generateListOfAllPathsToLeafs(ProtectedObject node, String accumulator) {
        if (node.children != null) {
            for (ProtectedObject child : node.children) {
                String elem = accumulator;
                elem += "/" + child.data;

                if ((child.children != null) && (!child.children.isEmpty())) {
                    generateListOfAllPathsToLeafs(child, elem);
                } else {
                    listOfStringsWithPathToAllLeafs.add(elem);
                }
            }
        }

        return listOfStringsWithPathToAllLeafs;
    }

    public List<String> returnListOfStringsWithPathToAllLeafs() {
        if (listOfStringsWithPathToAllLeafs == null) {
            listOfStringsWithPathToAllLeafs = new ArrayList<>();

            generateListOfAllPathsToLeafs(this, data);
        }

        return listOfStringsWithPathToAllLeafs;
    }

    public boolean find(ProtectedObject protectedObject) {
        List<String> otherList = protectedObject.returnListOfStringsWithPathToAllLeafs();

        return returnListOfStringsWithPathToAllLeafs().containsAll(otherList);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProtectedObject that = (ProtectedObject) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        int hash = Objects.hash(data);

        return hash;
    }
}

