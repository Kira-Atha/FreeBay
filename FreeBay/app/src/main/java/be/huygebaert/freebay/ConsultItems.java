package be.huygebaert.freebay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import be.huygebaert.freebay.models.User;

public class ConsultItems extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_items);

        User user = (User) this.getIntent().getSerializableExtra("user");
        System.out.println(user.getPseudo());

    }
}