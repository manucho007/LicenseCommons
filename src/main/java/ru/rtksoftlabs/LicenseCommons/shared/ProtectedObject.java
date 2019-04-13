package ru.rtksoftlabs.LicenseCommons.shared;

import java.util.*;

public class ProtectedObject {
    private List<String> listOfStringsWithPathToAllLeafs;
    private List<String> listOfStringsWithPathToAllLeafsWithNames;

    private String data;

    private String name;

    private LinkedHashSet<ProtectedObject> children;

    public ProtectedObject() {
        // needed for json mapper
    }

    public ProtectedObject(String data) {
        this(data, null);
    }

    public ProtectedObject(String data, String name) {
        if (data == null) {
            throw new RuntimeException("null value not allowed");
        }

        if (data.equals("")) {
            throw new RuntimeException("empty String value not allowed");
        }

        this.data = data;
        this.name = name;
        this.children = new LinkedHashSet<>();
    }

    public String getData() {
        return data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ProtectedObject> getChildren() {
        return children;
    }

    public ProtectedObject addChild(String child) {
        return addChild(child, null);
    }

    public ProtectedObject addChild(String child, String name) {
        ProtectedObject childNode = new ProtectedObject(child, name);

        if (!this.children.add(childNode)) {
            ProtectedObject protectedObject = this.children.stream().filter(p -> p.getData().equals(child)).findFirst().get();
            if (protectedObject.getName() == null) {
                protectedObject.setName(name);
            }
            else if ((name != null) && (!protectedObject.getName().equals(name))) {
                protectedObject.setName(name);
            }

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
            cumulativeProtectedObject = addChild(childProtectedObjects.getData(), childProtectedObjects.getName());

            cumulativeProtectedObject.addChilds(childProtectedObjects);
        }

        return cumulativeProtectedObject;
    }

    public List<String> generateListOfAllPathsToLeafsWithNames(ProtectedObject node, String accumulator) {
        if (node.children != null) {
            for (ProtectedObject child : node.children) {
                String elem = accumulator;
                elem += "/" + child.data;

                if (child.getName() != null) {
                    elem += child.getName();
                }

                if ((child.children != null) && (!child.children.isEmpty())) {
                    generateListOfAllPathsToLeafsWithNames(child, elem);
                } else {
                    listOfStringsWithPathToAllLeafsWithNames.add(elem);
                }
            }
        }

        return listOfStringsWithPathToAllLeafsWithNames;
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

    public List<String> returnListOfStringsWithPathToAllLeafsWithNames() {
        if (listOfStringsWithPathToAllLeafsWithNames == null) {
            listOfStringsWithPathToAllLeafsWithNames = new ArrayList<>();

            generateListOfAllPathsToLeafsWithNames(this, data);
        }

        return listOfStringsWithPathToAllLeafsWithNames;
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

