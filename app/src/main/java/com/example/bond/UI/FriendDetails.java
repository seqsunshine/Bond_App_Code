package com.example.bond.UI;

import android.os.Bundle;
import android.telecom.Call;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bond.Adapters.FriendDetailsAdapter;
import com.example.bond.DAO.UserDAO;
import com.example.bond.Database.BondAppDatabase;
import com.example.bond.Entities.User;
import com.example.bond.Models.Detail;
import com.example.bond.R;

import java.util.ArrayList;
import java.util.List;

public class FriendDetails extends AppCompatActivity {

    private int friendUserID;
    private User friendUser;

    private BondAppDatabase db;
    private UserDAO userDAO;

    private RecyclerView friendDetailsRecycler;
    private FriendDetailsAdapter friendDetailsAdapter;
    private List<Detail> detailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_friend_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.friend_details), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //set up backwards navigation
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //connect xml components to file
        friendDetailsRecycler = findViewById(R.id.friend_details_recycler);

        //set up recycler
        friendDetailsRecycler.setLayoutManager(new LinearLayoutManager(this));

        //initialize detail list
        detailList = new ArrayList<>();
        friendDetailsAdapter = new FriendDetailsAdapter(this, detailList);
        friendDetailsRecycler.setAdapter(friendDetailsAdapter);

        //initialize database and DAO
        db = BondAppDatabase.getDatabase(getApplicationContext());
        userDAO = db.userDAO();

        friendUserID = getIntent().getIntExtra("friendUserID", -1);
        if (friendUserID == -1) {
            Toast.makeText(this, "Error: Friend not found.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadFriendDetails(friendUserID);
    }

    private void loadFriendDetails(final int friendUserID) {
        BondAppDatabase.databaseWriteExecutor.execute(() -> {
            friendUser = userDAO.getUserByID(friendUserID);
            runOnUiThread(() -> {
                if (friendUser != null) {
                    populateDetailList(friendUser);
                } else {
                    Toast.makeText(this, "Error: Friend not found.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        });
    }
    private void populateDetailList(User friendUser) {
        detailList.clear();

        detailList.add(new Detail("Name", friendUser.getName()));
        detailList.add(new Detail("User Name", friendUser.getUserName()));
        detailList.add(new Detail("Birthday", friendUser.getBirthday()));
        detailList.add(new Detail("Favorite Color", friendUser.getFavoriteColor()));
        detailList.add(new Detail("Allergies", friendUser.getAllergies()));
        detailList.add(new Detail("Dietary Restrictions", friendUser.getDietaryRestrictions()));
        detailList.add(new Detail("Favorite Food", friendUser.getFavoriteFood()));
        detailList.add(new Detail("Hobbies", friendUser.getHobbies()));
        detailList.add(new Detail("Current Job", friendUser.getCurrentJob()));
        detailList.add(new Detail("Pet Name", friendUser.getPetName()));
        detailList.add(new Detail("Partner Name", friendUser.getPartnerName()));
        detailList.add(new Detail("Interests", friendUser.getInterests()));

        friendDetailsAdapter.notifyDataSetChanged();
    }
}

