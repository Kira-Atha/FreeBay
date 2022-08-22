package be.huygebaert.freebay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;

import be.huygebaert.freebay.accessDb.createItem;
import be.huygebaert.freebay.models.Item;
import be.huygebaert.freebay.models.TypeItem;
import be.huygebaert.freebay.models.User;

public class AddItem extends AppCompatActivity {
    private User user;
    private Intent intent;
    private String name;
    private double price;
    private String description;
    private String type;
    private TypeItem type_selected;
    private EditText et_name_item;
    private EditText et_price_item;
    private EditText et_desc_item;
    private EditText et_type_item;

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.btn_exit:
                    AddItem.this.finishAffinity();
                    break;
                case R.id.btn_logout:
                    intent = new Intent(AddItem.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    AddItem.this.finish();
                    startActivity(intent);
                    break;
                case R.id.btn_back:
                    intent = new Intent(AddItem.this, ConsultItems.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("user",user);
                    AddItem.this.finish();
                    startActivity(intent);
                    break;
                case R.id.btn_wizard:
                    intent = new Intent(AddItem.this, WizardType.class);
                    et_name_item = findViewById(R.id.et_name_item);
                    et_price_item = findViewById(R.id.et_price_item);
                    et_desc_item = findViewById(R.id.et_desc_item);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    name = et_name_item.getText().toString();
                    description = et_desc_item.getText().toString();
                    try {
                        price = Double.parseDouble(et_price_item.getText().toString());
                    }catch(NumberFormatException e){
                        price =0;
                    }

                    intent.putExtra("user",user);
                    intent.putExtra("name",name);
                    intent.putExtra("price",price);
                    intent.putExtra("description",description);
                    AddItem.this.finish();
                    startActivity(intent);
                    break;
                case R.id.btn_add_item:
                    et_name_item = findViewById(R.id.et_name_item);
                    et_price_item = findViewById(R.id.et_price_item);
                    et_desc_item = findViewById(R.id.et_desc_item);
                    et_type_item = findViewById(R.id.et_type_item);

                    name = et_name_item.getText().toString();
                    description = et_desc_item.getText().toString();
                    type = et_type_item.getText().toString();

                    if (name.equals(null) || description.equals(null) || type.equals(null) || name.equals("") || description.equals("") || type.equals("")) {
                        Toast toast = Toast.makeText(AddItem.this, getResources().getString(R.string.fieldsEmpty), Toast.LENGTH_LONG);
                        toast.show();
                        break;
                    }
                    String errors = "";
                    if(!name.matches("^[A-z]*$") || name.length() > 30 || name.length() < 2){
                        errors += getResources().getString(R.string.rulesNameItem);
                        errors += "\n";
                    }
                    try{
                        price = Double.parseDouble(et_price_item.getText().toString());
                        if(price<0 || price > 100000){
                            errors += getResources().getString(R.string.rulesPrice);
                        }
                    }catch(Exception e){
                        errors += getResources().getString(R.string.rulesPrice);
                        errors += "\n";
                    }
                    if(description.length() > 500 || !description.matches("^[A-z 0-9]*$")){
                        errors += getResources().getString(R.string.rulesDesc);
                        errors += "\n";
                    }
                    if(!type.matches("^[A-z]*$") || type.length() > 30 || type.length() < 2){
                        errors += getResources().getString(R.string.rulesType);
                        errors += "\n";
                    }
                    if (errors.length() > 0) {
                        Toast toast = Toast.makeText(AddItem.this, errors, Toast.LENGTH_LONG);
                        toast.show();
                        break;
                    }
     // Si pas revenu du wizard, créer un nouveau TypeItem, sinon récupérer celui qui a été choisi
                    if(type_selected!=null){
                        Item item = new Item(0,name,price,description,type_selected,user);
                        if(user.createItem(item)){
                            new createItem(user,item,AddItem.this).execute();
                        }
                    }else{
                        TypeItem.setAllTypes();
                        TypeItem newType = new TypeItem(type);
                        TypeItem.addType(newType);
                        Item item = new Item(0,name,price,description,newType,user);
                        if(user.createItem(item)){
                            new createItem(user,item,AddItem.this).execute();
                        }
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        user = (User) this.getIntent().getSerializableExtra("user");
        type_selected = (TypeItem) this.getIntent().getSerializableExtra("type");
        if(type_selected !=null){
            et_type_item = findViewById(R.id.et_type_item);
            et_type_item.setText(type_selected.getName());
        }
        name = (String) this.getIntent().getSerializableExtra("name");
        if(name!=null){
            et_name_item = findViewById(R.id.et_name_item);
            et_name_item.setText(name);
        }
        if(this.getIntent().getSerializableExtra("price") != null){
            price = (Double) this.getIntent().getSerializableExtra("price");
            if(price!=0){
                et_price_item = findViewById(R.id.et_price_item);
                et_price_item.setText(String.valueOf(price));
            }
        }
        description = (String) this.getIntent().getSerializableExtra("description");
        if(description!=null){
            et_desc_item = findViewById(R.id.et_desc_item);
            et_desc_item.setText(description);
        }
        Button btn_exit = findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(listener);
        Button btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(listener);
        Button btn_add_item = findViewById(R.id.btn_add_item);
        btn_add_item.setOnClickListener(listener);
        Button btn_wizard = findViewById(R.id.btn_wizard);
        btn_wizard.setOnClickListener(listener);
        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(listener);
    }

    protected void onPause(){
        super.onPause();
        type_selected = (TypeItem) this.getIntent().getSerializableExtra("type");
        if(type_selected !=null){
            EditText et_type_item = findViewById(R.id.et_type_item);
            et_type_item.setText(type_selected.getName());
        }
    }
    protected void onRestart(){
        super.onRestart();
        type_selected = (TypeItem) this.getIntent().getSerializableExtra("type");
        if(type_selected !=null){
            EditText et_type_item = findViewById(R.id.et_type_item);
            et_type_item.setText(type_selected.getName());
        }
    }
    public void populate(String msg) {
        Toast toast;
        switch(msg){
            case "ok":
                toast = Toast.makeText(AddItem.this, getResources().getString(R.string.createdItem), Toast.LENGTH_LONG);
                toast.show();
                intent = new Intent(AddItem.this, ConsultItems.class);
                intent.putExtra("user",user);
                this.finish();
                startActivity(intent);
                break;
            case "Field empty":
                toast = Toast.makeText(AddItem.this, getResources().getString(R.string.fieldsEmpty), Toast.LENGTH_LONG);
                toast.show();
                break;
            default:
                // Pour le dev
                System.out.println(msg);
                break;
        }
    }
}