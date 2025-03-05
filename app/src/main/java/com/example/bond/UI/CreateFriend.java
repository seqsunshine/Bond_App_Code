package com.example.bond.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bond.Adapters.FriendPreferenceAdapter;
import com.example.bond.Models.FriendPreference;
import com.example.bond.R;

import java.util.ArrayList;
import java.util.List;

public class CreateFriend extends AppCompatActivity {

    private Button addFriendDateButton;
    private Button addFriendPreferenceButton;
    private Button changePhotoButton;
    private RecyclerView friendPreferencesRecycler;
    private ImageView editFriendProfilePicture;
    private ImageView createFriendLogo;
    private TextView friendPreferencesText;

    private List<FriendPreference> friendPreferenceList;
    private FriendPreferenceAdapter friendPreferenceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_friend);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.create_friend), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //set up backwards navigation
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //set up custom backwards navigation from NewDate and NewPreference
        Intent intent = new Intent(CreateFriend.this, NewDate.class);
        intent.putExtra("origin", "createFriend");
        startActivity(intent);

        //connect xml components to file
        addFriendDateButton = findViewById(R.id.add_friend_date_button);
        addFriendPreferenceButton = findViewById(R.id.add_friend_preference_button);
        changePhotoButton = findViewById(R.id.change_photo_button2);
        friendPreferencesRecycler = findViewById(R.id.friend_preferences_recycler);
        editFriendProfilePicture = findViewById(R.id.edit_friend_profile_picture);
        createFriendLogo = findViewById(R.id.create_friend_logo);
        friendPreferencesText = findViewById(R.id.friend_preferences_text);


        //set up recycler
        friendPreferencesRecycler.setLayoutManager(new LinearLayoutManager(this));
        friendPreferenceList = new ArrayList<>();
        //could add sample data here
        friendPreferenceAdapter = new FriendPreferenceAdapter(this, friendPreferenceList);
        friendPreferencesRecycler.setAdapter(friendPreferenceAdapter);

        //activate add date button
        addFriendDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateFriend.this, NewDate.class);
                startActivity(intent);
            }
        });

        //activate add preference button
        addFriendPreferenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateFriend.this, NewPreference.class);
            }
        });

        changePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //need to add the pic photo thing... tbd
            }
        });
    }
}