package com.example.bond.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.bond.Entities.Occasion;
import com.example.bond.Relations.OccasionWithFriends;

import java.util.List;

@Dao
public interface OccasionDAO {
    @Insert
    long insertOccasion(Occasion occasion);

    @Update
    int updateOccasion(Occasion occasion);

    @Delete
    int deleteOccasion(Occasion occasion);

    @Query("SELECT * FROM occasion")
    List<Occasion> getAllOccasions();

    //gets an occasion and all associated friends with fields
    @Transaction
    @Query("SELECT * FROM occasion WHERE occasionID = :occasionID")
    OccasionWithFriends getOccasionWithFriends(int occasionID);

    @Query("SELECT * FROM occasion WHERE ownerUserID = :ownerID ORDER BY dateCreated DESC")
    List<Occasion> getOccasionsByOwner(int ownerID);
}
