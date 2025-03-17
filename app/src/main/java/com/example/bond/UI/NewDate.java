package com.example.bond.UI;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
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

import com.example.bond.DAO.FriendCustomFieldDAO;
import com.example.bond.DAO.UserCustomFieldDAO;
import com.example.bond.Database.BondAppDatabase;
import com.example.bond.Entities.FriendCustomField;
import com.example.bond.Entities.UserCustomField;
import com.example.bond.R;

import java.util.Calendar;

public class NewDate extends AppCompatActivity {

    private EditText dateTitleEditText;
    private EditText dateEditText;
    private Button createNewDateButton;

    private BondAppDatabase db;
    private UserCustomFieldDAO userCustomFieldDAO;
    private FriendCustomFieldDAO friendCustomFieldDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_date);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.new_date), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //set up backwards navigation
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //connect xml components
        dateTitleEditText = findViewById(R.id.create_new_date_title_edit_text);
        createNewDateButton = findViewById(R.id.create_new_date_button);
        dateEditText = findViewById(R.id.create_new_date_date_edit_text);

        //initialize database and dao
        db = BondAppDatabase.getDatabase(getApplicationContext());
        userCustomFieldDAO = db.userCustomFieldDAO();
        friendCustomFieldDAO = db.friendCustomFieldDAO();

        //date picker
        dateEditText.setFocusable(false);
        dateEditText.setOnClickListener(v -> showDatePickerDialog());

        //activate create date button
        createNewDateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //get inputs
                String title = dateTitleEditText.getText().toString().trim();
                String date = dateEditText.getText().toString().trim();

                //validate inputs
                if (TextUtils.isEmpty(title) || TextUtils.isEmpty(date)) {
                    Toast.makeText(NewDate.this,"Please enter both title and date", Toast.LENGTH_SHORT).show();
                    return;
                }
                //check if friend id was provided
                int friendID = getIntent().getIntExtra("friend_id", -1);
                if (friendID != -1) {
                    FriendCustomField newDateField = new FriendCustomField(0, friendID, title, date);
                    BondAppDatabase.databaseWriteExecutor.execute(() -> {
                        friendCustomFieldDAO.insertFriendCustomField(newDateField);
                        runOnUiThread(() -> {
                            Toast.makeText(NewDate.this, "Date Created for Friend: " + title + " on " + date, Toast.LENGTH_SHORT).show();
                            finish();
                        });
                    });
                } else {
                    //get current user's ID
                    SharedPreferences prefs = getSharedPreferences("my_app_prefs", MODE_PRIVATE);
                    int currentUserID = prefs.getInt("current_user_id", -1);
                    if (currentUserID == -1) {
                        Toast.makeText(NewDate.this, "No user logged in", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    //create a new user custom field object
                    UserCustomField newDateField = new UserCustomField(0, currentUserID, title, date);

                    //insert the date
                    BondAppDatabase.databaseWriteExecutor.execute(() -> {
                        userCustomFieldDAO.insertUserCustomField(newDateField);
                        runOnUiThread(() -> {
                            Toast.makeText(NewDate.this, "Date Created: " + title + " on " + date, Toast.LENGTH_SHORT).show();
                            finish();
                        });
                    });
                }
            }
        });
    }
    //date picker
    private void showDatePickerDialog() {
        //get current date as default
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String formattedDate = selectedMonth + 1 + "/" + selectedDay + "/" + selectedYear;
                    dateEditText.setText(formattedDate);
                },
                year,
                month,
                day
        );
        datePickerDialog.show();
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