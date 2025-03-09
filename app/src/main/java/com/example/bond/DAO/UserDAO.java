package com.example.bond.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bond.Entities.User;

@Dao
public interface UserDAO {
    @Insert
    long insertUser(User user);

    @Update
    int updateUser(User user);

    @Delete
    int deleteUser(User user);

    @Query("SELECT * FROM user WHERE userID = :userID")
    User getUserByID(int userID);

    @Query("SELECT * FROM user WHERE userName = :userName")
    User getUserByUserName(String userName);

    @Query("SELECT * FROM user WHERE emailAddress = :emailAddress")
    User getUserByEmailAddress(String emailAddress);
}
