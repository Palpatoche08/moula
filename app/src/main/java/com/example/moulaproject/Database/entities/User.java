package com.example.moulaproject.Database.entities;
import com.example.moulaproject.Database.UserDatabase;
import com.example.moulaproject.Database.typeConverties.LocalDateTypeConverter;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import java.time.LocalDateTime;

//@TypeConverter(LocalDateTypeConverter.class)
@Entity(tableName = UserDatabase.USER_TABLE)
public class User {

        @PrimaryKey(autoGenerate = true)
        private int id;
        private String userId;

        private String password;

        private boolean isAdmin;

        private LocalDateTime date;

        public User(String userId, String password, boolean isAdmin) {
            this.userId = userId;
            this.password = password;
            this.isAdmin = isAdmin;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
