package com.example.bond.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bond.Adapters.OccasionFriendAdapter;
import com.example.bond.Adapters.OccasionPreferenceAdapter;
import com.example.bond.DAO.FriendDAO;
import com.example.bond.DAO.UserDAO;
import com.example.bond.Database.BondAppDatabase;
import com.example.bond.Entities.Occasion;
import com.example.bond.Entities.User;
import com.example.bond.Models.Preference;
import com.example.bond.R;

import java.util.ArrayList;
import java.util.Calendar;
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
    private List<User> friendList;
    private List<User> filteredFriendList;
    private OccasionFriendAdapter occasionFriendAdapter;

    //preference data related
    private List<Preference> preferenceList;
    private List<Preference> filteredPreferenceList;
    private OccasionPreferenceAdapter occasionPreferenceAdapter;

    //adding friends and preferences
    private List<User> selectedFriend;
    private List<Preference> selectedPreference;

    //database related
    private BondAppDatabase db;
    private FriendDAO friendDAO;
    private UserDAO userDAO;

    private int currentUserID;

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
        occasionDateEditText.setFocusable(false);
        occasionDateEditText.setOnClickListener(v -> showDatePickerDialog());
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

        //initialize selected friend and preference
        selectedFriend = new ArrayList<>();
        selectedPreference = new ArrayList<>();

        //set up recycler views
        LinearLayoutManager friendsLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        occasionSelectFriendsRecycler.setLayoutManager(friendsLayoutManager);

        LinearLayoutManager preferencesLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        occasionSelectPreferencesRecycler.setLayoutManager(preferencesLayoutManager);

        //initialize friend list
        friendList = new ArrayList<>();
        filteredFriendList = new ArrayList<>(friendList);

        //initialize preference list
        preferenceList = new ArrayList<>();
        filteredPreferenceList = new ArrayList<>(preferenceList);

        //initialize occasion friend attached to recycler
        occasionFriendAdapter = new OccasionFriendAdapter(this, filteredFriendList, (User user) -> {
            if (!selectedFriend.contains(user)) {
                selectedFriend.add(user);
                Toast.makeText(CreateOccasion.this, "Friend added!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(CreateOccasion.this, "Friend already selected!", Toast.LENGTH_SHORT).show();
            }
        });
        occasionSelectFriendsRecycler.setAdapter(occasionFriendAdapter);

        //initialize occasion preference attached to recycler
        occasionPreferenceAdapter = new OccasionPreferenceAdapter(this, filteredPreferenceList, (Preference preference) -> {
            if (!selectedPreference.contains(preference)) {
                selectedPreference.add(preference);
                Toast.makeText(CreateOccasion.this, "Preference added!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(CreateOccasion.this, "Preference already selected!", Toast.LENGTH_SHORT).show();
            }
        });
        occasionSelectPreferencesRecycler.setAdapter(occasionPreferenceAdapter);

        //initialize database and DAO
        db = BondAppDatabase.getDatabase(getApplicationContext());
        friendDAO = db.friendDAO();
        userDAO = db.userDAO();

        //get current user id
        currentUserID = getSharedPreferences("my_app_prefs", MODE_PRIVATE).getInt("current_user_id", -1);


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
        occasionCreateOccasionButton.setOnClickListener(v -> {
            createOccasion();
        });
    }
    //filters friends
    private void filterFriends(String query) {
       if (query.isEmpty()) {
           filteredFriendList.clear();
           occasionFriendAdapter.notifyDataSetChanged();
           return;
       } else {
           BondAppDatabase.databaseWriteExecutor.execute(() -> {
               List<User> results = friendDAO.searchFriendUsersForOwner(currentUserID, query);
               runOnUiThread(() -> {
                   filteredFriendList.clear();
                   filteredFriendList.addAll(results);
                   occasionFriendAdapter.notifyDataSetChanged();
               });
           });
       }
    }

    //filters preferences
    private void filterPreferences(String query) {
        final String q = query.trim().toLowerCase();
        if(q.isEmpty()){
            runOnUiThread(() -> {
                filteredPreferenceList.clear();
                occasionPreferenceAdapter.notifyDataSetChanged();
            });
            return;
            }
        BondAppDatabase.databaseWriteExecutor.execute(() -> {
            User currentUser = userDAO.getUserByID(currentUserID);
            List<Preference> allPrefs = new ArrayList<>();
            if(currentUser != null){
                allPrefs.add(new Preference("Birthday", currentUser.getBirthday()));
                allPrefs.add(new Preference("Favorite Color", currentUser.getFavoriteColor()));
                allPrefs.add(new Preference("Allergies", currentUser.getAllergies()));
                allPrefs.add(new Preference("Dietary Restrictions", currentUser.getDietaryRestrictions()));
                allPrefs.add(new Preference("Favorite Food", currentUser.getFavoriteFood()));
                allPrefs.add(new Preference("Hobbies", currentUser.getHobbies()));
                allPrefs.add(new Preference("Current Job", currentUser.getCurrentJob()));
                allPrefs.add(new Preference("Pet Name", currentUser.getPetName()));
                allPrefs.add(new Preference("Partner Name", currentUser.getPartnerName()));
                allPrefs.add(new Preference("Interests", currentUser.getInterests()));
            }

            List<Preference> results = new ArrayList<>();
            for (Preference pref : allPrefs) {
                if (pref.getName() != null && pref.getName().toLowerCase().contains(q)) {
                    results.add(pref);
                }
            }

            runOnUiThread(() -> {
                filteredPreferenceList.clear();
                filteredPreferenceList.addAll(results);
                occasionPreferenceAdapter.notifyDataSetChanged();
            });
        });
    }

    private void createOccasion(){
        String title = occasionTitleEditText.getText().toString().trim();
        String date = occasionDateEditText.getText().toString().trim();
        String location = occasionLocationEditText.getText().toString().trim();
        String description = occasionDescriptionEditText.getText().toString().trim();

        if(title.isEmpty() || date.isEmpty() || location.isEmpty() || description.isEmpty()){
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder friendIDsBuilder = new StringBuilder();
        for (User friend : selectedFriend) {
            friendIDsBuilder.append(friend.getUserID()).append(",");
        }
        String friendIDs = "";
        if (friendIDsBuilder.length() > 0) {
            friendIDs = friendIDsBuilder.substring(0, friendIDsBuilder.length() - 1);
        }

        StringBuilder prefBuilder = new StringBuilder();
        for (Preference pref : selectedPreference) {
            String category = pref.getName();
            prefBuilder.append(category).append(": ");
            for (User friend : selectedFriend) {
                String friendValue = getFriendPreferenceForCategory(friend, category);
                if (friendValue != null && !friendValue.isEmpty()) {
                    prefBuilder.append(friend.getName())
                            .append(" - ")
                            .append(friendValue)
                            .append(", ");
                }
            }
            int length = prefBuilder.length();
            if (length >= 2 && prefBuilder.substring(length - 2).equals(", ")) {
                prefBuilder.setLength(length - 2);
            }
            prefBuilder.append("\n");
        }

        String preferencesString = prefBuilder.toString().trim();
        String friendPreferencesCombined = friendIDs + "|" + preferencesString;

        Occasion newOccasion = new Occasion(
                0,
                currentUserID,
                title,
                description,
                date,
                location,
                getCurrentDate(),
                friendPreferencesCombined,
                friendIDs,
                currentUserID

        );

        BondAppDatabase.databaseWriteExecutor.execute(() -> {
            long insertedID = db.occasionDAO().insertOccasion(newOccasion);
            runOnUiThread(() -> {
                Toast.makeText(this, "Occasion created!", Toast.LENGTH_SHORT).show();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("occasionID", insertedID);
                setResult(RESULT_OK, resultIntent);
                finish();
            });
        });
    }

    private String getFriendPreferenceForCategory(User friend, String category) {
        switch(category) {
            case "Birthday":
                return friend.getBirthday();
            case "Favorite Color":
                return friend.getFavoriteColor();
            case "Allergies":
                return friend.getAllergies();
            case "Dietary Restrictions":
                return friend.getDietaryRestrictions();
            case "Favorite Food":
                return friend.getFavoriteFood();
            case "Hobbies":
                return friend.getHobbies();
            case "Current Job":
                return friend.getCurrentJob();
            case "Pet Name":
                return friend.getPetName();
            case "Partner Name":
                return friend.getPartnerName();
            case "Interests":
                return friend.getInterests();
            default:
                return "";
        }
    }

    private String getCurrentDate() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM-dd-yyyy", java.util.Locale.getDefault());
        return sdf.format(new java.util.Date());
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String formattedDate = selectedMonth + 1 + "/" + selectedDay + "/" + selectedYear;
                    occasionDateEditText.setText(formattedDate);
                },
                year,
                month,
                day
        );
        datePickerDialog.show();
    }
}