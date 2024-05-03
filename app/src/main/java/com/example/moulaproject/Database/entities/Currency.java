
package com.example.moulaproject.Database.entities;

import com.example.moulaproject.Database.UserDatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = UserDatabase.CURRENCY_TABLE)
public class Currency {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private double rate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Currency(String name, double rate) {
        this.name = name;
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
