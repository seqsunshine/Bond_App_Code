package com.example.bond.Entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "occasion_friend_cross_ref",
        primaryKeys = {"occasionID", "friendID"},
        foreignKeys = {
                @ForeignKey(entity = Occasion.class, parentColumns = "occasionID", childColumns = "occasionID",
                onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Friend.class, parentColumns = "friendID", childColumns = "friendID",
                onDelete = ForeignKey.CASCADE)
        },
        indices = {@Index("occasionID"), @Index("friendID")})
public class OccasionFriendCrossRef {
    public int occasionID;
    public int friendID;

    public OccasionFriendCrossRef(int occasionID, int friendID) {
        this.occasionID = occasionID;
        this.friendID = friendID;
    }
}
