package be.huygebaert.freebay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import be.huygebaert.freebay.models.User;

public class MainActivity extends AppCompatActivity {

    View.OnClickListener listener = new View.OnClickListener() {
        private Intent intent;


        @Override
        public void onClick(View view){

            switch(view.getId()){
                case R.id.btn_signin:
                    List<User> allUsers = User.getAllUsers();

                    EditText pseudo = findViewById(R.id.et_pseudo);
                    EditText password = findViewById(R.id.et_password);
                    User user = new User(pseudo.getText().toString(),password.getText().toString());
                    for(User user_ : allUsers){
                        if(user.equals(user_)) {
                            user = User.getUser(user_.getId());
                            break;
                        }
                    }

                    if(user.getId() ==0){
                        Toast toast = Toast.makeText(MainActivity.this,getResources().getString(R.string.incorrectAccount),Toast.LENGTH_LONG);
                        toast.show();
                    }else{
                        intent = new Intent(MainActivity.this,ConsultItems.class);
                        intent.putExtra("user",user);
                        startActivity(intent);
                    }
                    break;
                case R.id.btn_signup:
                    intent = new Intent(MainActivity.this, Signup.class);
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

        Button btn_signIn = (Button)findViewById(R.id.btn_signin);
        btn_signIn.setOnClickListener(listener);

        Button btn_signUp = (Button) findViewById(R.id.btn_signup);
        btn_signUp.setOnClickListener(listener);

        Button btn_exit = (Button) findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(listener);
    }
}