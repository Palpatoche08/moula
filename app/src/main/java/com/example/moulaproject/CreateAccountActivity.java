package com.example.moulaproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moulaproject.Database.UserRepo;
import com.example.moulaproject.Database.entities.Currency;
import com.example.moulaproject.Database.entities.User;

import java.util.List;

public class CreateAccountActivity extends AppCompatActivity {

    private UserRepo db;
    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEdit;
    public static String enteredUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        this.db = new UserRepo(getApplication());
        this.prefs = getSharedPreferences("CreateAccountActivity", Context.MODE_PRIVATE);
        this.prefsEdit = prefs.edit();

        // Insert default values if they do not already exist
        insertDefaultValuesIfNotExist();

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
                if (user != null) {
                    Toast.makeText(CreateAccountActivity.this, "This username already exists.", Toast.LENGTH_SHORT).show();
                } else if (enteredUsername.isEmpty()) {
                    Toast.makeText(CreateAccountActivity.this, "You need to enter a username", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(repassword)) {
                    Toast.makeText(CreateAccountActivity.this, "The password is not the same, try again", Toast.LENGTH_SHORT).show();
                } else {
                    User newUser = new User(enteredUsername, password, false);
                    db.insertUser(newUser);
                    prefsEdit.putString("curUser", newUser.getName());
                    prefsEdit.putBoolean("isAdmin", newUser.isAdmin());
                    prefsEdit.putString("enteredUsername", enteredUsername);
                    prefsEdit.apply();
                    Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
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

    // Function to insert default values if they don't already exist
    private void insertDefaultValuesIfNotExist() {
        List<User> allUsers = db.getAllLogs();
        boolean adminExists = allUsers.stream().anyMatch(user -> user.getName().equals("admin2"));
        boolean testUser1Exists = allUsers.stream().anyMatch(user -> user.getName().equals("testUser2"));

        if (!adminExists) {
            User admin = new User("admin2", "admin2", true);
            db.insertUser(admin);
            Log.d("DefaultValues", "Admin user inserted");
        }

        if (!testUser1Exists) {
            User testUser1 = new User("testUser2", "testUser2", false);
            db.insertUser(testUser1);
            Log.d("DefaultValues", "Test user inserted");
        }

        if (db.getCurrencyByName("Dollar") == null) {
            Currency dollar = new Currency("Dollar", 1.0);
            db.insertCurrency(dollar);
            Log.d("DefaultValues", "Currency 'Dollar' inserted");
        }
    }
}



//package com.example.moulaproject;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.moulaproject.Database.UserRepo;
//import com.example.moulaproject.Database.entities.User;
//
//import java.util.Objects;
//
//public class CreateAccountActivity extends AppCompatActivity {
//
//    private UserRepo db;
//    private SharedPreferences prefs;
//    private SharedPreferences.Editor prefsEdit;
//    public static String enteredUsername;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_create_account);
//        this.db = new UserRepo(getApplication());
//        this.prefs = getSharedPreferences("CreateAccountActivity", Context.MODE_PRIVATE);
//        this.prefsEdit = prefs.edit();
//
//        Button submitButton = findViewById(R.id.login_btn);
//
//        submitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EditText usernameInput = findViewById(R.id.createUsername);
//                enteredUsername = usernameInput.getText().toString();
//
//                EditText passwordInput = findViewById(R.id.createPassword);
//                String password = passwordInput.getText().toString();
//
//                EditText repasswordInput = findViewById(R.id.recreatePassword);
//                String repassword = repasswordInput.getText().toString();
//
//                User user = db.getUserByName(enteredUsername);
//                if(user != null) {
//                    Toast.makeText(CreateAccountActivity.this, "This username already exists.", Toast.LENGTH_SHORT).show();
//                }
//                else if (enteredUsername.isEmpty())
//                {
//                    Toast.makeText(CreateAccountActivity.this, "You need to enter a username", Toast.LENGTH_SHORT).show();
//                }
//                else if (!password.equals(repassword))
//                {
//                    Toast.makeText(CreateAccountActivity.this, "The password is not the same, try again", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    User newUser = new User(enteredUsername, password, false);
//                    db.insertUser(newUser);
//                    prefsEdit.putString("curUser", newUser.getName());
//                    prefsEdit.putBoolean("isAdmin", newUser.isAdmin());
//                    prefsEdit.putString("enteredUsername", enteredUsername);
//                    Log.d("defaulvalue",  prefs.getString("enteredUsername", ""));
//                    prefsEdit.apply();
//                    Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                }
//            }
//        });
//
//        Button createAccount = findViewById(R.id.enterLogin);
//        createAccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
//                startActivity(intent);
//            }
//        });
//
//
//    }
//}
