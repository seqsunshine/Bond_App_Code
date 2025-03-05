package com.example.bond.UI;

import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bond.Adapters.OccasionDetailsFriendAdapter;
import com.example.bond.Adapters.OccasionDetailsPreferenceAdapter;
import com.example.bond.Entities.Occasion;
import com.example.bond.Models.Preference;
import com.example.bond.R;

import java.util.ArrayList;
import java.util.List;

public class OccasionDetails extends AppCompatActivity {

    //occasion details
    private EditText occasionDetailsTitleEditText;
    private EditText occasionDetailsDateEditText;
    private EditText occasionDetailsLocationEditText;
    private EditText occasionDetailsDescriptionEditText;
    private EditText occasionDetailsDateCreatedEditText;

    //recycler views
    private RecyclerView occasionDetailsGuestRecycler;
    private RecyclerView occasionDetailsPreferencesRecycler;

    //lists for sample date TO BE ADDED
    private List<String> friendsAttending;
    private List<Preference> preferenceList;

    //adapters NEED TO CREATE
    private OccasionDetailsFriendAdapter occasionDetailsFriendAdapter;
    private OccasionDetailsPreferenceAdapter occasionDetailsPreferenceAdapter;

    //just temporary, will eventually be directly imported from DB
    private Occasion occasion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_occasion_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.occasion_details), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //edit text
        occasionDetailsTitleEditText = findViewById(R.id.occasion_details_title_edit_text);
        occasionDetailsDateEditText = findViewById(R.id.occasion_details_date_edit_text);
        occasionDetailsLocationEditText = findViewById(R.id.occasion_details_location_edit_text);
        occasionDetailsDescriptionEditText = findViewById(R.id.occasion_details_description_edit_text);
        occasionDetailsDateCreatedEditText= findViewById(R.id.occasion_details_date_created_edit_text);

        //recycler views
        occasionDetailsGuestRecycler = findViewById(R.id.occasion_details_guest_recycler);
        occasionDetailsPreferencesRecycler = findViewById(R.id.occasion_details_preferences_recycler);

        occasionDetailsGuestRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        occasionDetailsPreferencesRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        //start of adding sample data, NEED TO COMPLETE
        //guest list
        friendsAttending = new ArrayList<>();

        //preferences
        preferenceList = new ArrayList<>();

        //attach adapters to recycler views
        occasionDetailsFriendAdapter = new OccasionDetailsFriendAdapter(this, friendsAttending);
        occasionDetailsGuestRecycler.setAdapter(occasionDetailsFriendAdapter);

        occasionDetailsPreferenceAdapter = new OccasionDetailsPreferenceAdapter(this, preferenceList);
        occasionDetailsPreferencesRecycler.setAdapter(occasionDetailsPreferenceAdapter);

        loadOccasionDetails();
    }

    private void loadOccasionDetails() {
        occasionDetailsTitleEditText.setText(occasion.getOccasionTitle());
        occasionDetailsDateEditText.setText(occasion.getOccasionDate());
        occasionDetailsLocationEditText.setText(occasion.getOccasionLocation());
        occasionDetailsDescriptionEditText.setText(occasion.getDescription());
        occasionDetailsDateCreatedEditText.setText(occasion.getDateCreated()); //THIS ONE IS NOT DONE
        //you will need to fetch data from the DB eventually
        //STOPPED HERE!!! Need to create more methods in Occasion class.
    }
}