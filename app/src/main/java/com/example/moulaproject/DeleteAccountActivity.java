
package com.example.moulaproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.moulaproject.Database.UserDAO;

public class DeleteAccountActivity extends AppCompatActivity {

    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);


    }
}
