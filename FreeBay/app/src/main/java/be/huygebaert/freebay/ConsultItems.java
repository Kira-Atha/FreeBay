package be.huygebaert.freebay;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import be.huygebaert.freebay.accessDb.getAllItems;
import be.huygebaert.freebay.models.Item;
import be.huygebaert.freebay.models.User;

public class ConsultItems extends AppCompatActivity {
    private Intent intent;
    private User user;
    private getAllItems getAllItems;

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_logout:
                    intent = new Intent(ConsultItems.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    ConsultItems.this.finish();
                    startActivity(intent);
                    break;
                case 10:
                    ConsultItems.this.finishAffinity();
                    break;
                case R.id.btn_my_list:
                    intent = new Intent(ConsultItems.this,ConsultMyList.class);
                    startActivity(intent);
                    break;
                case R.id.btn_add_item:
                    intent = new Intent(ConsultItems.this,AddItem.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_items);
        try {
            new getAllItems(ConsultItems.this,user).execute().get(3000, TimeUnit.MILLISECONDS);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        user = (User) this.getIntent().getSerializableExtra("user");
    }

    @Override
    protected void onPause() {
        super.onPause();
        new getAllItems(ConsultItems.this,user).execute();
    }
    @SuppressLint("ResourceType")
    public void setLayout(){
        LinearLayout layout = findViewById(R.id.layout_consult_item);
        TextView tv_hello = new TextView(this);
        layout.addView(tv_hello);
        ViewGroup.LayoutParams params = tv_hello.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        tv_hello.setLayoutParams(params);
        tv_hello.setTextAppearance(this, R.style.title);
        String personal_hello = getResources().getString(R.string.welcome)+" "+this.user.getPseudo();
        tv_hello.setText(personal_hello);

        Button btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(listener);
        Button btn_additem = findViewById(R.id.btn_add_item);
        btn_additem.setOnClickListener(listener);
        Button btn_mylist = findViewById(R.id.btn_my_list);
        btn_mylist.setOnClickListener(listener);

        ScrollView scrollView = new ScrollView(this);
        scrollView.setLayoutParams(params);
        scrollView.setFillViewport(true);
        layout.addView(scrollView);

        LinearLayout layout_scroll = new LinearLayout(this);
        layout_scroll.setLayoutParams(params);
        layout_scroll.setOrientation(LinearLayout.HORIZONTAL);
        scrollView.addView(layout_scroll);

        //LinearLayout layout_scroll = findViewById(R.id.layout_in_scroll);

        if(Item.getAllItems().size() > 0){
            for(Item item:Item.getAllItems()){
                Button btn_item = new Button(this);
                btn_item.setLayoutParams(params);
                btn_item.setId(item.getId());
                btn_item.setText(item.getName());
                btn_item.setTextAppearance(this,R.style.btn_text);
                layout_scroll.addView(btn_item);
            }
        }else{
            TextView nothing = new TextView(this);
            nothing.setLayoutParams(params);
            nothing.setText(getResources().getString(R.string.nothingInDb));
            layout_scroll.addView(nothing);
        }

        Button btn_exit = new Button(this);
        btn_exit.setLayoutParams(params);
        btn_exit.setId(10);
        btn_exit.setText(getResources().getString(R.string.exit));
        btn_exit.setTextAppearance(this,R.style.btn_text);
        btn_exit.setOnClickListener(listener);
        layout.addView(btn_exit);
    }

    public void populate(List<Item> allItems){
        Item.setAllItems(allItems);
        setLayout();
    }
    public void populate_error(String error){
        System.out.println(error);
    }
}