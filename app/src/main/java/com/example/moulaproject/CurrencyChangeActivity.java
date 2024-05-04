
package com.example.moulaproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moulaproject.Database.UserRepo;
import com.example.moulaproject.Database.entities.Currency;
import com.example.moulaproject.Database.entities.User;

import java.util.List;

public class CurrencyChangeActivity extends AppCompatActivity {

    private UserRepo db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_change);

        Button Confirm = findViewById(R.id.confirmButton);
        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CurrencyChangeActivity.this, "Currency Changed", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CurrencyChangeActivity.this, LandingPageActivity.class);
                startActivity(intent);
            }

        });



        Button landing = findViewById(R.id.goBackButton);
        landing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CurrencyChangeActivity.this, LandingPageActivity.class);
                startActivity(intent);
            }

        });
    }


}
