package com.example.bond.UI;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bond.Adapters.OccasionFriendAdapter;
import com.example.bond.Adapters.OccasionPreferenceAdapter;
import com.example.bond.Entities.Friend;
import com.example.bond.Entities.Occasion;
import com.example.bond.Models.Preference;
import com.example.bond.R;

import java.util.ArrayList;
import java.util.List;

public class CreateOccasion extends AppCompatActivity {
    //event details
    private EditText occasionTitleEditText;
    private EditText occasionDateEditText;
    private EditText occasionLocationEditText;
    private EditText occasionDescriptionEditText;

    //search fields
    private EditText occasionSearchFriendsEditText;
    private EditText occasionSearchPreferencesEditText;

    //recycler views
    private RecyclerView occasionSelectFriendsRecycler;
    private RecyclerView occasionSelectPreferencesRecycler;

    //button
    private Button occasionCreateOccasionButton;

    //friend data related
    private List<Friend> friendList;
    private List<Friend> filteredFriendList;
    private OccasionFriendAdapter occasionFriendAdapter;

    //preference data related
    private List<Preference> preferenceList;
    private List<Preference> filteredPreferenceList;
    private OccasionPreferenceAdapter occasionPreferenceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_occasion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.create_occasion), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //set up backwards navigation
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //occasion detail fields
        occasionTitleEditText = findViewById(R.id.occasion_title_edit_text);
        occasionDateEditText = findViewById(R.id.occasion_date_edit_text);
        occasionLocationEditText = findViewById(R.id.occasion_location_edit_text);
        occasionDescriptionEditText = findViewById(R.id.occasion_description_edit_text);

        //search fields
        occasionSearchFriendsEditText = findViewById(R.id.occasion_search_friends_edit_text);
        occasionSearchPreferencesEditText = findViewById(R.id.occasion_search_preferences_edit_text);

        //recyclers
        occasionSelectFriendsRecycler = findViewById(R.id.occasion_select_friends_recycler);
        occasionSelectPreferencesRecycler = findViewById(R.id.occasion_select_preferences_recycler);

        //button
        occasionCreateOccasionButton = findViewById(R.id.occasion_create_occasion_button);

        //set up recycler views
        LinearLayoutManager friendsLayoutManager = new LinearLayoutManager(this, LinearLayoutManager. HORIZONTAL, false);
        occasionSelectFriendsRecycler.setLayoutManager(friendsLayoutManager);

        LinearLayoutManager preferencesLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        occasionSelectPreferencesRecycler.setLayoutManager(preferencesLayoutManager);

        //initialize friend list
        friendList = new ArrayList<>();
        filteredFriendList = new ArrayList<>(friendList);
        //need to add info to filter in data. NOT DONE

        //initialize occasion friend attached to recycler
        occasionFriendAdapter = new OccasionFriendAdapter(this, filteredFriendList);
        occasionSelectFriendsRecycler.setAdapter(occasionFriendAdapter);

        //initialize preference list
        preferenceList = new ArrayList<>();
        filteredPreferenceList = new ArrayList<>(preferenceList);
        //need to add info to filter in data. NOT DONE

        //initialize occasion preference attached to recycler
        occasionPreferenceAdapter = new OccasionPreferenceAdapter(this, filteredPreferenceList);

        //set up friend search
        occasionSearchFriendsEditText.addTextChangedListener(new TextWatcher() {
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

        //set up preferences search
        occasionSearchPreferencesEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterPreferences(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //activate create occasion button
        occasionCreateOccasionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                createOccasion();
            }
        });
    }
    //filters friends
    private void filterFriends(String query) {
        filteredFriendList.clear();
        if(query.isEmpty()){
            filteredFriendList.addAll(friendList);
        }
        else{
            for(Friend friend : friendList){
                if(friend.getFriendName().toLowerCase().contains(query.toLowerCase())){
                    filteredFriendList.add(friend);
                }
            }
        }
        occasionFriendAdapter.notifyDataSetChanged();
    }

    //filters preferences
    private void filterPreferences(String query) {
        filteredPreferenceList.clear();
        if(query.isEmpty()){
            filteredPreferenceList.addAll(preferenceList);
            }
        else {
            for(Preference preference : preferenceList){
                if(preference.getName().toLowerCase().contains(query.toLowerCase())){
                    filteredPreferenceList.add(preference);
                }
            }
        }
    }

    private void createOccasion(){
        String title = occasionTitleEditText.getText().toString().trim();
        String date = occasionDateEditText.getText().toString().trim();
        String location = occasionLocationEditText.getText().toString().trim();
        String description = occasionDescriptionEditText.getText().toString().trim();

        if(title.isEmpty() || date.isEmpty() || location.isEmpty() || description.isEmpty()){
            //make a toast showing error message here
            //make sure to look at required fields in Occasion to ensure all required fields here
            //are actually required
            return;
        }

        Occasion newOccasion = new Occasion(0, title, date, location, description, null, 0);
        //make sure to add logic to save the occasion to database here!!
        //also add a toast to ensure that a successful occasion was created
    }
}