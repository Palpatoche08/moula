package com.example.moulaproject.Database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.moulaproject.Database.entities.Currency;
import com.example.moulaproject.Database.entities.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Delete
    void delete(User user);

    @Query("Select * from " + UserDatabase.USER_TABLE + " ORDER BY name ")
    List<User> getAllRecords();

    @Query("DELETE from " + UserDatabase.USER_TABLE)
    void deleteALL();

    @Query("SELECT * FROM " + UserDatabase.USER_TABLE + " WHERE name = :username")
    User getUserByName(String username);

    @Query("SELECT * FROM " + UserDatabase.USER_TABLE + " WHERE id = :id")
    User getUserByID(int id);

    @Query("SELECT isAdmin FROM " + UserDatabase.USER_TABLE + " WHERE name = :username")
    boolean isAdmin(String username);

    @Query("SELECT balance FROM " + UserDatabase.USER_TABLE + " WHERE name = :username")
    double getBalanceByUsername(String username);

    @Query("UPDATE " + UserDatabase.USER_TABLE + " SET balance = :balance WHERE name = :username")
    void updateBalance(String username, double balance);
    @Query("UPDATE " + UserDatabase.USER_TABLE + " SET balance = balance - :amount WHERE name = :username AND balance >= :amount")
    int deductAmount(String username, double amount);

    @Query("UPDATE " + UserDatabase.USER_TABLE + " SET balance = balance + :amount WHERE id = :userId")
    int addAmount(int userId, double amount);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCurrency(Currency currency);
    @Delete
    void deleteCurrency(Currency currency);

    @Query("Select * from " + UserDatabase.CURRENCY_TABLE + " ORDER BY name ")
    List<Currency> getAllCurrencies();

    @Query("Select * from " + UserDatabase.CURRENCY_TABLE +  " WHERE name = :name")
    Currency getCurrencyByName(String name);

}
