package com.example.moulaproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.moulaproject.Database.UserDAO;
import com.example.moulaproject.Database.UserRepo;
import com.example.moulaproject.LoginActivity;

public class LandingPageActivity extends AppCompatActivity {

    private SharedPreferences prefs;

    private SharedPreferences.Editor prefsEdit;

    private UserDAO userDAO;

    LoginActivity loginActivity;

    public boolean startsWithAdmin(String name) {
        return name != null && name.toLowerCase().startsWith("admin");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        this.prefs = getSharedPreferences("LoginActivity", Context.MODE_PRIVATE);
        this.prefsEdit = prefs.edit();


        Button admin_button = findViewById(R.id.admin_button);
        String enteredUsername = prefs.getString("enteredUsername", "");
        Log.d("enteredUsername", enteredUsername + " ");
        admin_button.setVisibility(View.INVISIBLE);
        if (startsWithAdmin(CreateAccountActivity.enteredUsername) || (startsWithAdmin(LoginActivity.enteredUsername) && !startsWithAdmin(CreateAccountActivity.enteredUsername))) {
            admin_button.setVisibility(View.VISIBLE);
        }

        admin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingPageActivity.this, AdminActivity.class);
                startActivity(intent);
            }

        });

        Button transaction = findViewById(R.id.transactions_button);
        transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingPageActivity.this, BankingActivity.class);
                Log.d("landing user", enteredUsername + " ");
                prefsEdit.putString("enteredUsername", enteredUsername);
                prefsEdit.apply();
                startActivity(intent);
            }

        });



        Button logout_button = findViewById(R.id.logout_button);
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingPageActivity.this, MainActivity.class);

                startActivity(intent);
            }

        });

    }
}