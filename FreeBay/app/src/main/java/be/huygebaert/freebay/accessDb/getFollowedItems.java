package be.huygebaert.freebay.accessDb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import be.huygebaert.freebay.ConsultMyList;
import be.huygebaert.freebay.MainActivity;
import be.huygebaert.freebay.models.Item;
import be.huygebaert.freebay.models.User;

public class getFollowedItems extends config{
    private ConsultMyList activity;
    private User user;

    public getFollowedItems(ConsultMyList activity, User user){
        this.activity = activity;
        this.user = user;
    }
    @Override
    protected String doInBackground(Void... voids) {
        try {
            URL url = new URL(this.getBasedURL() + this.get_followed_items);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"utf-8"));
            String parameters = "idUser="+this.user.getId();
            bufferedWriter.write(parameters);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            connection.connect();
            int statusCode = connection.getResponseCode();
            if (statusCode == 200) {
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
                Scanner scanner = new Scanner(inputStreamReader);
                scanner.useDelimiter("\n");
                String ret = "";
                while (scanner.hasNext()) {
                    ret += scanner.next();
                }
                return ret;
            } else {
                return String.valueOf(statusCode);
                //return "badStatus";
            }
        } catch (MalformedURLException e) {
            return "malformedURL";
        } catch (IOException e) {
            return "connectionFail";
        }
    }

    // va récup ret
    protected void onPostExecute(String chaineJSON) {
        System.out.println("JE RECUPERE =>"+chaineJSON);
        Item item;
        if (chaineJSON.equals("malformedURL") || chaineJSON.equals("connectionFail") || chaineJSON.equals("badStatus")) {
            this.activity.populate(chaineJSON);
        }else {
            try {
                JSONArray jsonArray = new JSONArray(chaineJSON);
                int i = 0;
                while(i <jsonArray.length()){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    item = Item.getItem(jsonObject.getInt("IdItem"));
                    this.user.addItemFollow(item);
                    i++;
                }
                this.activity.populate("ok");
            }catch (JSONException e) {
                try {
                    JSONObject jsonObject = new JSONObject(chaineJSON);
                    item = Item.getItem(jsonObject.getInt("IdItem"));
                    this.user.addItemFollow(item);
                    System.out.println("UN SEUL OBJET SUIVI");
                    this.activity.populate("ok");
                }catch(JSONException e2){
                    this.activity.populate(e2.getMessage());
                }
            }
        }
    }
}
