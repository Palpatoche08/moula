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
        // Initialize UserDAO
        UserDatabase userDatabase = UserDatabase.getDatabase(this);
        userDAO = userDatabase.UserDAO();

        // Initialize views
        bankingAccountField = findViewById(R.id.banking_account_field);

        // Fetch and display user's banking account field
        displayBankingAccount();
    }
    private void displayBankingAccount() {
        // Assuming you have a logged-in user, fetch their banking account info
        User loggedInUser = userDAO.getUserByName("logged-in-username");
        if (loggedInUser != null) {
            //int bankingAccount = userDAO.getBalance(loggedInUser.getName());
            //bankingAccountField.setText(bankingAccount);
        } else {
            // Handle case where user is not found or not logged in
        }
    }
}