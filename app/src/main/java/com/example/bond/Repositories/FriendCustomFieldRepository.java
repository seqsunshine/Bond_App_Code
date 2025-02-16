package com.example.bond.Repositories;

import android.app.Application;

import com.example.bond.DAO.FriendCustomFieldDAO;
import com.example.bond.Database.BondAppDatabase;
import com.example.bond.Entities.Friend;
import com.example.bond.Entities.FriendCustomField;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FriendCustomFieldRepository {
    private FriendCustomFieldDAO mFriendCustomFieldDAO;

    private static int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor2 = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public FriendCustomFieldRepository(Application application){
        BondAppDatabase db = BondAppDatabase.getDatabase(application);
        mFriendCustomFieldDAO = db.friendCustomFieldDAO();
    }

    public void insertFriendCustomField(FriendCustomField friendCustomField){
        databaseExecutor2.execute(()->{
            mFriendCustomFieldDAO.insertFriendCustomField(friendCustomField);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(FriendCustomField friendCustomField){
        databaseExecutor2.execute(()->{
            mFriendCustomFieldDAO.updateFriendCustomField(friendCustomField);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(FriendCustomField friendCustomField){
        databaseExecutor2.execute(()->{
            mFriendCustomFieldDAO.deleteFriendCustomField(friendCustomField);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
