package be.huygebaert.freebay.DAO;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import be.huygebaert.freebay.models.User;

public class UserDAO extends DAO{
    public UserDAO(String ipServer, String port, String repertory) {
        super(ipServer, port, repertory);
    }
    private String rpc = "get_users.php";
    @Override
    protected String doInBackground(Void... voids) {
        try {
            URL url = new URL(this.getBasedURL()+rpc);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.connect();

            if(connection.getResponseCode() == 200){
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8");
                Scanner scanner = new Scanner(inputStreamReader);
                scanner.useDelimiter("\n");
                String ret = "";
                while(scanner.hasNext()){
                    ret += scanner.next();
                }
                return ret;
            }else{
                return String.valueOf(connection.getResponseCode());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "malformedURL";
        } catch (IOException e) {
            e.printStackTrace();
            return "connectionImpossible";
        }

    }


    public User find(int id){
        return new User();
    }
    public List<User> findAll(){
        return new ArrayList<User>();
    }

    public boolean update(User user){
        return false;
    }
    public boolean create(User user){
        return false;
    }


    public void onPostExecute(String txt){

    }
}
