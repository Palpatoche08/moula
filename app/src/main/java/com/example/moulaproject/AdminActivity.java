
package com.example.moulaproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.moulaproject.Database.UserRepo;
import com.example.moulaproject.Database.entities.User;

import java.util.List;

public class AdminActivity extends AppCompatActivity {

    private UserRepo db;
    private TextView listUserTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        this.db = new UserRepo(getApplication());
        listUserTextView = findViewById(R.id.listUser);

        StringBuilder list = new StringBuilder();

        List<User> users = db.getAllLogs();
        for(User u: users){
            list.append(u.getName()).append("\n");
        }
        listUserTextView.setText(list.toString());

        Button userRemove = findViewById(R.id.user_remove);
        userRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, DeleteAccountActivity.class);
                startActivity(intent);
            }

        });
    }


}
