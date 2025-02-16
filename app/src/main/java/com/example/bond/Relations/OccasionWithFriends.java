package com.example.bond.Relations;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.bond.Entities.Occasion;
import com.example.bond.Entities.OccasionFriendCrossRef;

import java.util.List;

public class OccasionWithFriends {
    @Embedded
    public Occasion occasion;

    @Relation(
            parentColumn = "occasionID",
            entityColumn = "friendID",
            associateBy = @Junction(OccasionFriendCrossRef.class)
    )

    public List<FriendWithCustomField> friend;
}
