package com.example.moulaproject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moulaproject.Database.UserDAO;
import com.example.moulaproject.Database.UserDatabase;
import com.example.moulaproject.Database.entities.User;

public class AmountActivity extends AppCompatActivity {

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
                double amount = Double.parseDouble(amountEntered.getText().toString());
                updateBalance(amount);

            } catch (NumberFormatException e) {
                Toast.makeText(AmountActivity.this, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateBalance(double amount) {
        new Thread(() -> {
            String username = getSharedPreferences("LoginActivity", MODE_PRIVATE)
                    .getString("enteredUsername", null);
            if (username != null) {
                User user = userDAO.getUserByName(username);
                if (user != null) {
                    double newBalance = user.getBalance() - amount;
                    userDAO.updateBalance(username, newBalance);
                    runOnUiThread(() -> {
                        Toast.makeText(AmountActivity.this, "Balance updated: " + newBalance + " Credits", Toast.LENGTH_LONG).show();
                        setResult(RESULT_OK); // Set result for the BankingActivity
                        finish(); // Close this activity and return
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(AmountActivity.this, "User not found", Toast.LENGTH_SHORT).show());
                }
            } else {
                runOnUiThread(() -> Toast.makeText(AmountActivity.this, "No user logged in", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
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
//                    int AmountActivity = Integer.parseInt(amountEntered.getText().toString());
//                    updateBalance(AmountActivity);
//                    Intent intent = new Intent(AmountActivity.this, BankingActivity.class);
//                    startActivity(intent);
//                } catch (NumberFormatException e) {
//                    Toast.makeText(AmountActivity.this, "Please enter a valid AmountActivity", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//
//    private void updateBalance(int AmountActivity) {
//        new Thread(() -> {
//            String username = getSharedPreferences("LoginActivity", MODE_PRIVATE)
//                    .getString("enteredUsername", null);
//            if (username != null) {
//                User user = userDAO.getUserByName(username);
//                if (user != null) {
//                    int newBalance = user.getBalance() - AmountActivity;
//                    userDAO.updateBalance(username, newBalance);
//                    runOnUiThread(() -> {
//                        Toast.makeText(AmountActivity.this, "Balance updated: " + newBalance + " Credits", Toast.LENGTH_LONG).show();
//                        finish();  // Optionally close the activity
//                    });
//                } else {
//                    runOnUiThread(() -> Toast.makeText(AmountActivity.this, "User not found", Toast.LENGTH_SHORT).show());
//                }
//            } else {
//                runOnUiThread(() -> Toast.makeText(AmountActivity.this, "No user logged in", Toast.LENGTH_SHORT)