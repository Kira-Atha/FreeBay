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
        itemsTracked = new ArrayList<Item>();
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
        List<User> allUsers = User.getAllUsers();
        for(User us : allUsers){
            if(us.getId() == id){
                return us;
            }
        }
        return null;
    }

    public static List<User> getAllUsers(){
        List<User> allUsers = new ArrayList<User>();
        allUsers.add(new User(1,"Kira0","012345","0","0"));
        allUsers.add(new User(2,"Kira1","012345","0","0"));
        allUsers.add(new User(3,"Kira2","012345","0","0"));

        return allUsers;
    }

    public boolean create(User user){
        return false;
    }

    public boolean update(User user) {
        return false;
    }
}
