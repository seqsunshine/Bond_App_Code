package com.example.bond.Entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "friend_custom_field",
        foreignKeys = @ForeignKey(
            entity = Friend.class,
            parentColumns = "friendID",
            childColumns = "friendOwnerID",
            onDelete = ForeignKey.CASCADE),
        indices = {@Index("friendOwnerID")})
public class FriendCustomField {

    @PrimaryKey(autoGenerate = true)
    private int ID;
    private int friendOwnerID;
    private String fieldName;
    private String fieldValue;

    public FriendCustomField(int ID, int friendOwnerID, String fieldName, String fieldValue) {
        this.ID = ID;
        this.friendOwnerID = friendOwnerID;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getFriendOwnerID() {
        return friendOwnerID;
    }

    public void setFriendOwnerID(int friendOwnerID) {
        this.friendOwnerID = friendOwnerID;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }
}
