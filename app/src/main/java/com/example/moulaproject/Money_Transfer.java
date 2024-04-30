package com.example.moulaproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moulaproject.Database.UserDAO;
import com.example.moulaproject.Database.UserDatabase;
import com.example.moulaproject.Database.entities.User;

public class Money_Transfer extends AppCompatActivity {

    private EditText trMoney;
    private EditText trId;
    private UserDAO userDAO;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_transfer);

        userDAO = UserDatabase.getDatabase(this).UserDAO();
        prefs = getSharedPreferences("LoginActivity", MODE_PRIVATE);

        trMoney = findViewById(R.id.TrMoney);
        trId = findViewById(R.id.TrId);
        Button sendButton = findViewById(R.id.send);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performTransaction();
                Intent intent = new Intent(Money_Transfer.this, BankingActivity.class);
                startActivity(intent);
            }
        });
    }

    //code bon
    private void performTransaction() {
        String currentUsername = prefs.getString("enteredUsername", null);
        if (currentUsername == null) {
            Toast.makeText(this, "No user logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        int amount;
        String recipientUsername;
        try {
            amount = Integer.parseInt(trMoney.getText().toString().trim());
            if (amount <= 0) {
                Toast.makeText(this, "Please enter a valid amount greater than 0", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid amount format", Toast.LENGTH_SHORT).show();
            return;
        }

        recipientUsername = trId.getText().toString().trim();
        if (recipientUsername.isEmpty()) {
            Toast.makeText(this, "Please enter a recipient username", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {
            User recipient = userDAO.getUserByName(recipientUsername);
            if (recipient != null) {
                int deductResult = userDAO.deductAmount(currentUsername, amount);
                if (deductResult > 0) {
                    int addResult = userDAO.addAmount(recipient.getId(), amount); // Note that addAmount still uses the ID
                    if (addResult > 0) {
                        runOnUiThread(() -> Toast.makeText(Money_Transfer.this, "Transaction successful", Toast.LENGTH_LONG).show());
                    } else {
                        runOnUiThread(() -> Toast.makeText(Money_Transfer.this, "Transaction failed: recipient update failed", Toast.LENGTH_LONG).show());
                    }
                } else {
                    runOnUiThread(() -> Toast.makeText(Money_Transfer.this, "Transaction failed: insufficient funds", Toast.LENGTH_LONG).show());
                }
            } else {
                runOnUiThread(() -> Toast.makeText(Money_Transfer.this, "Recipient not found", Toast.LENGTH_LONG).show());
            }
        }).start();
    }


}
