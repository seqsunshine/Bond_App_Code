package com.example.bond.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.bond.Entities.Friend;
import com.example.bond.Entities.OccasionFriendCrossRef;
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

   @Query("DELETE FROM friend WHERE ownerUserID = :ownerID AND friendUserID = :friendID")
    int deleteFriend(int ownerID, int friendID);

   @Query("SELECT * FROM friend WHERE friendID IN (SELECT friendID FROM occasion_friend_cross_ref WHERE occasionID = :occasionID)")
    List<Friend> getFriendsForOccasion(int occasionID);

   @Transaction
    default void updateOccasionFriends(int occasionID, List<Friend> friends) {
       deleteOccasionFriends(occasionID);
       for (Friend friend : friends) {
        insertOccasionFriend(new OccasionFriendCrossRef(occasionID, friend.getFriendID()));
       }
   }

   @Query("DELETE FROM occasion_friend_cross_ref WHERE occasionID = :occasionID")
    void deleteOccasionFriends(int occasionID);

   @Insert
    void insertOccasionFriend(OccasionFriendCrossRef occasionFriendCrossRef);
}
