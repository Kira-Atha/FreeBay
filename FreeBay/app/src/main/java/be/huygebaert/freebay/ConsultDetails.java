package be.huygebaert.freebay;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import be.huygebaert.freebay.accessDb.createItem;
import be.huygebaert.freebay.accessDb.followItem;
import be.huygebaert.freebay.models.Item;
import be.huygebaert.freebay.models.TypeItem;
import be.huygebaert.freebay.models.User;

public class ConsultDetails extends AppCompatActivity {
    private Item item;
    private User user;
    private ViewGroup.LayoutParams params;
    private Intent intent;
    private Button btn_follow;
    // Nom de la personne qui vend + distance
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_logout:
                    intent = new Intent(ConsultDetails.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    ConsultDetails.this.finish();
                    startActivity(intent);
                    break;
                case R.id.btn_exit:
                    ConsultDetails.this.finishAffinity();
                    break;
                case R.id.btn_my_list:
                    intent = new Intent(ConsultDetails.this, ConsultMyList.class);
                    intent.putExtra("user", user);
                    ConsultDetails.this.finish();
                    startActivity(intent);
                    break;
                case R.id.btn_add_item:
                    intent = new Intent(ConsultDetails.this, AddItem.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                    break;
                //case btn add follow
                case R.id.btn_follow:
                    if (ConsultDetails.this.user.addItemFollow(item)) {
                        new followItem(user, item, ConsultDetails.this).execute();
                    } else {
                        Toast toast = Toast.makeText(ConsultDetails.this, getResources().getString(R.string.alreadyInList), Toast.LENGTH_LONG);
                        toast.show();
                        break;
                    }
                    break;
                case R.id.btn_back:
                    intent = new Intent(ConsultDetails.this, ConsultItems.class);
                    ConsultDetails.this.finish();
                    intent.putExtra("user", user);
                    startActivity(intent);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_details);
        item = (Item) this.getIntent().getSerializableExtra("item");
        user = (User) this.getIntent().getSerializableExtra("user");

        Button btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(listener);
        Button btn_additem = findViewById(R.id.btn_add_item);
        btn_additem.setOnClickListener(listener);
        Button btn_mylist = findViewById(R.id.btn_my_list);
        btn_mylist.setOnClickListener(listener);
        Button btn_exit = findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(listener);
        btn_follow = findViewById(R.id.btn_follow);
        btn_follow.setOnClickListener(listener);
        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(listener);

        params = btn_logout.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;

        setLayout();
    }

    public void setLayout() {
        TextView name = findViewById(R.id.name);
        name.setText(item.getName());

        TextView price = findViewById(R.id.price_item);
        price.setText(String.valueOf(item.getPrice()));

        TextView description = findViewById(R.id.desc_item);
        description.setText(item.getDescription());

        TextView type = findViewById(R.id.type);
        type.setText(item.getType().getName());
        TextView sellerClickable = findViewById(R.id.owner);
        // Pas la peine d'afficher la distance si c'est mon objet que je consulte
        // Je ne peux pas suivre mon propre objet
        if(this.user.equals(this.item.getOwner())){
            sellerClickable.setText(getResources().getString(R.string.you));
            TextView tv_distance = findViewById(R.id.tv_distance);
            tv_distance.setVisibility(View.GONE);
            btn_follow.setVisibility(View.GONE);
        }else{
            LinearLayout mainLayout = findViewById(R.id.layout_consult_details_item);
            sellerClickable.setText(item.getOwner().getPseudo());
            // d'ici, consulter la liste des objets suivis de la personne
            sellerClickable.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    intent = new Intent(ConsultDetails.this,ConsultMyList.class);
                    intent.putExtra("user",user);
                    intent.putExtra("userToCheck",item.getOwner());
                    startActivity(intent);
                }
            });
            TextView distance = findViewById(R.id.distance);

            Location pos_current_user = new Location("");
            pos_current_user.setLatitude(Double.parseDouble(this.user.getLatitude()));
            pos_current_user.setLongitude(Double.parseDouble(this.user.getLongitude()));

            Location pos_seller = new Location("");
            pos_seller.setLatitude(Double.parseDouble(this.item.getOwner().getLatitude()));
            pos_seller.setLongitude(Double.parseDouble(this.item.getOwner().getLongitude()));

            distance.setText(String.valueOf(Math.round((pos_current_user.distanceTo(pos_seller))/1000))+" kms");
        }
    }

    public void populate(String msg) {
        Toast toast;
        switch (msg) {
            case "ok":
                toast = Toast.makeText(ConsultDetails.this, getResources().getString(R.string.okFollow), Toast.LENGTH_LONG);
                toast.show();
                intent = new Intent(ConsultDetails.this, ConsultItems.class);
                intent.putExtra("user", user);
                this.finish();
                startActivity(intent);
                break;
            case "Field empty":
                toast = Toast.makeText(ConsultDetails.this, getResources().getString(R.string.fieldsEmpty), Toast.LENGTH_LONG);
                toast.show();
                break;
            default:
                // Pour le dev
                System.out.println(msg);
                break;
        }
    }
}