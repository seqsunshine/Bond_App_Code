package com.example.bond.Repositories;

import android.app.Application;

import com.example.bond.DAO.OccasionDAO;
import com.example.bond.DAO.UserDAO;
import com.example.bond.Database.BondAppDatabase;
import com.example.bond.Entities.Occasion;
import com.example.bond.Entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {
    private UserDAO mUserDAO;

    private static int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor5 = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public UserRepository(Application application){
        BondAppDatabase db = BondAppDatabase.getDatabase(application);
        mUserDAO = db.userDAO();
    }

    public void insertUser(User user){
        databaseExecutor5.execute(()->{
            mUserDAO.insertUser(user);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(User user){
        databaseExecutor5.execute(()->{
            mUserDAO.updateUser(user);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(User user){
        databaseExecutor5.execute(()->{
            mUserDAO.deleteUser(user);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
