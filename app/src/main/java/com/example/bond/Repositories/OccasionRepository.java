package com.example.bond.Repositories;

import android.app.Application;

import com.example.bond.DAO.FriendCustomFieldDAO;
import com.example.bond.DAO.OccasionDAO;
import com.example.bond.Database.BondAppDatabase;
import com.example.bond.Entities.FriendCustomField;
import com.example.bond.Entities.Occasion;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OccasionRepository {
    private OccasionDAO mOccasionDAO;

    private static int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor3 = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public OccasionRepository(Application application){
        BondAppDatabase db = BondAppDatabase.getDatabase(application);
        mOccasionDAO = db.occasionDAO();
    }

    public void insertOccasion(Occasion occasion){
        databaseExecutor3.execute(()->{
            mOccasionDAO.insertOccasion(occasion);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Occasion occasion){
        databaseExecutor3.execute(()->{
            mOccasionDAO.updateOccasion(occasion);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Occasion occasion){
        databaseExecutor3.execute(()->{
            mOccasionDAO.deleteOccasion(occasion);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
