package com.example.moulaproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.moulaproject.Database.UserDAO;
import com.example.moulaproject.Database.UserDatabase;
import com.example.moulaproject.Database.entities.User;

public class BankingActivity extends AppCompatActivity {

    private TextView bankingAccountField;
    private UserDAO userDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banking);

        bankingAccountField = findViewById(R.id.banking_account_field);

        // Get the balance passed from LoginActivity
        int balance = getIntent().getIntExtra("UserBalance", 50);  // Default to 0 if no data found
        displayBalance(balance);


    }
    private void displayBalance(int balance) {
        bankingAccountField.setText("Balance: " + balance + " Credits");
    }



}