package com.example.moulaproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moulaproject.Database.UserRepo;
import com.example.moulaproject.Database.entities.Currency;

import java.util.List;

public class CurrencyChangeActivity extends AppCompatActivity {

    private UserRepo db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_change);

        String initialCurrency = BankingActivity.InitialCurrency;
        TextView currentCurrencyTextView = findViewById(R.id.currentCurrency);

        currentCurrencyTextView.setText("Current Currency: " + initialCurrency);

        db = new UserRepo(getApplication());

        Button confirmButton = findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText newCurrencyEditText = findViewById(R.id.newCurrency);
                String newCurrencyName = newCurrencyEditText.getText().toString().trim();

                if (isCurrencyValid(newCurrencyName)) {
                    BankingActivity.NewCurrency = newCurrencyName;
                    Toast.makeText(CurrencyChangeActivity.this, "Currency Changed", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CurrencyChangeActivity.this, LandingPageActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(CurrencyChangeActivity.this, "Invalid Currency", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button goBackButton = findViewById(R.id.goBackButton);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CurrencyChangeActivity.this, LandingPageActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean isCurrencyValid(String currencyName) {
        List<Currency> currencies = db.getAllCurrencies();
        for (Currency currency : currencies) {
            if (currency.getName().equalsIgnoreCase(currencyName)) {
                return true;
            }
        }
        return false;
    }
}
