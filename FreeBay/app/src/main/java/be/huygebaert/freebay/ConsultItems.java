package be.huygebaert.freebay;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import be.huygebaert.freebay.accessDb.getAllItems;
import be.huygebaert.freebay.accessDb.getFollowedItems;
import be.huygebaert.freebay.models.Item;
import be.huygebaert.freebay.models.TypeItem;
import be.huygebaert.freebay.models.User;

public class ConsultItems extends AppCompatActivity {
    private Intent intent;
    private User user;
    private String maxDistance = "0";
    private LinearLayout mainLayout;
    private ViewGroup.LayoutParams params;
    private ScrollView scrollView;
    private LinearLayout helloLayout;
    private LinearLayout row;
    private LinearLayout layout_sort;

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
                    intent.putExtra("user",user);
                    ConsultItems.this.finish();
                    startActivity(intent);
                    break;
                case R.id.btn_add_item:
                    intent = new Intent(ConsultItems.this,AddItem.class);
                    intent.putExtra("user",user);
                    ConsultItems.this.finish();
                    startActivity(intent);
                    break;
                case R.id.btn_see_my_sales:
                    intent = new Intent(ConsultItems.this,ConsultMySales.class);
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
        Button btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(listener);
        Button btn_additem = findViewById(R.id.btn_add_item);
        btn_additem.setOnClickListener(listener);
        Button btn_mylist = findViewById(R.id.btn_my_list);
        btn_mylist.setOnClickListener(listener);
        Button btn_see_my_sales = findViewById(R.id.btn_see_my_sales);
        btn_see_my_sales.setOnClickListener(listener);

        user = (User) this.getIntent().getSerializableExtra("user");
        new getAllItems(ConsultItems.this,user).execute();
        new getFollowedItems(ConsultItems.this,user).execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
        new getAllItems(ConsultItems.this,user).execute();
        new getFollowedItems(ConsultItems.this,user).execute();
        user = (User) this.getIntent().getSerializableExtra("user");
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        new getAllItems(ConsultItems.this,user).execute();
        new getFollowedItems(ConsultItems.this,user).execute();
        user = (User) this.getIntent().getSerializableExtra("user");
    }

    public void setLayout(){
        mainLayout = findViewById(R.id.layout_consult_item);

        helloLayout = new LinearLayout(this);
        mainLayout.addView(helloLayout);
        params = helloLayout.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        helloLayout.setLayoutParams(params);
        helloLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView tv_hello = new TextView(this);
        helloLayout.addView(tv_hello);
        tv_hello.setLayoutParams(params);
        tv_hello.setTextAppearance(this, R.style.btn_text);
        String personal_hello = getResources().getString(R.string.welcome)+" "+this.user.getPseudo();
        tv_hello.setText(personal_hello);

        TextView spin_title = new TextView(this);
        spin_title.setText(getResources().getString(R.string.maxDistance));
        helloLayout.addView(spin_title);
        Spinner spin_filter = new Spinner(this);
        List<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("5");
        spinnerArray.add("10");
        spinnerArray.add("20");
        spinnerArray.add("30");
        spinnerArray.add("50");
        spinnerArray.add("100");
        spinnerArray.add(getResources().getString(R.string.viewAll));

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,spinnerArray);
        spin_filter.setAdapter(spinnerArrayAdapter);
        spin_filter.setLayoutParams(params);
        helloLayout.addView(spin_filter);
        spin_filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                maxDistance = parent.getItemAtPosition(pos).toString();
                if(scrollView != null){
                    clear_item(scrollView);
                }
                set_scroll();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        layout_sort = new LinearLayout(this);
        layout_sort.setLayoutParams(params);
        mainLayout.addView(layout_sort);
    }

    @SuppressLint("ResourceType")
    public void set_scroll(){
        if(Item.getAllItems().size() > 0){
            scrollView = new ScrollView(this);
            scrollView.setLayoutParams(params);
            scrollView.setFillViewport(true);
            mainLayout.addView(scrollView);

            LinearLayout layout_scroll = new LinearLayout(this);
            layout_scroll.setLayoutParams(params);
            layout_scroll.setOrientation(LinearLayout.VERTICAL);
            scrollView.addView(layout_scroll);
            for(Item item:Item.getAllItems()) {
                Location pos_current_user = new Location("");
                pos_current_user.setLatitude(Double.parseDouble(this.user.getLatitude()));
                pos_current_user.setLongitude(Double.parseDouble(this.user.getLongitude()));

                Location pos_seller = new Location("");
                pos_seller.setLatitude(Double.parseDouble(item.getOwner().getLatitude()));
                pos_seller.setLongitude(Double.parseDouble(item.getOwner().getLongitude()));
                int distanceBetween = Math.round((pos_current_user.distanceTo(pos_seller)) / 1000);

                row = new LinearLayout(this);
                row.setLayoutParams(params);
                layout_scroll.addView(row);
                try{
                    if (distanceBetween < Integer.parseInt(maxDistance)) {
                        item_info(item);
                    }
                }catch(NumberFormatException nfe){
                    // si c'est le choix est "view all", il ira ici, j'ignore la condition
                    item_info(item);
                }
            }
            Button btn_exit = new Button(this);
            btn_exit.setLayoutParams(params);
            btn_exit.setId(10);
            btn_exit.setText(getResources().getString(R.string.exit));
            btn_exit.setTextAppearance(this,R.style.btn_text);
            btn_exit.setOnClickListener(listener);
            btn_exit.setBackgroundColor(getResources().getColor(R.color.purple_200));
            layout_scroll.addView(btn_exit);
        }else{
            TextView nothing = new TextView(this);
            nothing.setLayoutParams(params);
            nothing.setText(getResources().getString(R.string.noItems));
            mainLayout.addView(nothing);
        }
    }

    @SuppressLint("ResourceAsColor")
    public void item_info(Item item){
        Button btn_item = new Button(this);
        btn_item.setLayoutParams(params);
        btn_item.setId(item.getId());
        btn_item.setText(item.getName());
        btn_item.setTextAppearance(this, R.style.btn_text);
        //changer la couleur de mes objets dans la liste
        if(item.getOwner().equals(user)){
            btn_item.setBackgroundColor(R.color.olivedrab);
        }
        btn_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(ConsultItems.this, ConsultDetails.class);
                intent.putExtra("item", item);
                intent.putExtra("user", user);
                ConsultItems.this.finish();
                startActivity(intent);
            }
        });
        row.addView(btn_item);
