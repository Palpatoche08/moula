package com.example.moulaproject.Database;

import android.app.Application;

import com.example.moulaproject.Database.entities.Currency;
import com.example.moulaproject.Database.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UserRepo {

    private UserDAO userDAO;

    private static UserRepo instance;


    public UserRepo(Application application) {
        UserDatabase db = UserDatabase.getDatabase(application);
        this.userDAO = db.UserDAO();
    }

    public static UserRepo getInstance(Application application) {
        if (instance == null) {
            synchronized (UserRepo.class) {
                if (instance == null) {
                    instance = new UserRepo(application);
                }
            }
        }
        return instance;
    }

    public List<User> getAllLogs() {
        Future<List<User>> future = UserDatabase.databaseWriteExecutor.submit(

                new Callable<List<User>>() {
                    @Override
                    public List<User> call() throws Exception {
                        return userDAO.getAllRecords();
                    }
                });
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return null;

    }

    public void insertUser(User user) {
        UserDatabase.databaseWriteExecutor.execute(() ->
        {
            userDAO.insert(user);
        });
    }

    public void deleteUser(User user) {
        UserDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.delete(user);
        });
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public User getUserByName(String name) {
        Future<User> user = UserDatabase.databaseWriteExecutor.submit(
                new Callable<User>() {
                    @Override
                    public User call() throws Exception {
                        return userDAO.getUserByName(name);
                    }
                }
        );
        try {
            return user.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void insertCurrency(Currency currency) {
        UserDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.insertCurrency(currency);
        });
    }

    public void deleteCurrency(Currency currency) {
        UserDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.deleteCurrency(currency);
        });
    }

    public List<Currency> getAllCurrencies() {
        Future<List<Currency>> future = UserDatabase.databaseWriteExecutor.submit(
                new Callable<List<Currency>>() {
                    @Override
                    public List<Currency> call() throws Exception {
                        return userDAO.getAllCurrencies();
                    }
                });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Currency getCurrencyByName(String name) {
        Future<Currency> future = UserDatabase.databaseWriteExecutor.submit(() -> userDAO.getCurrencyByName(name));

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }


}