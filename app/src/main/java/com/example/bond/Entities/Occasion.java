package com.example.bond.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "occasion",
        foreignKeys = @ForeignKey(
                entity = User.class,
                parentColumns = "occasionID",
                childColumns = "userID",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index("userID")}
)
public class Occasion {
    @PrimaryKey(autoGenerate = true)
    private int occasionID;

    @NonNull
    private String occasionTitle;
    private String description;
    private String occasionDate;
    private String occasionLocation;
    private int userID;

    public Occasion(int occasionID, @NonNull String occasionTitle, String description, String occasionDate, String occasionLocation, int userID) {
        this.occasionID = occasionID;
        this.occasionTitle = occasionTitle;
        this.description = description;
        this.occasionDate = occasionDate;
        this.occasionLocation = occasionLocation;
        this.userID = userID;
    }

    public int getOccasionID() {
        return occasionID;
    }

    public void setOccasionID(int occasionID) {
        this.occasionID = occasionID;
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

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
