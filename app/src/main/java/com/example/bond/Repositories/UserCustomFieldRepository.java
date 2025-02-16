package com.example.bond.Repositories;

import android.app.Application;

import com.example.bond.DAO.OccasionDAO;
import com.example.bond.DAO.UserCustomFieldDAO;
import com.example.bond.Database.BondAppDatabase;
import com.example.bond.Entities.Occasion;
import com.example.bond.Entities.UserCustomField;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserCustomFieldRepository {
    private UserCustomFieldDAO mUserCustomFieldDAO;

    private static int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor6 = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public UserCustomFieldRepository(Application application){
        BondAppDatabase db = BondAppDatabase.getDatabase(application);
        mUserCustomFieldDAO = db.userCustomFieldDAO();
    }

    public void insertUserCustomField(UserCustomField userCustomField){
        databaseExecutor6.execute(()->{
            mUserCustomFieldDAO.insertUserCustomField(userCustomField);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(UserCustomField userCustomField){
        databaseExecutor6.execute(()->{
            mUserCustomFieldDAO.updateUserCustomField(userCustomField);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(UserCustomField userCustomField){
        databaseExecutor6.execute(()->{
            mUserCustomFieldDAO.deleteUserCustomField(userCustomField);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
