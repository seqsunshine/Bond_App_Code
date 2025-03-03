package com.example.bond.UI;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bond.Adapters.FriendSearchAdapter;
import com.example.bond.Entities.Friend;
import com.example.bond.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AddFriend extends AppCompatActivity {

    private RecyclerView searchFriendsRecycler;
    private EditText friendsSearchEditText;
    private TextView searchForFriendsText;
    private TextView friendsSearchText;

    private FriendSearchAdapter friendSearchAdapter;
    private List<Friend> friendList;
    private List<Friend> filteredFriendList;

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

        searchForFriendsText = findViewById(R.id.search_for_friends_text);
        friendsSearchText = findViewById(R.id.friends_search_text);
        friendsSearchEditText = findViewById(R.id.friends_search_edit_text);
        searchFriendsRecycler = findViewById(R.id.search_friends_recycler);

        //set up recycler
        searchFriendsRecycler.setLayoutManager(new LinearLayoutManager(this));

        //initialize friend list
        friendList = new ArrayList<>();
        //ADD LOGIC TO IMPORT EXISTING FRIENDS HERE not done!!!!!

        //set up adapter
        friendSearchAdapter = new FriendSearchAdapter(this, filteredFriendList);
        searchFriendsRecycler.setAdapter(friendSearchAdapter);

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
        filteredFriendList.clear();
        if(query.isEmpty()) {
            filteredFriendList.addAll(friendList);
        }
        else {
            for (Friend friend : friendList) {
                if (friend.getFriendUserName().toLowerCase().contains(query.toLowerCase())) {
                    filteredFriendList.add(friend);
                }
            }
        }
        friendSearchAdapter.notifyDataSetChanged();
    }
}