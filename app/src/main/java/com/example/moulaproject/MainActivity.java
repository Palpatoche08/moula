package com.example.moulaproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;

import com.example.moulaproject.Database.UserRepo;
import com.example.moulaproject.Database.entities.Currency;
import com.example.moulaproject.Database.entities.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private UserRepo db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the database repository
        db = new UserRepo(getApplication());

        // Insert default accounts and currency if they do not already exist
        insertDefaultValuesIfNotExist();

        Button loginButton = findViewById(R.id.login_btn);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        Button createAccountButton = findViewById(R.id.createAccount);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });
    }

    // Function to insert default values if they don't already exist
    private void insertDefaultValuesIfNotExist() {
        // Retrieve all existing users
        List<User> allUsers = db.getAllLogs();
        boolean adminExists = allUsers.stream().anyMatch(user -> user.getName().equals("admin2"));
        boolean testUser1Exists = allUsers.stream().anyMatch(user -> user.getName().equals("testUser2"));

        // Insert the default users if they don't already exist
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

        // Insert the default currencies if they don't already exist
        if (db.getCurrencyByName("Dollar") == null) {
            Currency dollar = new Currency("Dollar", 1.0); // 1 USD to 1 USD
            db.insertCurrency(dollar);
            Log.d("DefaultValues", "Currency 'Dollar' inserted");
        }

        if (db.getCurrencyByName("Yen") == null) {
            Currency yen = new Currency("Yen", 0.0073); // Approximate value of 1 Yen to USD
            db.insertCurrency(yen);
            Log.d("DefaultValues", "Currency 'Yen' inserted");
        }

        if (db.getCurrencyByName("Pesos") == null) {
            Currency pesos = new Currency("Pesos", 0.056); // Approximate value of 1 Mexican Peso to USD
            db.insertCurrency(pesos);
            Log.d("DefaultValues", "Currency 'Pesos' inserted");
        }

        if (db.getCurrencyByName("Euro") == null) {
            Currency euro = new Currency("Euro", 1.1); // Approximate value of 1 Euro to USD
            db.insertCurrency(euro);
            Log.d("DefaultValues", "Currency 'Euro' inserted");
        }
    }

}




//package com.example.moulaproject;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//public class MainActivity extends AppCompatActivity {
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//
//        Button login_button = findViewById(R.id.login_btn);
//        login_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        Button createAccount = findViewById(R.id.createAccount);
//        createAccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, CreateAccountActivity.class);
//                startActivity(intent);
//            }
//        });
//
//
//
//    }
//}