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
        preferenceAdapter = new PreferenceAdapter(this, preferenceList);
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
        preferenceList.clear();
        preferenceAdapter.notifyDataSetChanged();
    }
}