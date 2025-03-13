package com.example.bond.UI;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bond.Adapters.FriendAdapter;
import com.example.bond.DAO.FriendDAO;
import com.example.bond.Database.BondAppDatabase;
import com.example.bond.Entities.Friend;
import com.example.bond.Entities.User;
import com.example.bond.R;

import java.util.List;

public class FriendList extends AppCompatActivity {

    private BondAppDatabase db;
    private FriendDAO friendDAO;
    private List<User> friendList;
    private RecyclerView recyclerView;
    private FriendAdapter friendAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_friend_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.friend_list), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //set up backwards navigation
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        int currentUserID = getSharedPreferences("my_app_prefs", MODE_PRIVATE).getInt("current_user_id", -1);

        //initialize recycler view
        recyclerView = findViewById(R.id.my_friends_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //get database instance
        db = BondAppDatabase.getDatabase(getApplicationContext());
        friendDAO = db.friendDAO();

        //load friends
        loadFriends(currentUserID);
    }

    private void loadFriends(int currentUserID) {
        BondAppDatabase.databaseWriteExecutor.execute(() -> {
            friendList = friendDAO.getFriendUsersForOwner(currentUserID);
            runOnUiThread(() -> {
                friendAdapter = new FriendAdapter(FriendList.this, friendList);
                recyclerView.setAdapter(friendAdapter);
            });
        });
    }
}