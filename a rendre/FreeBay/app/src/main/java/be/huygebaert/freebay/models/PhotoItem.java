package be.huygebaert.freebay.models;

import android.graphics.Bitmap;

import java.io.Serializable;

public class PhotoItem implements Serializable {
    private int id;
    private Bitmap photo;
    //private byte[] pho_to;

    public PhotoItem() {
    }

    public PhotoItem(Bitmap photo, int id) {
        this.photo = photo;
        this.id = id;
    }

    /*
    public PhotoItem(byte[] photo,int id){
        this.pho_to = photo;
        this.id = id;
    }
     */
    public Bitmap getPhoto() {
        return photo;
    }

    public int getId() {
        return this.id;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public void setId(int id) {
        this.id = id;
    }
}
