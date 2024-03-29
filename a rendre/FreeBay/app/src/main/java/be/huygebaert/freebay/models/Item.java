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
    private User owner;
    //private List<PhotoItem> photoItems;
    //private byte[] photo;
    private static List<Item> allItems = new ArrayList<Item>();

    public Item() {
    }

    /*
    public Item(int id, String name, double price, String description, TypeItem type,User user,byte[] photo) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.type = type;
        this.owner = user;
        this.photo = photo;
        //this.photoItems = new ArrayList<PhotoItem>();
    }
    */
    public Item(int id, String name, double price, String description, TypeItem type, User user) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.type = type;
        this.owner = user;
        //this.photoItems = new ArrayList<PhotoItem>();
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

    public static Item getItem(int id) {
        for (Item it : Item.allItems) {
            if (it.getId() == id) {
                return it;
            }
        }
        return null;
    }

    public User getOwner() {
        return this.owner;
    }

    public void setOwner(User user) {
        this.owner = user;
    }

    public static List<Item> getAllItems() {
        return Item.allItems;
    }

    public static void setAllItems(List<Item> allItems) {
        Item.allItems = allItems;
    }

    /// test pour les photos
    /*
    public byte[] getPhoto() {
        return photo;
    }
    public void setPhoto(byte[] photos){
        this.photo = photos;
    }*/

    /*
    public boolean addPhoto(byte photo){

        if(!this.photos.contains(photo) && this.photoItems.size()<=3){
            this.photoItems.add(photo);
            return true;
        }
        return false;
    }
    public List<PhotoItem> getPhotoItems(){
        return this.photoItems;
    }*/
}
