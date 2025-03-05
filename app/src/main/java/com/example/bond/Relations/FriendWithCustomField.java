package com.example.bond.Relations;

import androidx.room.DatabaseView;
import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.bond.Entities.Friend;
import com.example.bond.Entities.FriendCustomField;

import java.util.List;

@DatabaseView(viewName = "friend_with_custom_field_view", value = "SELECT * FROM friend")
public class FriendWithCustomField {
    @Embedded
    public Friend friend;

    @Relation(parentColumn = "friendID", entityColumn = "friendOwnerID")
    public List<FriendCustomField> customfield;
}
