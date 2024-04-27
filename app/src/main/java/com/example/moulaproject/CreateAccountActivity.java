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

public class CreateAccountActivity extends AppCompatActivity {

    private UserRepo db;
    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEdit;
    public String enteredUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        this.db = new UserRepo(getApplication());
        this.prefs = getSharedPreferences("CreateAccountActivity", Context.MODE_PRIVATE);
        this.prefsEdit = prefs.edit();

        Button submitButton = findViewById(R.id.login_btn);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText usernameInput = findViewById(R.id.createUsername);
                enteredUsername = usernameInput.getText().toString();

                EditText passwordInput = findViewById(R.id.createPassword);
                String password = passwordInput.getText().toString();

                EditText repasswordInput = findViewById(R.id.recreatePassword);
                String repassword = repasswordInput.getText().toString();

                User user = db.getUserByName(enteredUsername);
                if(user != null) {
                    Toast.makeText(CreateAccountActivity.this, "This username already exists.", Toast.LENGTH_SHORT).show();
                }
                else if (enteredUsername.isEmpty())
                {
                    Toast.makeText(CreateAccountActivity.this, "You need to enter a username", Toast.LENGTH_SHORT).show();
                }
                else if (!password.equals(repassword))
                {
                    Toast.makeText(CreateAccountActivity.this, "The password is not the same, try again", Toast.LENGTH_SHORT).show();
                }
                else {
                    User newUser = new User(enteredUsername, password, false);
                    db.insertUser(newUser);
                    prefsEdit.putString("curUser", newUser.getName());
                    prefsEdit.putBoolean("isAdmin", newUser.isAdmin());
                    prefsEdit.putString("enteredUsername", enteredUsername);
                    prefsEdit.apply();
                    Intent intent = new Intent(CreateAccountActivity.this, LandingPageActivity.class);
                    startActivity(intent);
                }
            }
        });

        Button createAccount = findViewById(R.id.enterLogin);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }
}
