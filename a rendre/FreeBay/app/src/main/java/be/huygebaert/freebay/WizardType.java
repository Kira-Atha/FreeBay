package be.huygebaert.freebay;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import be.huygebaert.freebay.models.TypeItem;
import be.huygebaert.freebay.models.User;

public class WizardType extends AppCompatActivity {
    private String typeSelected = "";
    private Intent intent;
    private User user;
    double price;
    String name;
    String description;

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                //sans le choix
                case R.id.btn_back:
                    intent = new Intent(WizardType.this, AddItem.class);
                    WizardType.this.finish();
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    break;
                // avec le choix
                case 10:
                    if (!typeSelected.equals("")) {
                        TypeItem type = TypeItem.getTypeItem(typeSelected);
                        intent = new Intent(WizardType.this, AddItem.class);
                        WizardType.this.finish();
                        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("type", type);
                        intent.putExtra("user", user);
                        intent.putExtra("name", name);
                        intent.putExtra("price", price);
                        intent.putExtra("description", description);
                        startActivity(intent);
                        break;

                    } else {
                        Toast toast = Toast.makeText(WizardType.this, getResources().getString(R.string.nothingSelected), Toast.LENGTH_LONG);
                        toast.show();
                    }
                    break;
            }
        }
    };

    //râle pour l'id à 10
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = (User) getIntent().getSerializableExtra("user");

        price = (double) getIntent().getSerializableExtra("price");
        name = (String) getIntent().getSerializableExtra("name");
        description = (String) getIntent().getSerializableExtra("description");

        setContentView(R.layout.activity_wizard_type);
        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(listener);

        LinearLayout mainLayout = findViewById(R.id.wizard_layout);
        Spinner type_spin = new Spinner(this);
        List<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("");
        for (TypeItem type : TypeItem.getAllTypes()) {
            spinnerArray.add(type.getName());
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        type_spin.setAdapter(spinnerArrayAdapter);
        mainLayout.addView(type_spin);
        type_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                typeSelected = parent.getItemAtPosition(pos).toString();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        Button btn_choice_type = new Button(this);
        btn_choice_type.setText(getResources().getString(R.string.choose));
        btn_choice_type.setId(10);
        mainLayout.addView(btn_choice_type);
        btn_choice_type.setOnClickListener(listener);
    }
}