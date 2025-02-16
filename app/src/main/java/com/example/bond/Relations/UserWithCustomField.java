package com.example.bond.Relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.bond.Entities.User;
import com.example.bond.Entities.UserCustomField;

import java.util.List;

public class UserWithCustomField {
    @Embedded
    public User user;

    @Relation(parentColumn = "ID", entityColumn = "userOwnerID")
    public List<UserCustomField> customField;
}
