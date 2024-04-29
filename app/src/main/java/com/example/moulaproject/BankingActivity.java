package com.example.moulaproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.moulaproject.Database.UserDAO;
import com.example.moulaproject.Database.UserDatabase;
import com.example.moulaproject.Database.entities.User;

public class BankingActivity extends AppCompatActivity {

    private TextView bankingAccountField;
    private UserDAO userDAO;
    private SharedPreferences prefs;

    private SharedPreferences.Editor prefsEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banking);

        userDAO = UserDatabase.getDatabase(this).UserDAO();
        prefs = getSharedPreferences("LoginActivity", MODE_PRIVATE);
        this.prefsEdit = prefs.edit();//added

        bankingAccountField = findViewById(R.id.banking_account_field);
        String username  = prefs.getString("enteredUsername", "");
        Log.d("Banking", username );

        Button transacButton = findViewById(R.id.balance);
        transacButton.setOnClickListener(v -> {
            Intent intent = new Intent(BankingActivity.this, amount.class);
            startActivity(intent);
        });

        Button addButton = findViewById(R.id.addMoney);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BankingActivity.this, addMoney.class);
                startActivity(intent);
            }
        });

        Button back = findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BankingActivity.this, LandingPageActivity.class);
                startActivity(intent);
            }
        });

        loadAndUpdateBalance(); // Load balance on activity creation

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAndUpdateBalance(); // Reload balance every time the activity resumes
    }

    private void loadAndUpdateBalance() {
        new Thread(() -> {
            String username = prefs.getString("enteredUsername", null);
            if (username != null) {
                int balance = userDAO.getBalanceByUsername(username);
                Log.d("Balance", username + " ");
                runOnUiThread(() -> displayBalance(balance));
            } else {
                runOnUiThread(() -> bankingAccountField.setText("No user logged in"));
            }
        }).start();
    }

    private void displayBalance(int balance) {
        bankingAccountField.setText(String.valueOf(balance)); // Display balance in the EditText
    }


//    private TextView bankingAccountField;
//    private UserDAO userDAO;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_banking);
//
//        bankingAccountField = findViewById(R.id.banking_account_field);
//
//        // Get the balance passed from LoginActivity
//        int balance = getIntent().getIntExtra("UserBalance", 50);  // Default to 0 if no data found
//        displayBalance(balance);
//
//        Button transacButton = findViewById(R.id.balance);
//        transacButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(BankingActivity.this, amount.class);
//                startActivity(intent);
//            }
//        });
//    }
//    private void displayBalance(int balance) {
//        bankingAccountField.setText("Balance: " + balance + " Credits");
//    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        // Fetch and update the balance display again
//        int balance = getIntent().getIntExtra("UserBalance", 0);
//        displayBalance(balance);
//    }

}