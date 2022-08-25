package be.huygebaert.freebay.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// L'idée de cette classe est de ne pas stocker les nouveaux types en base de données
public class TypeItem implements Serializable {
    private String name;
    private static List<TypeItem> allTypes = new ArrayList<TypeItem>();

    public TypeItem(String name) {
        this.name = name;
    }

    public static void setAllTypes() {
        addType(new TypeItem("Kitchen"));
        addType(new TypeItem("Bathroom"));
        addType(new TypeItem("VideoGame"));
        addType(new TypeItem("Clothing"));
        addType(new TypeItem("Film"));
        addType(new TypeItem("Furniture"));
        addType(new TypeItem("Decoration"));
        addType(new TypeItem("DoItYourself"));
    }

    public static List<TypeItem> getAllTypes() {
        return allTypes;
    }

    public static TypeItem getTypeItem(String name) {
        for (TypeItem type : allTypes) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        return null;
    }

    public static boolean addType(TypeItem type) {
        if (!allTypes.contains(type)) {
            allTypes.add(type);
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeItem typeItem = (TypeItem) o;
        return name.equals(typeItem.name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return this.getName().hashCode();
    }

    @Override
    public String toString() {
        return this.getName();
    }
}