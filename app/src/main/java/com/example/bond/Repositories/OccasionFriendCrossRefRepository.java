package com.example.bond.Repositories;

import android.app.Application;

import com.example.bond.DAO.OccasionDAO;
import com.example.bond.DAO.OccasionFriendCrossRefDAO;
import com.example.bond.Database.BondAppDatabase;
import com.example.bond.Entities.Occasion;
import com.example.bond.Entities.OccasionFriendCrossRef;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OccasionFriendCrossRefRepository {
    private OccasionFriendCrossRefDAO mOccasionFriendCrossRefDAO;

    private static int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor4 = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public void OccasionFriendCrossRefDAO(Application application){
        BondAppDatabase db = BondAppDatabase.getDatabase(application);
        mOccasionFriendCrossRefDAO = db.occasionFriendCrossRefDAO();
    }

    public void insertOccasionFriendCrossRef(OccasionFriendCrossRef occasionFriendCrossRef){
        databaseExecutor4.execute(()->{
            mOccasionFriendCrossRefDAO.insertOccasionFriendCrossRef(occasionFriendCrossRef);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
