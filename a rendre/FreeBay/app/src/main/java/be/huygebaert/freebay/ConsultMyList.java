package be.huygebaert.freebay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import be.huygebaert.freebay.accessDb.getFollowedItems;
import be.huygebaert.freebay.models.Item;
import be.huygebaert.freebay.models.User;

public class ConsultMyList extends AppCompatActivity {
    private User user;
    private User userToCheck;
    private Intent intent;
    private ViewGroup.LayoutParams params;
    private LinearLayout mainLayout;
    private ScrollView scrollView;

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_logout:
                    intent = new Intent(ConsultMyList.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    ConsultMyList.this.finish();
                    startActivity(intent);
                    break;
                case R.id.btn_exit:
                    ConsultMyList.this.finishAffinity();
                    break;
                case R.id.btn_add_item:
                    intent = new Intent(ConsultMyList.this, AddItem.class);
                    intent.putExtra("user", user);
                    ConsultMyList.this.finish();
                    startActivity(intent);
                    break;
                case R.id.btn_back:
                    intent = new Intent(ConsultMyList.this, ConsultItems.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("user", user);
                    if (userToCheck != null) {
                        intent.putExtra("userToCheck", userToCheck);
                    }
                    startActivity(intent);
                    ConsultMyList.this.finish();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_my_list);
        user = (User) this.getIntent().getSerializableExtra("user");
        if ((User) getIntent().getSerializableExtra("userToCheck") != null) {
            userToCheck = (User) getIntent().getSerializableExtra("userToCheck");
            new getFollowedItems(this, userToCheck).execute();
        } else {
            new getFollowedItems(this, user).execute();
        }

        Button btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(listener);
        Button btn_additem = findViewById(R.id.btn_add_item);
        btn_additem.setOnClickListener(listener);
        Button btn_exit = findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(listener);
        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(listener);
    }

    // est-ce que la personne dont je consultais la liste a suivi un autre objet entre temps???
    public void onPause() {
        super.onPause();
        if ((User) getIntent().getSerializableExtra("userToCheck") != null) {
            userToCheck = (User) getIntent().getSerializableExtra("userToCheck");
            new getFollowedItems(this, userToCheck).execute();
        } else {
            new getFollowedItems(this, user).execute();
        }
    }

    public void onRestart() {
        super.onRestart();
        if ((User) getIntent().getSerializableExtra("userToCheck") != null) {
            userToCheck = (User) getIntent().getSerializableExtra("userToCheck");
            new getFollowedItems(this, userToCheck).execute();
        } else {
            new getFollowedItems(this, user).execute();
        }
    }

    public void populate(String msg) {
        System.out.print(msg);
        if (msg.equals("ok")) {
            setLayout();
        } else {
            System.out.println(msg);
        }
    }

    public void setLayout() {
        if(scrollView !=null){
            clear_item(scrollView);
        }
        mainLayout = findViewById(R.id.layout_consult_my_list);
        params = mainLayout.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;

        if (userToCheck != null) {
            disp_items_tracked(userToCheck);
            TextView tv_list = findViewById(R.id.myList);
            String txt_list = getResources().getString(R.string.consultMyList).replace("my", userToCheck.getPseudo());
            tv_list.setText(txt_list);
        } else {
            disp_items_tracked(user);
        }
    }

    public void disp_items_tracked(User user) {
        if (user.getItemsTracked().size() > 0) {
            scrollView = new ScrollView(this);
            scrollView.setLayoutParams(params);
            scrollView.setFillViewport(true);
            mainLayout.addView(scrollView);

            LinearLayout layout_scroll = new LinearLayout(this);
            layout_scroll.setLayoutParams(params);
            layout_scroll.setOrientation(LinearLayout.VERTICAL);
            scrollView.addView(layout_scroll);
            for (Item item : user.getItemsTracked()) {
                Button btn_item = new Button(this);
                btn_item.setLayoutParams(params);
                btn_item.setId(item.getId());
                btn_item.setText(item.getName());
                btn_item.setTextAppearance(this, R.style.btn_text);

                btn_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intent = new Intent(ConsultMyList.this, ConsultDetails.class);
                        intent.putExtra("item", item);
                        intent.putExtra("user", ConsultMyList.this.user);
                        ConsultMyList.this.finish();
                        startActivity(intent);
                    }
                });
                layout_scroll.addView(btn_item);
            }
        } else {
            TextView nothing = new TextView(this);
            nothing.setLayoutParams(params);
            nothing.setText(getResources().getString(R.string.noItems));
            mainLayout.addView(nothing);
        }
    }
    public void clear_item(ViewGroup container) {
        container.removeAllViews();
    }
}