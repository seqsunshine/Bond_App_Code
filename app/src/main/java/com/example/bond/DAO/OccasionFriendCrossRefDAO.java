package com.example.bond.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.bond.Entities.OccasionFriendCrossRef;

import java.util.List;

@Dao
public interface OccasionFriendCrossRefDAO {
    @Insert
    void insertOccasionFriendCrossRef(OccasionFriendCrossRef crossRef);

    @Query("SELECT * FROM occasion_friend_cross_ref WHERE occasionID = :occasionID")
    List<OccasionFriendCrossRef> getFriendsForOccasion(int occasionID);
}
