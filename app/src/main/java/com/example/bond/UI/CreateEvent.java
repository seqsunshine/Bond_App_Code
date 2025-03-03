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

import com.example.bond.Adapters.EventFriendAdapter;
import com.example.bond.Adapters.EventPreferenceAdapter;
import com.example.bond.Entities.Friend;
import com.example.bond.Entities.Occasion;
import com.example.bond.Models.Preference;
import com.example.bond.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CreateEvent extends AppCompatActivity {
    //event details
    private EditText eventTitleEditText;
    private EditText eventDateEditText;
    private EditText eventLocationEditText;
    private EditText eventDescriptionEditText;

    //search fields
    private EditText eventSearchFriendsEditText;
    private EditText eventSearchPreferencesEditText;

    //recycler views
    private RecyclerView eventSelectFriendsRecycler;
    private RecyclerView eventSelectPreferencesRecycler;

    //button
    private Button eventCreateEventButton;

    //friend data related
    private List<Friend> friendList;
    private List<Friend> filteredFriendList;
    private EventFriendAdapter eventFriendAdapter;

    //preference data related
    private List<Preference> preferenceList;
    private List<Preference> filteredPreferenceList;
    private EventPreferenceAdapter eventPreferenceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_event);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.create_event), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //event detail fields
        eventTitleEditText = findViewById(R.id.event_title_edit_text);
        eventDateEditText = findViewById(R.id.event_date_edit_text);
        eventLocationEditText = findViewById(R.id.event_location_edit_text);
        eventDescriptionEditText = findViewById(R.id.event_description_edit_text);

        //search fields
        eventSearchFriendsEditText = findViewById(R.id.event_search_friends_edit_text);
        eventSearchPreferencesEditText = findViewById(R.id.event_search_preferences_edit_text);

        //recyclers
        eventSelectFriendsRecycler = findViewById(R.id.event_select_friends_recycler);
        eventSelectPreferencesRecycler = findViewById(R.id.event_select_preferences_recycler);

        //button
        eventCreateEventButton = findViewById(R.id.event_create_event_button);

        //set up recycler views
        LinearLayoutManager friendsLayoutManager = new LinearLayoutManager(this, LinearLayoutManager. HORIZONTAL, false);
        eventSelectFriendsRecycler.setLayoutManager(friendsLayoutManager);

        LinearLayoutManager preferencesLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        eventSelectPreferencesRecycler.setLayoutManager(preferencesLayoutManager);

        //initialize friend list
        friendList = new ArrayList<>();
        filteredFriendList = new ArrayList<>(friendList);
        //need to add info to filter in data. NOT DONE

        //initialize event friend attached to recycler
        eventFriendAdapter = new EventFriendAdapter(this, filteredFriendList);
        eventSelectFriendsRecycler.setAdapter(eventFriendAdapter);

        //initialize preference list
        preferenceList = new ArrayList<>();
        filteredPreferenceList = new ArrayList<>(preferenceList);
        //need to add info to filter in data. NOT DONE

        //initialize event preference attached to recycler
        eventPreferenceAdapter = new EventPreferenceAdapter(this, filteredPreferenceList);

        //set up friend search
        eventSearchFriendsEditText.addTextChangedListener(new TextWatcher() {
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
        eventSearchPreferencesEditText.addTextChangedListener(new TextWatcher() {
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

        //activate create event button
        eventCreateEventButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                createEvent();
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
        eventFriendAdapter.notifyDataSetChanged();
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

    private void createEvent(){
        String title = eventTitleEditText.getText().toString().trim();
        String date = eventDateEditText.getText().toString().trim();
        String location = eventLocationEditText.getText().toString().trim();
        String description = eventDescriptionEditText.getText().toString().trim();

        if(title.isEmpty() || date.isEmpty() || location.isEmpty() || description.isEmpty()){
            //make a toast showing error message here
            //make sure to look at required fields in Occasion to ensure all required fields here
            //are actually required
            return;
        }

        Occasion newOccasion = new Occasion(0, title, date, location, description, 0);
        //make sure to add logic to save the event to database here!!
        //also add a toast to ensure that a successful event was created
        //ALSO noted previously, but you need to go through and make all "event" things occasions!!
    }
}