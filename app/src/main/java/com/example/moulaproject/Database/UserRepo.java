package com.example.moulaproject.Database;

import android.app.Application;

import com.example.moulaproject.Database.entities.User;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UserRepo {

    private UserDAO userDAO;

    private ArrayList<User> allLogs;

    public UserRepo(Application application)
    {
        UserDatabase db = UserDatabase.getDatabase(application);
        this.userDAO = db.UserDAO();
        this.allLogs = (ArrayList<User>) this.userDAO.getAllRecords();
    }

    public ArrayList<User> getAllLogs(){
        Future<ArrayList<User>> future = UserDatabase.databaseWriteExecutor.submit(

                new Callable<ArrayList<User>>() {
                    @Override
                    public ArrayList<User> call() throws Exception {
                        return (ArrayList<User>) userDAO.getAllRecords();
                    }
                });
        try {
            return future.get();
        }
        catch(InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
        }

        return null;

    }

    public void insertUser(User user)
    {
        UserDatabase.databaseWriteExecutor.execute(() ->
        {
            userDAO.insert(user);
        });
    }
}
