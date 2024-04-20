package com.example.moulaproject.Database;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.moulaproject.Database.entities.User;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Query("Select * from " + UserDatabase.USER_TABLE)
    List<User> getAllRecords();
}
