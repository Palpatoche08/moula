package com.example.moulaproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moulaproject.Database.UserDAO;
import com.example.moulaproject.Database.UserDatabase;
import com.example.moulaproject.Database.UserRepo;
import com.example.moulaproject.Database.entities.Currency;

import java.util.List;

public class BankingActivity extends AppCompatActivity {

    private TextView bankingAccountField;
    private UserDAO userDAO;

    private SharedPreferences prefs;

    public static String InitialCurrency = "Dollar";


    public static String NewCurrency = "";

    public static boolean changed;

    private SharedPreferences.Editor prefsEdit;
    private final UserRepo db = new UserRepo(getApplication());

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
            Intent intent = new Intent(BankingActivity.this, AmountActivity.class);
            startActivity(intent);
        });

        Button addButton = findViewById(R.id.addMoney);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BankingActivity.this, AddMoneyActivity.class);
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

        Button transfer = findViewById(R.id.TrB);
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BankingActivity.this, MoneyTransferActivity.class);
                startActivity(intent);
            }
        });

        loadAndUpdateBalance(InitialCurrency);

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAndUpdateBalance(InitialCurrency);
    }

    private void loadAndUpdateBalance(String initialCurrency) {
        new Thread(() -> {
            String username = prefs.getString("enteredUsername", null);
            if (username != null) {
                double balance = userDAO.getBalanceByUsername(username);
                if(!InitialCurrency.equals("Dollar"))
                {
                    balance = convertBalance(balance, InitialCurrency);
                    changed = false;
                }
                InitialCurrency = initialCurrency;
                Log.d("Balance", username + " ");
                String formattedBalance = String.format("%.2f", balance); // Format the balance to show only 2 decimal places
                runOnUiThread(() -> displayBalance(formattedBalance));
            } else {
                runOnUiThread(() -> bankingAccountField.setText("No user logged in"));
            }
        }).start();
    }

    private void displayBalance(String balance) {
        if(!NewCurrency.equals(""))
        {
            InitialCurrency = NewCurrency;
            NewCurrency = "";
        }
        bankingAccountField.setText("balance: " + balance + " " + InitialCurrency );

    }

    public Currency findCurrency(String currencyName) {
        List<Currency> currencies = db.getAllCurrencies();
        for (Currency currency : currencies) {
            if (currency.getName().equalsIgnoreCase(currencyName)) {
                return currency;
            }
        }
        return null;
    }

    private double convertBalance(double balance, String initialCurrency) {
        switch (initialCurrency) {
            case "Dollar":
                return balance * 1;
            case "Euro":
                return balance / 1.08;
            case "Pesos":
                return balance / 0.059;
            case "Yen":
                return balance / 0.0065;
            case "Pounds":
                return balance * 1.25;
            default:
                return balance * 1;
        }
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
//        Button transactButton = findViewById(R.id.balance);
//        transactButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(BankingActivity.this, AmountActivity.class);
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