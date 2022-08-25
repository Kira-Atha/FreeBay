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
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import be.huygebaert.freebay.models.Item;
import be.huygebaert.freebay.models.User;

public class ConsultMySales extends AppCompatActivity {
    private User user, userToCheck;
    private Intent intent;
    private LinearLayout mainLayout;
    private ViewGroup.LayoutParams params;
    private Button btn_logout;
    private LinearLayout layout_scroll;

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_logout:
                    intent = new Intent(ConsultMySales.this, MainActivity.class);
                    ConsultMySales.this.finish();
                    startActivity(intent);
                    break;
                case 10:
                    ConsultMySales.this.finishAffinity();
                    break;
                case R.id.btn_my_list:
                    intent = new Intent(ConsultMySales.this, ConsultMyList.class);
                    intent.putExtra("user", user);
                    ConsultMySales.this.finish();
                    startActivity(intent);
                    break;
                case R.id.btn_add_item:
                    intent = new Intent(ConsultMySales.this, AddItem.class);
                    intent.putExtra("user", user);
                    ConsultMySales.this.finish();
                    startActivity(intent);
                    break;
                case R.id.btn_back:
                    intent = new Intent(ConsultMySales.this, ConsultItems.class);
                    ConsultMySales.this.finish();
                    intent.putExtra("user", user);
                    startActivity(intent);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_my_sales);
        user = (User) getIntent().getSerializableExtra("user");
        if ((User) getIntent().getSerializableExtra("userToCheck") != null) {
            userToCheck = (User) getIntent().getSerializableExtra("userToCheck");
        }
        btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(listener);
        Button btn_additem = findViewById(R.id.btn_add_item);
        btn_additem.setOnClickListener(listener);
        Button btn_mylist = findViewById(R.id.btn_my_list);
        btn_mylist.setOnClickListener(listener);
        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(listener);
        setLayout();
    }

    @Override
    protected void onPause() {
        super.onPause();
        user = (User) getIntent().getSerializableExtra("user");
        if ((User) getIntent().getSerializableExtra("userToCheck") != null) {
            userToCheck = (User) getIntent().getSerializableExtra("userToCheck");
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        user = (User) getIntent().getSerializableExtra("user");
        if ((User) getIntent().getSerializableExtra("userToCheck") != null) {
            userToCheck = (User) getIntent().getSerializableExtra("userToCheck");
        }
    }

    @SuppressLint("ResourceType")
    public void setLayout() {
        mainLayout = findViewById(R.id.layout_consult_my_sales);
        params = btn_logout.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;

        ScrollView scrollView = new ScrollView(this);
        scrollView.setLayoutParams(params);
        scrollView.setFillViewport(true);
        mainLayout.addView(scrollView);

        layout_scroll = new LinearLayout(this);
        layout_scroll.setLayoutParams(params);
        layout_scroll.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(layout_scroll);

        if (userToCheck == null) {
            disp_user_sales(user);
        } else {
            TextView tv_mysSales = findViewById(R.id.mySales);
            String title_sales = getResources().getString(R.string.consultMySales).replace("my", userToCheck.getPseudo());
            tv_mysSales.setText(title_sales);
            disp_user_sales(userToCheck);
        }
    }

    @SuppressLint("ResourceType")
    public void disp_user_sales(User user) {
        if (user.getItemsToSell().size() > 0) {
            for (Item item : user.getItemsToSell()) {
                Button btn_item = new Button(this);
                btn_item.setLayoutParams(params);
                btn_item.setId(item.getId());
                btn_item.setText(item.getName());
                btn_item.setTextAppearance(this, R.style.btn_text);

                btn_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intent = new Intent(ConsultMySales.this, ConsultDetails.class);
                        intent.putExtra("item", item);
                        intent.putExtra("user", ConsultMySales.this.user);

                        ConsultMySales.this.finish();
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
        Button btn_exit = new Button(this);
        btn_exit.setLayoutParams(params);
        btn_exit.setId(10);
        btn_exit.setText(getResources().getString(R.string.exit));
        btn_exit.setTextAppearance(this, R.style.btn_text);
        btn_exit.setOnClickListener(listener);
        btn_exit.setBackgroundColor(getResources().getColor(R.color.purple_200));
        layout_scroll.addView(btn_exit);
    }
}