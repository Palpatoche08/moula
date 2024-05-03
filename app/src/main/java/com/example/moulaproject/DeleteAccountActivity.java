
package com.example.moulaproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moulaproject.Database.UserRepo;
import com.example.moulaproject.Database.entities.User;

public class DeleteAccountActivity extends AppCompatActivity {

    private UserRepo userRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);

        userRepo = new UserRepo(getApplication());

        EditText deleteUsernameEditText = findViewById(R.id.deleteUsername);
        Button deleteButton = findViewById(R.id.user_remove);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = deleteUsernameEditText.getText().toString().trim();
                if(deleteUser(username))
                {
                    Intent intent = new Intent(DeleteAccountActivity.this, AdminActivity.class);
                    startActivity(intent);
                }
            }
        });

        Button goback_button = findViewById(R.id.goback);
        goback_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeleteAccountActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean deleteUser(String username) {
        User user = userRepo.getUserByName(username);
        if (user != null) {
            userRepo.deleteUser(user);
            Toast.makeText(this, "User " + username + " has been deleted", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(this, "User " + username + " does not exist", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}