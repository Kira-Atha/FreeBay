package be.huygebaert.freebay.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public enum TypeItem implements Serializable {
    INSTANCE,
    videoGames("Video games"),
    music("Music"),
    diy("Do it yourself"),
    kitchen("Kitchen"),
    clothing("Clothing"),
    decoration("Decoration"),
    films("Films"),
    furniture("Furniture");



    private String text;
    //private static List <TypeItem> allTypes= new ArrayList<TypeItem>();

    TypeItem(String text){
        this.text = text;
    }

    TypeItem() {}

    public String getText(){
        return this.text;
    }
    public void setText(String txt){
        this.text = txt;
    }
    public static TypeItem fromString(String text){
        for(TypeItem type : TypeItem.values()){
            if(type.text.equalsIgnoreCase(text)){
                return type;
            }
        }
        return null;
    }
    @Override
    public String toString(){
        return this.text;
    }
    // Dans le cas où ce que le wizard rend ne suffit pas, ajout momentané dans la liste afin de pouvoir créer un nouvel objet avec un
    // nouveau type
    /*
    public static boolean addType(TypeItem type){
        if(!allTypes.contains(type)){
            allTypes.add(type);
            return true;
        }
        return false;
    }
*/
}
