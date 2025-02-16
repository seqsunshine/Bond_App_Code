package com.example.bond.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.bond.DAO.FriendCustomFieldDAO;
import com.example.bond.DAO.FriendDAO;
import com.example.bond.DAO.OccasionDAO;
import com.example.bond.DAO.OccasionFriendCrossRefDAO;
import com.example.bond.DAO.UserCustomFieldDAO;
import com.example.bond.DAO.UserDAO;
import com.example.bond.Entities.Friend;
import com.example.bond.Entities.FriendCustomField;
import com.example.bond.Entities.Occasion;
import com.example.bond.Entities.OccasionFriendCrossRef;
import com.example.bond.Entities.User;
import com.example.bond.Entities.UserCustomField;

@Database(entities = {User.class, Friend.class, Occasion.class, UserCustomField.class,
        FriendCustomField.class, OccasionFriendCrossRef.class}, version = 1, exportSchema = false)
public abstract class BondAppDatabase extends RoomDatabase{
    public abstract UserDAO userDAO();
    public abstract FriendDAO friendDAO();
    public abstract OccasionDAO occasionDAO();
    public abstract UserCustomFieldDAO userCustomFieldDAO();
    public abstract FriendCustomFieldDAO friendCustomFieldDAO();
    public abstract OccasionFriendCrossRefDAO occasionFriendCrossRefDAO();

    private static volatile BondAppDatabase INSTANCE;

    public static BondAppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BondAppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BondAppDatabase.class, "bond_app_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
