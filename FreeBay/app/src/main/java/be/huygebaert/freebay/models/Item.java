package be.huygebaert.freebay.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Item implements Serializable {
    private int id;
    private String name;
    private double price;
    private String description;
    private TypeItem type;

    public Item(){}
    public Item(int id, String name, double price, String description, TypeItem type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TypeItem getType() {
        return type;
    }

    public void setType(TypeItem type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id;
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    public static Item getItem(int id){
        return new Item();
    }

    public static List<Item> getAllItems(){
        List<Item> allItems = new ArrayList<Item>();
        allItems.add(new Item(0,"pan",25.2,"Wonderful pan",TypeItem.kitchen));
        allItems.add(new Item(1,"dvd",32.2,"Wonderful collection dvds",TypeItem.films));
        allItems.add(new Item(2,"cd",41.2,"Wonderful music cd",TypeItem.music));
        return allItems;
    }
}
