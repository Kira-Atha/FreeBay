package be.huygebaert.freebay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import be.huygebaert.freebay.accessDb.getAllUsers;
import be.huygebaert.freebay.accessDb.signUp;
import be.huygebaert.freebay.models.User;

public class Signup extends AppCompatActivity {
    private String password ="";
    private String pseudo = "";
    private Intent intent;

    View.OnClickListener listener = new View.OnClickListener() {
        @SuppressLint("NewApi")
        @Override
        public void onClick(View view) {
            String password_verif="";
            switch (view.getId()) {
                case R.id.btn_signup:

                    EditText et_pseudo = findViewById(R.id.et_pseudo);
                    EditText et_password = findViewById(R.id.et_password);
                    EditText et_password_verif = findViewById(R.id.et_password_verif);

                    pseudo = et_pseudo.getText().toString();
                    password = et_password.getText().toString();
                    password_verif = et_password_verif.getText().toString();
                    if (pseudo.equals(null) || password.equals(null) || password_verif.equals(null) || pseudo.equals("") || password.equals("") || password_verif.equals("")) {
                        Toast toast = Toast.makeText(Signup.this, getResources().getString(R.string.fieldsEmpty), Toast.LENGTH_LONG);
                        toast.show();
                        break;
                    }
                    String errors = "";
                    if (!pseudo.matches("^[a-zA-Z0-9]+$") || pseudo.length()<3 || pseudo.length() >20) {
                        errors += getResources().getString(R.string.regPseudo);
                        errors += "\n";
                    }
                    if (password.length() > 200) {
                        errors += getResources().getString(R.string.rulesPassword);
                        errors += "\n";
                    }
                    if (!password.equals(password_verif)) {
                        errors += getResources().getString(R.string.rulesPasswordVerif);
                        errors += "\n";
                    }
                    if (errors.length() > 0) {
                        Toast toast = Toast.makeText(Signup.this, errors, Toast.LENGTH_LONG);
                        toast.show();
                        break;
                    }
    //verif pour localisation gps
                    if (ActivityCompat.checkSelfPermission(Signup.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                            explainUtilities();
                        } else {
                            askPermission();
                        }
                    } else {
                        getPosition();
                    }
                    break;
                case R.id.btn_back:
                    intent = new Intent(Signup.this, MainActivity.class);
                    Signup.this.finish();
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    break;
                case R.id.btn_exit:
                    Signup.this.finishAffinity();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(listener);
        Button btn_exit = findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(listener);
        Button btn_signup = findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(listener);
    }

    public void populate(String msg) {
        Toast toast;
        switch(msg){
            case "ok":
                toast = Toast.makeText(Signup.this, getResources().getString(R.string.createdAccount), Toast.LENGTH_LONG);
                toast.show();
                intent = new Intent(Signup.this, MainActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.finish();
                startActivity(intent);
                break;
            case "Field empty":
                toast = Toast.makeText(Signup.this, getResources().getString(R.string.fieldsEmpty), Toast.LENGTH_LONG);
                toast.show();
                break;
            default:
                // Pour le dev
                System.out.println(msg);
                break;

        }
    }

    //Snackbar => permet l'utilisation d'un bouton en comparaison du toast qui ne peut en avoir
    private void explainUtilities() {
        View layout = findViewById(R.id.layout_signup);
        Snackbar.make(layout, getResources().getString(R.string.explainPermissions), Snackbar.LENGTH_LONG).setAction("Activate", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askPermission();
            }
        }).show();
    }

    private void askPermission(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0);
    }
    @SuppressLint("NewApi")
    @Override
    public void onRequestPermissionsResult(int code,String[] permissions,int[] results) {
        switch(code){
            case 0:
                if(results[0] == PackageManager.PERMISSION_GRANTED){
                    getPosition();
                }else if(!shouldShowRequestPermissionRationale(permissions[0])){
                    displayParameters();
                }else{
                    explainUtilities();
                }
                break;
        }
        super.onRequestPermissionsResult(code, permissions, results);
    }

    //Géré avant
    @SuppressLint("MissingPermission")
    private void getPosition(){
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        //gps allumé?
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
        final LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                String longitude = String.valueOf(location.getLongitude());
                String latitude = String.valueOf(location.getLatitude());
                createUser(longitude,latitude);
            }
            @Override
            public void onProviderEnabled(@NonNull String provider) { }
            @Override
            public void onProviderDisabled(@NonNull String provider) { }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) { }
        };
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, locationListener);
    }
    private void displayParameters(){
        View layout = findViewById(R.id.layout_signup);
        Snackbar.make(layout,getResources().getString(R.string.permissionDisabled),Snackbar.LENGTH_LONG).setAction("Parameters", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                final Uri uri = Uri.fromParts("package", "test_gps", null);
                intent.setData(uri);
                startActivity(intent);
            }
        }).show();
    }
    private void createUser(String longitude,String latitude){
        User user = null;
        user = new User(0,pseudo,password,longitude,latitude);
        //envoyer user au constructeur de la tâche asynchrone pour le créer en db si si c'est possible
        if(user.signUp()) {
            new signUp(user, Signup.this).execute();
        }else{
            Toast toast = Toast.makeText(Signup.this, getResources().getString(R.string.pseudoExist), Toast.LENGTH_LONG);
            toast.show();
        }
    }
}