package com.example.bond.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bond.DAO.UserDAO;
import com.example.bond.Database.BondAppDatabase;
import com.example.bond.Entities.User;
import com.example.bond.R;

public class UserDashboard extends AppCompatActivity {
    private TextView dashboardUsersName;
    private Button editProfileButton;
    private Button addFriendsButton;
    private Button createFriendButton;
    private Button myOccasionsButton;
    private Button myFriendsButton;
    private Button createOccasionButton;

    private BondAppDatabase db;
    private UserDAO userDAO;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.user_dashboard), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //set up backwards navigation
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //connect xml components to file
        dashboardUsersName = findViewById(R.id.dashboard_users_name);
        editProfileButton = findViewById(R.id.dashboard_edit_profile_button);
        addFriendsButton = findViewById(R.id.dashboard_add_friends_button);
        createFriendButton = findViewById(R.id.dashboard_create_friend_button);
        myOccasionsButton = findViewById(R.id.dashboard_my_occasions_button);
        myFriendsButton = findViewById(R.id.dashboard_my_friends_button);
        createOccasionButton = findViewById(R.id.dashboard_create_occasion_button);

        //initialize database and DAO
        db = BondAppDatabase.getDatabase(getApplicationContext());
        userDAO = db.userDAO();

        // load current user
        loadCurrentUser();

        // ALSO!!!! need to add importing own profile picture stuff... tbd on how to do this

        //activates edit profile button
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDashboard.this, EditProfile.class);
                startActivity(intent);
            }
        });

        //activates add friend button
        addFriendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDashboard.this, AddFriend.class);
                startActivity(intent);
            }
        });

        //activates create friend button
        createFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDashboard.this, CreateFriend.class);
                startActivity(intent);
            }
        });

        //activates my occasions button
        myOccasionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDashboard.this, OccasionPage.class);
                startActivity(intent);
            }
        });

        //activates my friends button
        myFriendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDashboard.this, FriendList.class);
                startActivity(intent);
            }
        });

        //activates create occasion button
        createOccasionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDashboard.this, CreateOccasion.class);
                startActivity(intent);
            }
        });
    }

    private void loadCurrentUser() {
        SharedPreferences prefs = getSharedPreferences("my_app_prefs", MODE_PRIVATE);
        int userID = prefs.getInt("current_user_id", -1);
        if(userID != -1) {
            BondAppDatabase.databaseWriteExecutor.execute(() -> {
                currentUser = userDAO.getUserByID(userID);
                runOnUiThread(() -> {
                    if (currentUser != null && !TextUtils.isEmpty(currentUser.getName())) {
                        dashboardUsersName.setText(currentUser.getName());
                    } else {
                        dashboardUsersName.setText("");
                    }
                });
            });
        } else {
            Toast.makeText(this, "No user logged in", Toast.LENGTH_SHORT).show();
            runOnUiThread(() -> dashboardUsersName.setText(""));
        }
    }
}