package com.example.moulaproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moulaproject.Database.UserRepo;
import com.example.moulaproject.Database.entities.User;
import com.example.moulaproject.Database.UserDAO;
import com.example.moulaproject.Database.UserDatabase;

import java.util.Objects;

public class Login_activity extends AppCompatActivity {

    private UserRepo db;
    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.db = new UserRepo(getApplication());
        this.prefs = getSharedPreferences("Login_activity", Context.MODE_PRIVATE);
        this.prefsEdit = prefs.edit();

        Button sumbitButton = findViewById(R.id.login_btn);

        sumbitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username_input = findViewById(R.id.username_input);
                String enteredUsername = username_input.getText().toString();

                EditText password_input = findViewById(R.id.password_input);
                String password = password_input.getText().toString();

                User user = db.getUserByName(enteredUsername);
                if(user == null || !Objects.equals(user.getPassword(), password)){
                    Toast.makeText(Login_activity.this,
                            "Sorry, Your username or password is incorrect.",Toast.LENGTH_SHORT).show();
                }
                else{
                    prefsEdit.putString("curUser",user.getName());
                    prefsEdit.putBoolean("isAdmin",user.isAdmin());
                    prefsEdit.apply();
                    Intent intent = new Intent(Login_activity.this, LandingPageActivity.class);
                    startActivity(intent);
                }

            }
        });

    }


}
