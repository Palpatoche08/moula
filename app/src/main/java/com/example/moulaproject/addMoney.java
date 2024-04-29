package com.example.moulaproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moulaproject.Database.UserDAO;
import com.example.moulaproject.Database.UserDatabase;
import com.example.moulaproject.Database.entities.User;

public class addMoney extends AppCompatActivity {

    private EditText amountEntered;
    private Button addButton;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_amount);
        setContentView(R.layout.activity_add_money);

        amountEntered = findViewById(R.id.amountEntered);
        addButton = findViewById(R.id.add);
        userDAO = UserDatabase.getDatabase(this).UserDAO();

        addButton.setOnClickListener(v -> {
            try {
                int amount = Integer.parseInt(amountEntered.getText().toString());
                updateBalance(amount);

            } catch (NumberFormatException e) {
                Toast.makeText(addMoney.this, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateBalance(int amount) {
        new Thread(() -> {
            String username = getSharedPreferences("LoginActivity", MODE_PRIVATE)
                    .getString("enteredUsername", null);
            if (username != null) {
                User user = userDAO.getUserByName(username);
                if (user != null) {
                    int newBalance = user.getBalance() + amount;
                    userDAO.updateBalance(username, newBalance);
                    runOnUiThread(() -> {
                        Toast.makeText(addMoney.this, "Balance updated: " + newBalance + " Credits", Toast.LENGTH_LONG).show();
                        setResult(RESULT_OK); // Set result for the BankingActivity
                        finish(); // Close this activity and return
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(addMoney.this, "User not found", Toast.LENGTH_SHORT).show());
                }
            } else {
                runOnUiThread(() -> Toast.makeText(addMoney.this, "No user logged in", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}