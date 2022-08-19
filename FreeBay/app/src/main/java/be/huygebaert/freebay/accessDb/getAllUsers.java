package be.huygebaert.freebay.accessDb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import be.huygebaert.freebay.MainActivity;
import be.huygebaert.freebay.models.User;

public class getAllUsers extends config{

    private MainActivity main_activity;
    public getAllUsers(MainActivity activity) {
        super();
        this.main_activity = activity;
    }
//10.0.2.2

    @Override
    protected String doInBackground(Void... voids) {
        try {
            URL url = new URL(this.getBasedURL() + this.get_all_users);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.connect();
            // accès rpc php, si ok va retourner un code 200
            int statusCode = connection.getResponseCode();
            if (statusCode == 200) {
                // Ce que retourne la rpc
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
                // tout ce que la rpc renvoie
                Scanner scanner = new Scanner(inputStreamReader);
                scanner.useDelimiter("\n");
                String ret = "";
                while (scanner.hasNext()) {
                    ret += scanner.next();
                }
                //ret va contenir le texte réponse
                return ret;
            } else {
                // Pas status 200
                return "badStatus";
            }
        } catch (MalformedURLException e) {
            return "malformedURL";
        } catch (IOException e) {
            return "connectionFail";
        }
    }

    // va récup ret
    protected void onPostExecute(String chaineJSON) {
        List<User> allUsers = new ArrayList<User>();
        // le texte qui a bien été récupéré ou bien l'erreur
        if (chaineJSON.equals("malformedURL") || chaineJSON.equals("connectionFail") || chaineJSON.equals("badStatus")) {
            this.main_activity.populate_error(chaineJSON);
        }else {
            try {
                // String -> Json
                JSONArray jsonArray = new JSONArray(chaineJSON);
                int i = 0;
                User user;
                while(i <jsonArray.length()){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    user = new User(jsonObject.getInt("IdUser"), jsonObject.getString("Pseudo"), jsonObject.getString("Password"), jsonObject.getString("Longitude"), jsonObject.getString("Latitude"));
                    allUsers.add(user);
                    i++;
                }
               //User.setAllUsers(allUsers);
                this.main_activity.populate(allUsers);
            }catch (JSONException e) {
                //Je suis dans un cas où il y a alors qu'une seule personne ( pas de tableau possible à récupérer )
                try {
                    JSONObject jsonObject = new JSONObject(chaineJSON);
                    User user;
                    user = new User(jsonObject.getInt("IdUser"), jsonObject.getString("Pseudo"), jsonObject.getString("Password"), jsonObject.getString("Longitude"), jsonObject.getString("Latitude"));
                    allUsers.add(user);
                    this.main_activity.populate(allUsers);
                    //e.printStackTrace();
                }catch(JSONException e2){
                    //ou encore pas de personne dans la db
                    //e2.printStackTrace();
                    this.main_activity.populate_error(e2.getMessage());
                }
            }
        }
    }
}
