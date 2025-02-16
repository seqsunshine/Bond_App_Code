package com.example.bond.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.bond.Entities.Friend;
import com.example.bond.Relations.FriendWithCustomField;

import java.util.List;

@Dao
public interface FriendDAO {
    @Insert
    long insertFriend(Friend friend);

    @Update
    int updateFriend(Friend friend);

    @Delete
    int deleteFriend(Friend friend);

    @Query("SELECT * FROM friend")
    List<Friend> getAllFriends();

    //gets all of a specific friends' fields
    @Transaction
    @Query("SELECT * FROM friend WHERE friendID = :friendID")
    FriendWithCustomField getFriendWithCustomField(int friendID);
}
