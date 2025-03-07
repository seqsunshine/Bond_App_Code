package com.example.bond.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bond.Adapters.PreferenceAdapter;
import com.example.bond.DAO.UserDAO;
import com.example.bond.Database.BondAppDatabase;
import com.example.bond.Entities.User;
import com.example.bond.Models.Preference;
import com.example.bond.R;

import java.util.ArrayList;
import java.util.List;

public class EditProfile extends AppCompatActivity {

    private ImageView editProfileLogo;
    private ImageView profilePicture;
    private RecyclerView preferencesRecycler;
    private Button addPreferenceButton;
    private Button addDateButton;
    private Button changePhotoButton;
    private TextView myPreferencesText;

    private List<Preference> preferenceList;
    private PreferenceAdapter preferenceAdapter;

    private static final int REQUEST_EDIT_PREFERENCE = 100;

    private BondAppDatabase db;
    private UserDAO userDAO;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.edit_profile), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //set up backwards navigation
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //commented out for now.. need to work on the logic for this navigation
//        //set up custom backwards navigation from NewDate and NewPreference
//        Intent intent = new Intent(EditProfile.this, NewDate.class);
//        intent.putExtra("origin", "editProfile");
//        startActivity(intent);

        //connect xml components to file
        editProfileLogo = findViewById(R.id.edit_profile_logo);
        profilePicture = findViewById(R.id.edit_page_profile_picture);
        preferencesRecycler = findViewById(R.id.my_preferences_recycler);
        addPreferenceButton = findViewById(R.id.add_preference_button);
        addDateButton = findViewById(R.id.add_date_button);
        changePhotoButton = findViewById(R.id.change_photo_button);
        myPreferencesText = findViewById(R.id.my_preferences_text);

        //set up recycler view
        preferencesRecycler.setLayoutManager(new LinearLayoutManager(this));

        //empty preference list for now.. need to add the logic to load in user preferences!
        preferenceList = new ArrayList<>();

        //initialize adapter
        preferenceAdapter = new PreferenceAdapter(this, preferenceList, new PreferenceAdapter.OnPreferenceClickListener() {
            @Override
            public void onPreferenceClick(int position, Preference preference) {
                Intent intent = new Intent(EditProfile.this, EditPreference.class);
                //add names, descriptions, and positions
                intent.putExtra(EditPreference.EXTRA_PREFERENCE_NAME, preference.getName());
                intent.putExtra(EditPreference.EXTRA_PREFERENCE_DESCRIPTION, preference.getDescription());
                intent.putExtra(EditPreference.EXTRA_PREFERENCE_POSITION, position);
                startActivityForResult(intent, REQUEST_EDIT_PREFERENCE);
            }
        });
        preferencesRecycler.setAdapter(preferenceAdapter);

        //activate add preference button
        addPreferenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfile.this, NewPreference.class);
                startActivity(intent);
            }
        });

        //activates add date button
        addDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfile.this, NewDate.class);
                startActivity(intent);
            }
        });

        //activates change photo button
        changePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Need to create logic for image picker... tbd on how to do this
            }
        });

        loadUserPreferences();
    }

    //loads user preferences
    private void loadUserPreferences() {
        //need to create logic for this.. NOT FINISHED YET temp data for now

        //create default generic preferences with blank values
        List<Preference> defaultPreferences = new ArrayList<>();
        defaultPreferences.add(new Preference("Birthday: ", ""));
        defaultPreferences.add(new Preference("Favorite Color: ", ""));
        defaultPreferences.add(new Preference("Allergies", ""));
        defaultPreferences.add(new Preference("Dietary Restrictions: ", ""));
        defaultPreferences.add(new Preference("Favorite Food: ", ""));
        defaultPreferences.add(new Preference("Hobbies: ", ""));
        defaultPreferences.add(new Preference("Current Job: ",""));
        defaultPreferences.add(new Preference("Pet Name: ", ""));
        defaultPreferences.add(new Preference("Partner Name: ", ""));
        defaultPreferences.add(new Preference("Interests: ", ""));

        //retrieve custom preferences from the database
        List<Preference> customPreferences = new ArrayList<>();
        //need to add logic to load each new custom preference

        //merge default and custom preferences
        List<Preference> combinedPreferences = new ArrayList<>(defaultPreferences);
        combinedPreferences.addAll(customPreferences);

        //clear current list and update it with combined list
        preferenceList.clear();
        preferenceList.addAll(combinedPreferences);

        //notify data set changed so recycler view updates
        preferenceAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EDIT_PREFERENCE && resultCode == RESULT_OK && data != null) {
            String updatedDescription = data.getStringExtra(EditPreference.EXTRA_PREFERENCE_DESCRIPTION);
            int position = data.getIntExtra(EditPreference.EXTRA_PREFERENCE_POSITION, -1);
            if (position != -1) {
                //update preference
                Preference pref = preferenceList.get(position);
                pref.setDescription(updatedDescription);
                preferenceAdapter.notifyItemChanged(position);

                //update corresponding field in currentUser
                if (pref.getName().contains("Birthday")) {
                    currentUser.setFavoriteColor(updatedDescription);
                } else if (pref.getName().contains("Favorite Color")) {
                    currentUser.setFavoriteColor(updatedDescription);
                } else if (pref.getName().contains("Allergies")) {
                    currentUser.setAllergies(updatedDescription);
                } else if (pref.getName().contains("Dietary Restrictions")) {
                    currentUser.setDietaryRestrictions(updatedDescription);
                } else if (pref.getName().contains("Favorite Food")) {
                    currentUser.setFavoriteFood(updatedDescription);
                } else if (pref.getName().contains("Hobbies")) {
                    currentUser.setHobbies(updatedDescription);
                } else if (pref.getName().contains("Current Job")) {
                    currentUser.setCurrentJob(updatedDescription);
                } else if (pref.getName().contains("Pet Name")) {
                    currentUser.setPetName(updatedDescription);
                } else if (pref.getName().contains("Partner Name")) {
                    currentUser.setPartnerName(updatedDescription);
                } else if (pref.getName().contains("Interests")) {
                    currentUser.setInterests(updatedDescription);
                }

                BondAppDatabase.databaseWriteExecutor.execute(() -> {
                    db.userDAO().updateUser(currentUser);
                });
            }
        }
    }
}