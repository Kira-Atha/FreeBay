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

import be.huygebaert.freebay.ConsultItems;

import be.huygebaert.freebay.models.Item;
import be.huygebaert.freebay.models.TypeItem;
import be.huygebaert.freebay.models.User;

public class getAllItems extends config {
    private ConsultItems consult_activity;

    public getAllItems(ConsultItems activity) {
        super();
        this.consult_activity = activity;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            URL url = new URL(this.getBasedURL() + this.get_all_items);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
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
        List<Item> allItems = new ArrayList<Item>();
        TypeItem.setAllTypes();

        if (chaineJSON.equals("malformedURL") || chaineJSON.equals("connectionFail") || chaineJSON.equals("badStatus")) {
            this.consult_activity.populate_error(chaineJSON);
        } else {
            try {
                JSONArray jsonArray = new JSONArray(chaineJSON);
                int i = 0;
                Item item;
                while (i < jsonArray.length()) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    TypeItem type = new TypeItem(jsonObject.getString("Type"));
                    TypeItem.addType(type);
                    User owner = User.getUser(jsonObject.getInt("IdUser"));
                    item = new Item(jsonObject.getInt("IdItem"), jsonObject.getString("Name"), jsonObject.getDouble("Price"), jsonObject.getString("Description"), type, owner);
                    allItems.add(item);
                    i++;
                }
                this.consult_activity.populate(allItems);
            } catch (JSONException e) {
                try {
                    Item item;
                    JSONObject jsonObject = new JSONObject(chaineJSON);
                    TypeItem type = new TypeItem(jsonObject.getString("Type"));
                    TypeItem.addType(type);
                    User owner = User.getUser(jsonObject.getInt("IdUser"));
                    item = new Item(jsonObject.getInt("IdItem"), jsonObject.getString("Name"), jsonObject.getDouble("Price"), jsonObject.getString("Description"), type, owner);
                    allItems.add(item);
                    this.consult_activity.populate(allItems);
                    //e.printStackTrace();
                } catch (JSONException e2) {
                    this.consult_activity.populate_error(e2.getMessage());
                }
            }
        }
    }
}
