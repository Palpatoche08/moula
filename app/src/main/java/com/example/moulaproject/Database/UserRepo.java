package com.example.moulaproject.Database;

import android.app.Application;

import com.example.moulaproject.Database.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UserRepo {

    private UserDAO userDAO;


    public UserRepo(Application application)
    {
        UserDatabase db = UserDatabase.getDatabase(application);
        this.userDAO = db.UserDAO();
    }

    public List<User> getAllLogs(){
        Future<List<User>> future = UserDatabase.databaseWriteExecutor.submit(

                new Callable<List<User>>() {
                    @Override
                    public List<User> call() throws Exception {
                        return userDAO.getAllRecords();
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

    public User getUserByName(String name){
        Future<User> user = UserDatabase.databaseWriteExecutor.submit(
                new Callable<User>(){
                    @Override
                    public User call() throws Exception{
                        return userDAO.getUserByName(name);
                    }
                }
        );
        try{
            return user.get();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
