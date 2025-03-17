package com.example.bond.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.bond.Entities.UserCustomField;
import com.example.bond.Models.Preference;
import com.example.bond.R;

import java.util.ArrayList;
import java.util.List;

public class EditProfile extends AppCompatActivity {

    private RecyclerView preferencesRecycler;
    private Button addPreferenceButton;
    private Button addDateButton;
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

        //connect xml components to file
        preferencesRecycler = findViewById(R.id.my_preferences_recycler);
        addPreferenceButton = findViewById(R.id.add_preference_button);
        addDateButton = findViewById(R.id.add_date_button);
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

        //initialize database and DAO
        db = BondAppDatabase.getDatabase(getApplicationContext());
        userDAO = db.userDAO();

        loadCurrentUser();
    }

    //loads current user
    private void loadCurrentUser() {
        SharedPreferences prefs = getSharedPreferences("my_app_prefs", MODE_PRIVATE);
        int userID = prefs.getInt("current_user_id", -1);
        if (userID != -1) {
            BondAppDatabase.databaseWriteExecutor.execute(() -> {
                //load current user from database
                currentUser = userDAO.getUserByID(userID);

                //update the UI on main thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myPreferencesText.setText(currentUser.getName());
                        loadUserPreferences();
                    }
                });
            });
        } else {
            Toast.makeText(this, "No user logged in", Toast.LENGTH_SHORT).show();
        }
    }

    //loads user preferences
    private void loadUserPreferences() {
        if (currentUser != null) {
            //run query on background thread
            BondAppDatabase.databaseWriteExecutor.execute(() -> {
                //get custom fields from database
                List<UserCustomField> customFields = db.userCustomFieldDAO().getCustomFieldsForUser(currentUser.getUserID());
                //convert custom field objects to preference objects
                List<Preference> customPreferences = new ArrayList<>();
                if (customFields != null) {
                    for (UserCustomField field : customFields) {
                        customPreferences.add(new Preference(field.getFieldName(), field.getFieldValue()));
                    }
                }

                List<Preference> defaultPreferences = new ArrayList<>();
                //load default preferences
                defaultPreferences.add(new Preference("Name: ",
                        currentUser.getName() != null ? currentUser.getName() : ""));
                defaultPreferences.add(new Preference("Birthday: ",
                        currentUser.getBirthday() != null ? currentUser.getBirthday() : ""));
                defaultPreferences.add(new Preference("Favorite Color: ",
                        currentUser.getFavoriteColor() != null ? currentUser.getFavoriteColor() : ""));
                defaultPreferences.add(new Preference("Allergies: ",
                        currentUser.getAllergies() != null ? currentUser.getAllergies() : ""));
                defaultPreferences.add(new Preference("Dietary Restrictions: ",
                        currentUser.getDietaryRestrictions() != null ? currentUser.getDietaryRestrictions() : ""));
                defaultPreferences.add(new Preference("Favorite Food: ",
                        currentUser.getFavoriteFood() != null ? currentUser.getFavoriteFood() : ""));
                defaultPreferences.add(new Preference("Hobbies: ",
                        currentUser.getHobbies() != null ? currentUser.getHobbies() : ""));
                defaultPreferences.add(new Preference("Current Job: ",
                        currentUser.getCurrentJob() != null ? currentUser.getCurrentJob() : ""));
                defaultPreferences.add(new Preference("Pet Name: ",
                        currentUser.getPetName() != null ? currentUser.getPetName() : ""));
                defaultPreferences.add(new Preference("Partner Name: ",
                        currentUser.getPartnerName() != null ? currentUser.getPartnerName() : ""));
                defaultPreferences.add(new Preference("Interests: ",
                        currentUser.getInterests() != null ? currentUser.getInterests() : ""));

                //merge default and custom preferences
                List<Preference> combinedPreferences = new ArrayList<>(defaultPreferences);
                combinedPreferences.addAll(customPreferences);

                //update adapters list
                runOnUiThread(() -> {
                    preferenceList.clear();
                    preferenceList.addAll(combinedPreferences);
                    preferenceAdapter.notifyDataSetChanged();
                });
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EDIT_PREFERENCE && resultCode == RESULT_OK && data != null) {
            boolean shouldDelete = data.getBooleanExtra(EditPreference.EXTRA_PREFERENCE_DELETE, false);
            int position = data.getIntExtra(EditPreference.EXTRA_PREFERENCE_POSITION, -1);
            if (position != -1) {
                //update preference
                Preference pref = preferenceList.get(position);
                if (shouldDelete) {
                    //remove from list and update adapter
                    preferenceList.remove(position);
                    preferenceAdapter.notifyItemRemoved(position);

                    if (isCustomPreference(pref)) {
                        BondAppDatabase.databaseWriteExecutor.execute(() -> {
                            db.userCustomFieldDAO().deleteCustomFieldByNameAndUserID(pref.getName(), currentUser.getUserID());
                        });
                    }
                } else {
                    String updatedDescription = data.getStringExtra(EditPreference.EXTRA_PREFERENCE_DESCRIPTION);
                    pref.setDescription(updatedDescription);
                    preferenceAdapter.notifyItemChanged(position);
                    //update corresponding field in currentUser
                    if (pref.getName().contains("Birthday")) {
                        currentUser.setBirthday(updatedDescription);
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
                    } else if (pref.getName().contains("Name")) {
                        currentUser.setName(updatedDescription);
                    }

                    BondAppDatabase.databaseWriteExecutor.execute(() -> {
                        db.userDAO().updateUser(currentUser);
                    });
                }
            }
        }
    }

    private boolean isCustomPreference(Preference pref) {
        String name = pref.getName();
        return !(name.contains("Name: ") ||
                name.contains("Birthday: ") ||
                name.contains("Favorite Color: ") ||
                name.contains("Allergies: ") ||
                name.contains("Dietary Restrictions: ") ||
                name.contains("Favorite Food: ") ||
                name.contains("Hobbies: ") ||
                name.contains("Current Job: ") ||
                name.contains("Pet Name: ") ||
                name.contains("Partner Name: ") ||
                name.contains("Interests: "));

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserPreferences();
    }
}