package com.example.bond.Relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.bond.Entities.Friend;
import com.example.bond.Entities.FriendCustomField;

import java.util.List;

public class FriendWithCustomField {
    @Embedded
    public Friend friend;

    @Relation(parentColumn = "friendID", entityColumn = "friendOwnerID")
    public List<FriendCustomField> customfield;
}
