package com.example.bond.Entities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "friend", indices = {@Index(value = "friendUserName")})
public class Friend {
    //required fields
    @PrimaryKey(autoGenerate = true)
    private int friendID;
    @NonNull
    private String friendUserName;

    @NonNull
    private String friendName;

    //optional fields
    @Nullable
    private String profilePictureURL;
    @Nullable
    private String birthday;
    @Nullable
    private String favoriteColor;
    @Nullable
    private String allergies;
    @Nullable
    private String dietaryRestrictions;
    @Nullable
    private String favoriteFood;
    @Nullable
    private String hobbies;
    @Nullable
    private String currentJob;
    @Nullable
    private String petName;
    @Nullable
    private String partnerName;
    @Nullable
    private String interests;

    //indicates whether friend is created or added
    private boolean hasAccount;

    public Friend(int friendID, @NonNull String friendUserName, @NonNull String friendName, @Nullable String profilePictureURL,
                  @Nullable String birthday, @Nullable String favoriteColor,
                  @Nullable String allergies, @Nullable String dietaryRestrictions,
                  @Nullable String favoriteFood, @Nullable String hobbies,
                  @Nullable String currentJob, @Nullable String petName,
                  @Nullable String partnerName, @Nullable String interests, boolean hasAccount) {
        this.friendID = friendID;
        this.friendUserName = friendUserName;
        this.friendName = friendName;
        this.profilePictureURL = profilePictureURL;
        this.birthday = birthday;
        this.favoriteColor = favoriteColor;
        this.allergies = allergies;
        this.dietaryRestrictions = dietaryRestrictions;
        this.favoriteFood = favoriteFood;
        this.hobbies = hobbies;
        this.currentJob = currentJob;
        this.petName = petName;
        this.partnerName = partnerName;
        this.interests = interests;
        this.hasAccount = hasAccount;
    }

    public int getFriendID() {
        return friendID;
    }

    public void setFriendID(int friendID) {
        this.friendID = friendID;
    }

    @NonNull
    public String getFriendUserName() {
        return friendUserName;
    }

    public void setFriendUserName(@NonNull String friendUserName) {
        this.friendUserName = friendUserName;
    }

    @NonNull
    public String getFriendName(){
        return friendName;
    }

    public void setFriendName(@NonNull String friendName) {
        this.friendName = friendName;
    }

    @Nullable
    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    public void setProfilePictureURL(@Nullable String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }

    @Nullable
    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(@Nullable String birthday) {
        this.birthday = birthday;
    }

    @Nullable
    public String getFavoriteColor() {
        return favoriteColor;
    }

    public void setFavoriteColor(@Nullable String favoriteColor) {
        this.favoriteColor = favoriteColor;
    }

    @Nullable
    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(@Nullable String allergies) {
        this.allergies = allergies;
    }

    @Nullable
    public String getDietaryRestrictions() {
        return dietaryRestrictions;
    }

    public void setDietaryRestrictions(@Nullable String dietaryRestrictions) {
        this.dietaryRestrictions = dietaryRestrictions;
    }

    @Nullable
    public String getFavoriteFood() {
        return favoriteFood;
    }

    public void setFavoriteFood(@Nullable String favoriteFood) {
        this.favoriteFood = favoriteFood;
    }

    @Nullable
    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(@Nullable String hobbies) {
        this.hobbies = hobbies;
    }

    @Nullable
    public String getCurrentJob() {
        return currentJob;
    }

    public void setCurrentJob(@Nullable String currentJob) {
        this.currentJob = currentJob;
    }

    @Nullable
    public String getPetName() {
        return petName;
    }

    public void setPetName(@Nullable String petName) {
        this.petName = petName;
    }

    @Nullable
    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(@Nullable String partnerName) {
        this.partnerName = partnerName;
    }

    @Nullable
    public String getInterests() {
        return interests;
    }

    public void setInterests(@Nullable String interests) {
        this.interests = interests;
    }

    public boolean isHasAccount() {
        return hasAccount;
    }

    public void setHasAccount(boolean hasAccount) {
        this.hasAccount = hasAccount;
    }
}
