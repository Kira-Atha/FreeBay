package be.huygebaert.freebay.accessDb;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import be.huygebaert.freebay.ConsultDetails;
import be.huygebaert.freebay.models.Item;
import be.huygebaert.freebay.models.User;

public class followItem extends config {
    private User user;
    private Item item;
    private ConsultDetails activity;

    public followItem(User user, Item item, ConsultDetails activity){
        this.user = user;
        this.item = item;
        this.activity = activity;
    }

    @Override
    protected String doInBackground(Void... voids){
        try{
            URL url = new URL(this.getBasedURL()+this.add_item_to_list);
            HttpURLConnection connection  = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);

            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"utf-8"));

            String parameters = "idUser="+this.user.getId()+"&idItem="+this.item.getId();

            bufferedWriter.write(parameters);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            connection.connect();
            int statusCode = connection.getResponseCode();
            if(statusCode==200){
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");

                Scanner scanner = new Scanner(inputStreamReader);
                scanner.useDelimiter("\n");
                String ret = "";
                while (scanner.hasNext()) {
                    ret += scanner.next();
                }
                return ret;
            }else{
                return String.valueOf(statusCode);
                //return "badStatus";
            }
        }catch (MalformedURLException e) {
            return "malformedURL";
        } catch (IOException e) {
            return "connectionFail";
        }
    }

    protected void onPostExecute(String msg) {
        this.activity.populate(msg);
    }
}
