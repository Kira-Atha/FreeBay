package be.huygebaert.freebay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import be.huygebaert.freebay.accessDb.getAllItems;
import be.huygebaert.freebay.models.Item;
import be.huygebaert.freebay.models.User;

public class ConsultItems extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_items);

        new getAllItems("10.0.2.2","","android",ConsultItems.this).execute();
        User user = (User) this.getIntent().getSerializableExtra("user");
        System.out.println(user.getPseudo());
    }

    public void populate(List<Item> allItems){
        Item.setAllItems(allItems);
    }
    public void populate_error(String error){
        System.out.println(error);
        if (error.equals("nothing")) {
            // Inutile
            Toast toast = Toast.makeText(ConsultItems.this, getResources().getString(R.string.nothingInDb), Toast.LENGTH_LONG);
            toast.show();
        } else {
            // Pour le dev
            System.out.println(error);
        }
    }
}