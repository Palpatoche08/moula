package com.example.moulaproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moulaproject.Database.UserRepo;
import com.example.moulaproject.Database.entities.User;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private UserRepo db;
    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEdit;
    public String enteredUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.db = new UserRepo(getApplication());
        this.prefs = getSharedPreferences("LoginActivity", Context.MODE_PRIVATE);
        this.prefsEdit = prefs.edit();

        Button sumbitButton = findViewById(R.id.login_btn);

        sumbitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText usernameInput = findViewById(R.id.username_input);
                enteredUsername = usernameInput.getText().toString();

                EditText passwordInput = findViewById(R.id.password_input);
                String password = passwordInput.getText().toString();

                new Thread(() -> {
                    User user = db.getUserByName(enteredUsername);
                    runOnUiThread(() -> {
                        if (user == null || !Objects.equals(user.getPassword(), password)) {
                            Toast.makeText(LoginActivity.this,
                                    "Sorry, Your username or password is incorrect.", Toast.LENGTH_SHORT).show();
                        } else {
                            prefsEdit.putString("curUser", user.getName());
                            prefsEdit.putBoolean("isAdmin", user.isAdmin());
                            prefsEdit.putString("enteredUsername", enteredUsername);
                            prefsEdit.apply();

                            Intent intent = new Intent(LoginActivity.this, BankingActivity.class);
                            intent.putExtra("UserBalance", user.getBalance()); // Pass the balance
                            startActivity(intent);
                        }
                    });
                }).start();
            }
        });

        //old code that works:
//        sumbitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EditText username_input = findViewById(R.id.username_input);
//                enteredUsername = username_input.getText().toString();
//
//                EditText password_input = findViewById(R.id.password_input);
//                String password = password_input.getText().toString();
//
//                User user = db.getUserByName(enteredUsername);
//                if(enteredUsername.isEmpty() || !Objects.equals(user.getPassword(), password)){
//                    Toast.makeText(LoginActivity.this,
//                            "Sorry, Your username or password is incorrect.",Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    prefsEdit.putString("curUser",user.getName());
//                    prefsEdit.putBoolean("isAdmin",user.isAdmin());
//                    prefsEdit.putString("enteredUsername", enteredUsername);
//                    prefsEdit.apply();
//                    Intent intent = new Intent(LoginActivity.this, LandingPageActivity.class);
//                    startActivity(intent);
//                }
//
//            }
//        });

    }


}