// Pas la peine de consulter mes ventes par là, j'ai un bouton pour
        if(!item.getOwner().equals(user)){
            TextView sellerClickable = new TextView(this);
            sellerClickable.setLayoutParams(params);
            String seller = getResources().getString(R.string.seller);
            seller += " " +item.getOwner().getPseudo();
            sellerClickable.setText(seller);
            sellerClickable.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    intent = new Intent(ConsultItems.this,ConsultMySales.class);
                    intent.putExtra("user",user);
                    intent.putExtra("userToCheck",item.getOwner());
                    startActivity(intent);
                }
            });
            row.addView(sellerClickable);
        }
    }
    public void clear_item(ViewGroup container){
        container.removeAllViews();
    }

    public void populate(List<Item> allItems){
        Item.setAllItems(allItems);

        if(scrollView != null){
            clear_item(scrollView);
        }
        if(helloLayout != null){
            clear_item(helloLayout);
        }
        if(layout_sort!=null){
            clear_item(layout_sort);
        }
        setLayout();
        setLayout_sort();
    }
    public void populate_error(String error){
        System.out.println(error);
    }

    public void setLayout_sort(){
        Button button_sort = new Button(this);
        button_sort.setLayoutParams(params);
        button_sort.setText(getResources().getString(R.string.sort));
        button_sort.setTextAppearance(this,R.style.btn_text);
        layout_sort.addView(button_sort);
        button_sort.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                clear_item(scrollView);

                // => tab[item][distancebetween]
                int size = Item.getAllItems().size();
                int[][] itemsAndDistance = new int[size][2];
                int item_index = 0;
                //int distance_index = 0;
                if(size > 0) {
                    for(Item item : Item.getAllItems()){
                        Location pos_current_user = new Location("");
                        pos_current_user.setLatitude(Double.parseDouble(ConsultItems.this.user.getLatitude()));
                        pos_current_user.setLongitude(Double.parseDouble(ConsultItems.this.user.getLongitude()));

                        Location pos_seller = new Location("");
                        pos_seller.setLatitude(Double.parseDouble(item.getOwner().getLatitude()));
                        pos_seller.setLongitude(Double.parseDouble(item.getOwner().getLongitude()));
                        int distanceBetween = Math.round((pos_current_user.distanceTo(pos_seller)) / 1000);

                        itemsAndDistance[item_index][0] = item.getId();
                        itemsAndDistance[item_index][1] = distanceBetween;

                        item_index++;
                    }


                    Sort2DArrayBasedOnColumnNumber(itemsAndDistance,1);
                    List<Item> itemsSorted = new ArrayList<Item>();

                    List<Integer> distances = new ArrayList<Integer>();
                    for(int i = 0;i<itemsAndDistance.length;i++){
                        if(!itemsSorted.contains(Item.getItem(itemsAndDistance[i][0]))){
                            itemsSorted.add(Item.getItem(itemsAndDistance[i][0]));
                            distances.add(itemsAndDistance[i][1]);
                        }
                    }

                    Item.setAllItems(itemsSorted);
                    set_scroll();
                }
            }
        });
    }
    public static  void Sort2DArrayBasedOnColumnNumber (int[][] array, final int columnNumber){
        Arrays.sort(array, new Comparator<int[]>() {
            @Override
            public int compare(int[] first, int[] second) {
                if(first[columnNumber] > second[columnNumber]) return 1;
                else return -1;
            }
        });
    }
}