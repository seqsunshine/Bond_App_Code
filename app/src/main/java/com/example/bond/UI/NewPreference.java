package com.example.bond.UI;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bond.DAO.UserCustomFieldDAO;
import com.example.bond.Database.BondAppDatabase;
import com.example.bond.Entities.User;
import com.example.bond.Entities.UserCustomField;
import com.example.bond.R;

public class NewPreference extends AppCompatActivity {

    private EditText preferenceTitleEditText;
    private EditText preferenceDescriptionEditText;
    private Button createNewPreferenceButton;

    private BondAppDatabase db;
    private UserCustomFieldDAO userCustomFieldDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_preference);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.new_preference), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //set up backwards navigation
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //connect xml components
        preferenceTitleEditText = findViewById(R.id.create_new_preference_title_edit_text);
        preferenceDescriptionEditText = findViewById(R.id.create_new_preference_description_edit_text);
        createNewPreferenceButton = findViewById(R.id.create_new_preference_button);

        //initialize database and DAO
        db = BondAppDatabase.getDatabase(getApplicationContext());
        userCustomFieldDAO = db.userCustomFieldDAO();

        //activate new preference button
        createNewPreferenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //retrieve inputs
                String title = preferenceTitleEditText.getText().toString().trim();
                String description = preferenceDescriptionEditText.getText().toString().trim();

                //validate inputs
                if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
                    Toast.makeText(NewPreference.this, "Please fill out both title and description", Toast.LENGTH_SHORT).show();
                    return;
                }

                //get current user ID
                int currentUserID = getSharedPreferences("my_app_prefs", MODE_PRIVATE).getInt("current_user_id", -1);
                if (currentUserID == -1) {
                    Toast.makeText(NewPreference.this, "No user logged in", Toast.LENGTH_SHORT).show();
                    return;
                }

                //create new custom field
                UserCustomField newField = new UserCustomField(0, currentUserID, title, description);

                //insert new custom preference
                BondAppDatabase.databaseWriteExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        userCustomFieldDAO.insertUserCustomField(newField);
                        Toast.makeText(NewPreference.this, "Preference created: " + title, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
    }

    //add custom backwards navigation to EditProfile or CreateFriend
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            String origin = getIntent().getStringExtra("origin");
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}