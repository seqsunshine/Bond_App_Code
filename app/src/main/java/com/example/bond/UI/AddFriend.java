package com.example.bond.UI;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bond.Adapters.UserSearchAdapter;
import com.example.bond.DAO.FriendDAO;
import com.example.bond.DAO.UserDAO;
import com.example.bond.Database.BondAppDatabase;
import com.example.bond.Entities.Friend;
import com.example.bond.Entities.User;
import com.example.bond.R;

import java.util.ArrayList;
import java.util.List;

public class AddFriend extends AppCompatActivity {

    private RecyclerView searchFriendsRecycler;
    private EditText friendsSearchEditText;

    private List<User> filteredUserList = new ArrayList<>();
    private UserSearchAdapter userSearchAdapter;

    private UserDAO userDAO;
    private FriendDAO friendDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_friend);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.add_friend), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //set up backwards navigation
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //connect xml components
        friendsSearchEditText = findViewById(R.id.friends_search_edit_text);
        searchFriendsRecycler = findViewById(R.id.search_friends_recycler);

        //set up recycler
        searchFriendsRecycler.setLayoutManager(new LinearLayoutManager(this));

        //load from database
        BondAppDatabase db = BondAppDatabase.getDatabase(getApplicationContext());
        friendDAO = db.friendDAO();
        userDAO = db.userDAO();

        //set up adapter
        userSearchAdapter = new UserSearchAdapter(this, filteredUserList, selectedUser -> {
           addFriend(selectedUser);
        });

        searchFriendsRecycler.setAdapter(userSearchAdapter);

        //add text watcher for search bar
        friendsSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterFriends(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //filters friends based on input

    private void filterFriends(String query) {
        if (query.isEmpty()) {
            filteredUserList.clear();
            userSearchAdapter.notifyDataSetChanged();
            return;
        }

        BondAppDatabase.databaseWriteExecutor.execute(() -> {
            List<User> results = userDAO.searchUsers(query);
            runOnUiThread(() -> {
                filteredUserList.clear();
                filteredUserList.addAll(results);
                userSearchAdapter.notifyDataSetChanged();
            });
        });
    }

    private void addFriend(User selectedUser) {
        int currentUserID = getSharedPreferences("my_app_prefs", MODE_PRIVATE).getInt("current_user_id", -1);
        if (currentUserID == -1) {
            Toast.makeText(this, "Added " + selectedUser.getUserName() + " as a friend!", Toast.LENGTH_SHORT).show();
            return;
        }

        BondAppDatabase.databaseWriteExecutor.execute(() -> {
            Friend newFriendRow = new Friend(0, currentUserID, selectedUser.getUserID());
            friendDAO.insertFriend(newFriendRow);
            runOnUiThread(() -> {
                Toast.makeText(this, "Added " + selectedUser.getUserName() + " as a friend!", Toast.LENGTH_SHORT).show();
            });
        });
    }
}