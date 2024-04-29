package com.example.moulaproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moulaproject.Database.UserDAO;
import com.example.moulaproject.Database.UserDatabase;
import com.example.moulaproject.Database.entities.User;

public class amount extends AppCompatActivity {

    private EditText amountEntered;
    private Button addButton;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount);

        amountEntered = findViewById(R.id.amountEntered);
        addButton = findViewById(R.id.add);
        userDAO = UserDatabase.getDatabase(this).UserDAO();

        addButton.setOnClickListener(v -> {
            try {
                int amount = Integer.parseInt(amountEntered.getText().toString());
                updateBalance(amount);

            } catch (NumberFormatException e) {
                Toast.makeText(amount.this, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
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
                    int newBalance = user.getBalance() - amount;
                    userDAO.updateBalance(username, newBalance);
                    runOnUiThread(() -> {
                        Toast.makeText(amount.this, "Balance updated: " + newBalance + " Credits", Toast.LENGTH_LONG).show();
                        setResult(RESULT_OK); // Set result for the BankingActivity
                        finish(); // Close this activity and return
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(amount.this, "User not found", Toast.LENGTH_SHORT).show());
                }
            } else {
                runOnUiThread(() -> Toast.makeText(amount.this, "No user logged in", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }


//    private EditText amountEntered;
//    private Button addButton;
//    private UserDAO userDAO;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_amount);
//
//        amountEntered = findViewById(R.id.amountEntered);
//        addButton = findViewById(R.id.add);
//        userDAO = UserDatabase.getDatabase(this).UserDAO();
//
//        addButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    int amount = Integer.parseInt(amountEntered.getText().toString());
//                    updateBalance(amount);
//                    Intent intent = new Intent(amount.this, BankingActivity.class);
//                    startActivity(intent);
//                } catch (NumberFormatException e) {
//                    Toast.makeText(amount.this, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//
//    private void updateBalance(int amount) {
//        new Thread(() -> {
//            String username = getSharedPreferences("LoginActivity", MODE_PRIVATE)
//                    .getString("enteredUsername", null);
//            if (username != null) {
//                User user = userDAO.getUserByName(username);
//                if (user != null) {
//                    int newBalance = user.getBalance() - amount;
//                    userDAO.updateBalance(username, newBalance);
//                    runOnUiThread(() -> {
//                        Toast.makeText(amount.this, "Balance updated: " + newBalance + " Credits", Toast.LENGTH_LONG).show();
//                        finish();  // Optionally close the activity
//                    });
//                } else {
//                    runOnUiThread(() -> Toast.makeText(amount.this, "User not found", Toast.LENGTH_SHORT).show());
//                }
//            } else {
//                runOnUiThread(() -> Toast.makeText(amount.this, "No user logged in", Toast.LENGTH_SHORT).show());
//            }
//        }).start();
//    }
}