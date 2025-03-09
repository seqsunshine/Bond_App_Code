package com.example.bond.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bond.Entities.UserCustomField;

import java.util.List;

@Dao
public interface UserCustomFieldDAO {
    @Insert
    long insertUserCustomField(UserCustomField userCustomField);

    @Update
    int updateUserCustomField(UserCustomField userCustomField);

    @Delete
    int deleteUserCustomField(UserCustomField userCustomField);

    @Query("SELECT * FROM user_custom_field WHERE userOwnerID = :userID")
    List<UserCustomField> getCustomFieldsForUser(int userID);

    @Query("DELETE FROM user_custom_field WHERE fieldName = :name AND userOwnerID = :userID")
    void deleteCustomFieldByNameAndUserID(String name, int userID);
}
