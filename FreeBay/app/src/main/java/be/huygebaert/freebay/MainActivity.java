package be.huygebaert.freebay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

import be.huygebaert.freebay.accessDb.getAllUsers;
import be.huygebaert.freebay.models.User;

public class MainActivity extends AppCompatActivity {

    View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View view){
            Intent intent;
            switch(view.getId()){
                case R.id.btn_signin:
                    EditText pseudo = findViewById(R.id.et_pseudo);
                    EditText password = findViewById(R.id.et_password);

                    if(pseudo.equals(null) || password.equals(null) || pseudo.getText().toString().equals("") || password.getText().toString().equals("")){
                        Toast toast = Toast.makeText(MainActivity.this,getResources().getString(R.string.fieldsEmpty),Toast.LENGTH_LONG);
                        toast.show();
                        break;
                    }
                    User user = new User(pseudo.getText().toString(),password.getText().toString());
                    User userToConnect = user.signIn();

                    if(userToConnect != null){
                        intent = new Intent(MainActivity.this,ConsultItems.class);
                        intent.putExtra("user",userToConnect);
                        MainActivity.this.finish();
                        startActivity(intent);
                    }else{
                        Toast toast = Toast.makeText(MainActivity.this,getResources().getString(R.string.incorrectAccount),Toast.LENGTH_LONG);
                        toast.show();
                    }
                    break;
                case R.id.btn_signup:
                    intent = new Intent(MainActivity.this, Signup.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    break;
                case R.id.btn_exit:
                    MainActivity.this.finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Le temps d'écrire le password/pseudo, ça aura le temps de charger
        new getAllUsers(MainActivity.this).execute();
        Button btn_signIn = (Button)findViewById(R.id.btn_signin);
        btn_signIn.setOnClickListener(listener);
        Button btn_signUp = (Button) findViewById(R.id.btn_signup);
        btn_signUp.setOnClickListener(listener);
        Button btn_exit = (Button) findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(listener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Mettre à jour les utilisateurs lorsque quelqu'un vient de s'inscrire afin de pouvoir se connecter
        new getAllUsers(MainActivity.this).execute();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        new getAllUsers(MainActivity.this).execute();
    }
    public void populate(List<User> allUsers){
        User.setAllUsers(allUsers);
    }

    public void populate_error(String error) {
        // pour le dev
        System.out.println(error);
    }
}