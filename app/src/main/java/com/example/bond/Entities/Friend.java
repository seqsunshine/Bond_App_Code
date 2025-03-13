package com.example.bond.Entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "friend")
public class Friend {
    @PrimaryKey(autoGenerate = true)
    private int friendID;

    //the user who owns the friend
    private int ownerUserID;

    //the user being added as a friend
    private int friendUserID;

    public Friend(int friendID, int ownerUserID, int friendUserID) {
        this.friendID = friendID;
        this.ownerUserID = ownerUserID;
        this.friendUserID = friendUserID;
    }

    @Ignore
    public Friend(int ownerID, int friendUserID) {
        this.friendID = 0;
        this.ownerUserID = ownerID;
        this.friendUserID = friendUserID;
    }

    public int getFriendID() {
        return friendID;
    }

    public void setFriendID(int friendID) {
        this.friendID = friendID;
    }

    public int getOwnerUserID() {
        return ownerUserID;
    }

    public void setOwnerUserID(int ownerUserID) {
        this.ownerUserID = ownerUserID;
    }

    public int getFriendUserID() {
        return friendUserID;
    }

    public void setFriendUserID(int friendUserID) {
        this.friendUserID = friendUserID;
    }

}
