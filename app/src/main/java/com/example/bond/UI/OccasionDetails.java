package com.example.bond.UI;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bond.Adapters.OccasionDetailsFriendAdapter;
import com.example.bond.Adapters.OccasionDetailsPreferenceAdapter;
import com.example.bond.DAO.OccasionDAO;
import com.example.bond.DAO.UserDAO;
import com.example.bond.Database.BondAppDatabase;
import com.example.bond.Entities.Occasion;
import com.example.bond.Entities.User;
import com.example.bond.Models.Preference;
import com.example.bond.R;

import java.util.ArrayList;
import java.util.List;

public class OccasionDetails extends AppCompatActivity {

    //occasion details
    private TextView occasionDetailsTitleTextView;
    private TextView occasionDetailsDateTextView;
    private TextView occasionDetailsLocationTextView;
    private TextView occasionDetailsDescriptionTextView;
    private TextView occasionDetailsDateCreatedTextView;

    //recycler views
    private RecyclerView occasionDetailsGuestRecycler;
    private RecyclerView occasionDetailsPreferencesRecycler;

    //lists for guest and preference data
    private List<User> friendsAttending;
    private List<Preference> preferenceList;

    //adapters
    private OccasionDetailsFriendAdapter occasionDetailsFriendAdapter;
    private OccasionDetailsPreferenceAdapter occasionDetailsPreferenceAdapter;

    //database and DAO
    private BondAppDatabase db;
    private OccasionDAO occasionDAO;
    private UserDAO userDAO;

    //occasion to display and its ID
    private Occasion occasion;
    private int occasionID;

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

        //set up backwards navigation
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //edit text
        occasionDetailsTitleTextView = findViewById(R.id.occasion_details_title_text_view);
        occasionDetailsDateTextView = findViewById(R.id.occasion_details_date_text_view);
        occasionDetailsLocationTextView = findViewById(R.id.occasion_details_location_text_view);
        occasionDetailsDescriptionTextView = findViewById(R.id.occasion_details_description_text_view);
        occasionDetailsDateCreatedTextView= findViewById(R.id.occasion_details_date_created_text_view);

        //recycler views
        occasionDetailsGuestRecycler = findViewById(R.id.occasion_details_guest_recycler);
        occasionDetailsPreferencesRecycler = findViewById(R.id.occasion_details_preferences_recycler);

        occasionDetailsGuestRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        occasionDetailsPreferencesRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //guest list
        friendsAttending = new ArrayList<>();
        //preferences
        preferenceList = new ArrayList<>();

        //initialize adapters
        occasionDetailsFriendAdapter = new OccasionDetailsFriendAdapter(this, friendsAttending);
        occasionDetailsGuestRecycler.setAdapter(occasionDetailsFriendAdapter);
        occasionDetailsPreferenceAdapter = new OccasionDetailsPreferenceAdapter(this, preferenceList);
        occasionDetailsPreferencesRecycler.setAdapter(occasionDetailsPreferenceAdapter);

        //initialize database and DAO
        db = BondAppDatabase.getDatabase(getApplicationContext());
        occasionDAO = db.occasionDAO();
        userDAO = db.userDAO();

        //get occasion ID from intent
        occasionID = getIntent().getIntExtra("occasionID", -1);
        if (occasionID == -1) {
            Toast.makeText(this, "Error: Occasion not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadOccasionDetails();
    }

    private void loadOccasionDetails() {
        BondAppDatabase.databaseWriteExecutor.execute(() -> {
            occasion = occasionDAO.getOccasionByID(occasionID);
            runOnUiThread(() -> {
                if (occasion != null) {
                    occasionDetailsTitleTextView.setText(occasion.getOccasionTitle());
                    occasionDetailsDateTextView.setText(occasion.getOccasionDate());
                    occasionDetailsLocationTextView.setText(occasion.getOccasionLocation());
                    occasionDetailsDescriptionTextView.setText(occasion.getDescription());
                    occasionDetailsDateCreatedTextView.setText(occasion.getDateCreated());

                    loadOccasionFriends();
                    loadOccasionPreferences();
                } else {
                    Toast.makeText(this, "Error: Occasion not found", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        });
    }

    private void loadOccasionFriends() {
        String idsPart = occasion.getFriendIDs();
        Log.d("OccasionDetails", "Friend IDs: " + idsPart);
        if (idsPart == null || idsPart.isEmpty()) {
            friendsAttending.clear();
            occasionDetailsFriendAdapter.notifyDataSetChanged();
            return;
        }
        String[] idStrings = idsPart.split(",");
        List<Integer> IDs = new ArrayList<>();
        for (String idString : idStrings) {
            try {
                int id = Integer.parseInt(idString.trim());
                IDs.add(id);
            } catch (NumberFormatException e) {
                Log.e("OccasionDetails", "Error parsing ID: " + idString, e);
            }
        }

        BondAppDatabase.databaseWriteExecutor.execute(() -> {
            List<User> users = userDAO.getUsersByIDs(IDs);
            Log.d("OccasionDetails", "Users: " + users.size());
            runOnUiThread(() -> {
                friendsAttending.clear();
                friendsAttending.addAll(users);
                occasionDetailsFriendAdapter.notifyDataSetChanged();
            });
        });
    }

    private void loadOccasionPreferences() {
        String combinedData = occasion.getFriendPreferences();
        if (combinedData == null || combinedData.isEmpty()) {
            preferenceList.clear();
            occasionDetailsPreferenceAdapter.notifyDataSetChanged();
            return;
        }
        String[] parts = combinedData.split("\\|");
        if (parts.length > 1) {
            String prefsData = parts[1];
            String[] prefLines = prefsData.split("\\n");
            List<Preference> prefsList = new ArrayList<>();
            for (String line : prefLines) {
                if (!line.trim().isEmpty()) {
                    int colonIndex = line.indexOf(":");
                    if (colonIndex != -1) {
                        String name = line.substring(0, colonIndex).trim();
                        String value = line.substring(colonIndex + 1).trim();
                        prefsList.add(new Preference(name, value));
                    } else {
                        prefsList.add(new Preference(line.trim(), ""));
                    }
                }
            }
            preferenceList.clear();
            preferenceList.addAll(prefsList);
            occasionDetailsPreferenceAdapter.notifyDataSetChanged();
        } else {
            preferenceList.clear();
            occasionDetailsPreferenceAdapter.notifyDataSetChanged();
        }
    }
}