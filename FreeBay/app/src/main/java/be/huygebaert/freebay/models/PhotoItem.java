package be.huygebaert.freebay.models;

import android.graphics.Bitmap;

public class PhotoItem {
    private int id;
    private Bitmap photo;

    public PhotoItem(Bitmap photo, int id){
        this.photo = photo;
        this.id = id;
    }
}
