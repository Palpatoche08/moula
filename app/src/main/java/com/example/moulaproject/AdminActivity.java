package com.example.moulaproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.moulaproject.Database.UserRepo;
import com.example.moulaproject.Database.entities.User;

import java.util.List;

public class AdminActivity extends AppCompatActivity {

    private UserRepo db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        this.db = new UserRepo(getApplication());
        StringBuilder list = new StringBuilder();

        List<User> users = db.getAllLogs();
        for(User u: users){
            list.append(u.getName()).append("\n");
        }

    }
}