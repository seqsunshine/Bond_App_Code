package com.example.bond.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bond.Entities.FriendCustomField;

import java.util.List;

@Dao
public interface FriendCustomFieldDAO {
    @Insert
    long insertFriendCustomField(FriendCustomField friendCustomField);

    @Update
    int updateFriendCustomField(FriendCustomField friendCustomField);

    @Delete
    int deleteFriendCustomField(FriendCustomField friendCustomField);

    @Query("SELECT * FROM friend_custom_field WHERE friendOwnerID = :friendID")
    List<FriendCustomField> getCustomFieldsForFriend(int friendID);
}
