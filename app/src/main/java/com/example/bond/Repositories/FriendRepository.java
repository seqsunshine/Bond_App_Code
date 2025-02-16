package com.example.bond.Repositories;

import android.app.Application;

import com.example.bond.DAO.FriendDAO;
import com.example.bond.Database.BondAppDatabase;
import com.example.bond.Entities.Friend;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FriendRepository {
    private FriendDAO mFriendDAO;
    private List<Friend> mAllFriends;

    private static int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor1 = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public FriendRepository(Application application) {
        BondAppDatabase db = BondAppDatabase.getDatabase(application);
        mFriendDAO = db.friendDAO();
    }

    public List<Friend>getmAllFriends(){
        databaseExecutor1.execute(()->{
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllFriends;
    }

    public void insert(Friend friend){
        databaseExecutor1.execute(()->{
            mFriendDAO.insertFriend(friend);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Friend friend){
        databaseExecutor1.execute(()->{
            mFriendDAO.updateFriend(friend);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Friend friend){
        databaseExecutor1.execute(()->{
            mFriendDAO.deleteFriend(friend);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Friend getFriendByID(int friendID) {
        for (Friend f: getmAllFriends()){
            if(f.getFriendID() == friendID) {
                return f;
            }
        }
        return null;
    }
}
