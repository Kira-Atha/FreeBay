package be.huygebaert.freebay.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class User implements Serializable {
    private int id;
    private String pseudo;
    private String password;
    private String longitude;
    private String latitude;
    private List<Item> itemsTracked;
    private List<Item> itemsToSell;
    private static List<User> allUsers = new ArrayList<User>();

    public User(){}

    // Test connection
    public User(String pseudo, String password){
        this.id = 0;
        this.pseudo = pseudo;
        this.password= password;
    }
    public User(int id, String pseudo, String password,String longitude,String latitude){
        this.id = id;
        this.pseudo = pseudo;
        this.password = password;
        this.longitude = longitude;
        this.latitude = latitude;
        this.itemsTracked = new ArrayList<Item>();
        this.itemsToSell = new ArrayList<Item>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public List<Item> getItemsTracked() {
        return itemsTracked;
    }

    public void setItemsTracked(List<Item> itemsTracked) {
        this.itemsTracked = itemsTracked;
    }

    public List<Item> getItemsToSell() {
        return itemsToSell;
    }

    public void setItemsToSell(List<Item> itemsToSell) {
        this.itemsToSell = itemsToSell;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return pseudo.equals(user.pseudo) && password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return this.pseudo.hashCode()+this.password.hashCode();
    }

    public static User getUser(int id){
        for(User us : User.allUsers){
            if(us.getId() == id){
                return us;
            }
        }
        return null;
    }

    public static void setAllUsers(List<User> allUsers){
        User.allUsers = allUsers;
    }

    public static List<User> getAllUsers(){
        return User.allUsers;
    }
    public User signIn(){
        User userToReturn = null;
        for(User user_ : User.getAllUsers()){
            if(this.equals(user_)) {
                userToReturn= user_;
            }
        }
        return userToReturn;
    }

    public boolean signUp(){
        for(User user_ : User.getAllUsers()){
            if(this.getPseudo().equals(user_.getPseudo())){
                return false;
            }
        }
        return true;
    }
    public boolean createItem(Item item){
        if(item != null) {
            this.itemsToSell.add(item);
            // Pas vraiment obligatoire : je refresh avec la tâche asynchrone
            Item.getAllItems().add(item);
            return true;
        }
        return false;
    }
}
