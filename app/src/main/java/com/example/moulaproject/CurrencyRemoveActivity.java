package com.example.moulaproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.moulaproject.Database.UserRepo;
import com.example.moulaproject.Database.entities.Currency;

public class CurrencyRemoveActivity extends AppCompatActivity {

    private UserRepo userRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_remove);

        userRepo = new UserRepo(getApplication());

        EditText currencyNameEditText = findViewById(R.id.name);
        EditText currencyRateEditText = findViewById(R.id.rate);

        Button gobackButton = findViewById(R.id.goback);
        gobackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CurrencyRemoveActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });

        Button removeCurrencyButton = findViewById(R.id.Confirm);
        removeCurrencyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = currencyNameEditText.getText().toString().trim();
                double rate = Double.parseDouble(currencyRateEditText.getText().toString().trim());

                Currency existingCurrency = userRepo.getCurrencyByName(name);
                if (existingCurrency != null) {
                    userRepo.deleteCurrency(existingCurrency);
                    Toast.makeText(CurrencyRemoveActivity.this, "Existing currency removed", Toast.LENGTH_SHORT).show();
                }
                else {
                    Currency newCurrency = new Currency(name, rate);
                    userRepo.insertCurrency(newCurrency);
                    Toast.makeText(CurrencyRemoveActivity.this, "New currency added", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}