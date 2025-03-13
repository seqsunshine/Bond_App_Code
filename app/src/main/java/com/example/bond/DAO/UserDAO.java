package com.example.bond.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bond.Entities.User;
import com.example.bond.Models.Preference;

import java.util.List;

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

    @Query("SELECT * FROM user WHERE userName LIKE '%' || :query || '%'")
    List<User> searchUsers(String query);

    @Query(
            "SELECT 'Birthday' AS name, '' AS description FROM user " +
            "WHERE userID = :userID AND birthday LIKE '%' || :query || '%' " +
            "UNION " +
            "SELECT 'Favorite Color' AS name, '' AS description FROM user " +
            "WHERE userID = :userID AND favoriteColor LIKE '%' || :query || '%'" +
            "UNION " +
            "SELECT 'Allergies' AS name, '' AS description FROM user " +
            "WHERE userID = :userID AND allergies LIKE '%' || :query || '%'" +
            "UNION " +
            "SELECT 'Dietary Restrictions' AS name, '' AS description FROM user " +
            "WHERE userID = :userID AND dietaryRestrictions LIKE '%' || :query || '%'" +
            "UNION " +
            "SELECT 'Favorite Food' AS name, '' AS description FROM user " +
            "WHERE userID = :userID AND favoriteFood LIKE '%' || :query || '%'" +
            "UNION " +
            "SELECT 'Hobbies' AS name, '' AS description FROM user " +
            "WHERE userID = :userID AND hobbies LIKE '%' || :query || '%'" +
            "UNION " +
            "SELECT 'Current Job' AS name, '' AS description FROM user " +
            "WHERE userID = :userID AND currentJob LIKE '%' || :query || '%'" +
            "UNION " +
            "SELECT 'Pet Name' AS name, '' AS description FROM user " +
            "WHERE userID = :userID AND petName LIKE '%' || :query || '%'" +
            "UNION " +
            "SELECT 'Partner Name' AS name, '' AS description FROM user " +
            "WHERE userID = :userID AND partnerName LIKE '%' || :query || '%'" +
            "UNION " +
            "SELECT 'Interests' AS name, '' AS description FROM user " +
            "WHERE userID = :userID AND interests LIKE '%' || :query || '%'"
    )
    List<Preference> searchUserPreferences(int userID, String query);
}
