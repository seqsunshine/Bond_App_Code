package com.example.bond.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.bond.Entities.Friend;
import com.example.bond.Entities.User;
import com.example.bond.Relations.FriendWithCustomField;

import java.util.List;

@Dao
public interface FriendDAO {
   @Insert
    long insertFriend(Friend friend);

   @Query("SELECT * FROM friend WHERE ownerUserID = :ownerID")
    List<Friend> getFriendsByOwnerID(int ownerID);

   @Delete
    int deleteFriend(Friend friend);

   @Update
    int updateFriend(Friend friend);

   @Query("SELECT user.* FROM user INNER JOIN friend ON user.userID = friend.friendUserID WHERE friend.ownerUserID = :ownerID")
    List<User> getFriendUsersForOwner(int ownerID);

   @Query("SELECT user.* FROM user INNER JOIN friend ON user.userID = friend.friendUserID WHERE friend.ownerUserID = :ownerID AND user.username LIKE '%' || :query || '%'")
    List<User> searchFriendUsersForOwner(int ownerID, String query);
}
