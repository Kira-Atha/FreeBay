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

import be.huygebaert.freebay.accessDb.getFollowedItems;
import be.huygebaert.freebay.models.Item;
import be.huygebaert.freebay.models.User;

public class ConsultMyList extends AppCompatActivity {
    private User user;
    private Intent intent;

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
                    intent = new Intent(ConsultMyList.this,AddItem.class);
                    intent.putExtra("user",user);
                    ConsultMyList.this.finish();
                    startActivity(intent);
                    break;
                case R.id.btn_back:
                    intent = new Intent(ConsultMyList.this, ConsultItems.class);
                    ConsultMyList.this.finish();
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("user",user);
                    startActivity(intent);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_my_list);
        user = (User) this.getIntent().getSerializableExtra("user");
        new getFollowedItems(this,user).execute();

        Button btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(listener);
        Button btn_additem = findViewById(R.id.btn_add_item);
        btn_additem.setOnClickListener(listener);
        Button btn_exit = findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(listener);
        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(listener);
    }

    public void populate(String msg) {
        System.out.print(msg);
        if(msg.equals("ok")){
            setLayout();
        }else{
            System.out.println(msg);
        }
    }

    public void setLayout(){
        LinearLayout mainLayout = findViewById(R.id.layout_consult_my_list);
        ViewGroup.LayoutParams params = mainLayout.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;


        if(user.getItemsTracked().size() > 0){
            ScrollView scrollView = new ScrollView(this);
            scrollView.setLayoutParams(params);
            scrollView.setFillViewport(true);
            mainLayout.addView(scrollView);

            LinearLayout layout_scroll = new LinearLayout(this);
            layout_scroll.setLayoutParams(params);
            layout_scroll.setOrientation(LinearLayout.VERTICAL);
            scrollView.addView(layout_scroll);
            for(Item item : user.getItemsTracked()){
                Button btn_item = new Button(this);
                btn_item.setLayoutParams(params);
                btn_item.setId(item.getId());
                btn_item.setText(item.getName());
                btn_item.setTextAppearance(this,R.style.btn_text);

                btn_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intent = new Intent(ConsultMyList.this, ConsultDetails.class);
                        intent.putExtra("item",item);
                        intent.putExtra("user",user);
                        ConsultMyList.this.finish();
                        startActivity(intent);
                    }
                });
                layout_scroll.addView(btn_item);
            }
        }else{
            TextView nothing = new TextView(this);
            nothing.setLayoutParams(params);
            nothing.setText(getResources().getString(R.string.noItems));
            mainLayout.addView(nothing);
        }
    }
}