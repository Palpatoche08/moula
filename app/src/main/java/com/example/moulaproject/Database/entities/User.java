package com.example.moulaproject.Database.entities;
import com.example.moulaproject.Database.UserDatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = UserDatabase.USER_TABLE)
public class User {

        @PrimaryKey(autoGenerate = true)
        private int id;
        private String name;

        private String password;

        private boolean isAdmin;

        //change
        private int balance;

        public User(String name, String password, boolean isAdmin) {
            this.name = name;
            this.password = password;
            this.isAdmin = isAdmin;
            balance = 50;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public boolean isAdmin() {
            return isAdmin;
        }

        public void setAdmin(boolean admin) {
            isAdmin = admin;
        }

        public int getBalance() {
            return balance;
        }
        public void setBalance(int balance) {
            this.balance = balance;
        }
}
