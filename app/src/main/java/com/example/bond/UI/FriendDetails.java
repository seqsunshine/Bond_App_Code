package com.example.bond.UI;

import android.os.Bundle;
import android.telecom.Call;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bond.Adapters.FriendDetailsAdapter;
import com.example.bond.Models.Detail;
import com.example.bond.R;

import java.util.ArrayList;
import java.util.List;

public class FriendDetails extends AppCompatActivity {

    private RecyclerView friendDetailsRecycler;
    private ImageView friendDetailsProfilePicture;
    private ImageView friendDetailsLogo;
    private FriendDetailsAdapter friendDetailsAdapter;
    private List<Detail> detailList; //might want to change the name of this??

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
        //connect xml components to file
        friendDetailsRecycler = findViewById(R.id.friend_details_recycler);
        friendDetailsProfilePicture = findViewById(R.id.friend_details_profile_picture);
        friendDetailsLogo = findViewById(R.id.friend_details_logo);

        //set up recycler
        friendDetailsRecycler.setLayoutManager(new LinearLayoutManager(this));

        //initialize detail list
        detailList = new ArrayList<>();
        //need to add logic to import detail list!!! NOT FINISHED

        //set up adapter
        friendDetailsAdapter = new FriendDetailsAdapter(this, detailList);
        friendDetailsRecycler.setAdapter(friendDetailsAdapter);

        //NEED TO ADD LOGIC TO IMPORT FRIEND PROFILE PIC!!! tbd on how...
    }
}