package be.huygebaert.freebay.accessDb;

import android.graphics.Bitmap;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import be.huygebaert.freebay.AddItem;
import be.huygebaert.freebay.models.PhotoItem;

public class addPhotoItem extends config {
    //private final List<PhotoItem> photosItem;
    private final AddItem activity;
    private final byte[] photo;

    /*
    public addPhotoItem(List<PhotoItem> photosItem, AddItem activity) {
        this.photosItem = photosItem;
        this.activity = activity;
    }
    */
    public addPhotoItem(byte[] photo, AddItem activity) {
        this.photo = photo;
        this.activity = activity;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            URL url = new URL(this.getBasedURL() + this.add_photo_item);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);

            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));

            /*

            Bitmap[] tabPictures = new Bitmap[photosItem.size()];


            for (int i = 0; i < tabPictures.length; i++) {
                photosItem.get(i).getPhoto().compress(Bitmap.CompressFormat.PNG, 90, outputStream);
                tabPictures[i] = photosItem.get(i).getPhoto();
            }
            */
            String parameters = "pictures=" + photo;

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

    /* ne rien faire après l'ajout des images en db
    protected void onPostExecute(String msg) {
        this.activity.populate(msg);
    }*/
}
