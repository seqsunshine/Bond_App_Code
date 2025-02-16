package com.example.bond.Entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_custom_field",
        foreignKeys = @ForeignKey(
                entity = User.class,
                parentColumns = "ID",
                childColumns = "userOwnerID",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index("userOwnerID")})
public class UserCustomField {
    @PrimaryKey(autoGenerate = true)
    private int ID;
    private int userOwnerID;
    private String fieldName;
    private String fieldValue;

    public UserCustomField(int ID, int userOwnerID, String fieldName, String fieldValue) {
        this.ID = ID;
        this.userOwnerID = userOwnerID;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getUserOwnerID() {
        return userOwnerID;
    }

    public void setUserOwnerID(int userOwnerID) {
        this.userOwnerID = userOwnerID;
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
