package com.example.bond.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "occasion",
        foreignKeys = @ForeignKey(
                entity = User.class,
                parentColumns = "userID",
                childColumns = "ownerUserID",
                onDelete = ForeignKey.CASCADE
        ))
public class Occasion {
    @PrimaryKey(autoGenerate = true)
    private int occasionID;

    private int ownerUserID;

    @NonNull
    private String occasionTitle;
    private String description;
    private String occasionDate;
    private String occasionLocation;
    private String dateCreated;

    private String friendPreferences;
    private String friendIDs;

    private int userID;

    public Occasion(int occasionID, int ownerUserID, @NonNull String occasionTitle,
                    String description, String occasionDate, String occasionLocation,
                    String dateCreated, String friendPreferences, String friendIDs, int userID) {
        this.occasionID = occasionID;
        this.ownerUserID = ownerUserID;
        this.occasionTitle = occasionTitle;
        this.description = description;
        this.occasionDate = occasionDate;
        this.occasionLocation = occasionLocation;
        this.dateCreated = dateCreated;
        this.friendPreferences = friendPreferences;
        this.friendIDs = friendIDs;
        this.userID = userID;
    }

    public int getOccasionID() {
        return occasionID;
    }

    public void setOccasionID(int occasionID) {
        this.occasionID = occasionID;
    }

    public int getOwnerUserID() {
        return ownerUserID;
    }

    public void setOwnerUserID(int ownerUserID) {
        this.ownerUserID = ownerUserID;
    }

    @NonNull
    public String getOccasionTitle() {
        return occasionTitle;
    }

    public void setOccasionTitle(@NonNull String occasionTitle) {
        this.occasionTitle = occasionTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOccasionDate() {
        return occasionDate;
    }

    public void setOccasionDate(String occasionDate) {
        this.occasionDate = occasionDate;
    }

    public String getOccasionLocation() {
        return occasionLocation;
    }

    public void setOccasionLocation(String occasionLocation) {
        this.occasionLocation = occasionLocation;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getFriendPreferences() {
        return friendPreferences;
    }

    public void setFriendPreferences(String friendPreferences) {
        this.friendPreferences = friendPreferences;
    }

    public String getFriendIDs() {
        return friendIDs;
    }

    public void setFriendIDs(String friendIDs) {
        this.friendIDs = friendIDs;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Ignore
    public Occasion(int occasionID, int ownerUserID, @NonNull String occasionTitle, String description,
                    String occasionDate, String occasionLocation, String dateCreated, int userID) {
        this(occasionID, ownerUserID, occasionTitle,description, occasionDate, occasionLocation, dateCreated, null, null, userID);
    }
}
