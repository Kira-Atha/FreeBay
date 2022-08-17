package be.huygebaert.freebay.models;

import java.io.Serializable;

public enum TypeItem implements Serializable {
    videoGames("Video games"),
    music("Music"),
    diy("Do it yourself"),
    kitchen("Kitchen"),
    clothing("Clothing"),
    decoration("Decoration"),
    films("Films"),
    furniture("Furniture");

    private String text;

    TypeItem(String text){
        this.text = text;
    }
    public String getText(){
        return this.text;
    }
    public static TypeItem fromString(String text){
        for(TypeItem type : TypeItem.values()){
            if(type.text.equalsIgnoreCase(text)){
                return type;
            }
        }
        return null;
    }
}
