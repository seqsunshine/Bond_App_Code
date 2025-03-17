package com.example.bond.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bond.R;

import java.util.Calendar;

public class EditPreference extends AppCompatActivity {


    public static final String EXTRA_PREFERENCE_NAME = "EXTRA_PREFERENCE_NAME";
    public static final String EXTRA_PREFERENCE_DESCRIPTION = "EXTRA_PREFERENCE_DESCRIPTION";
    public static final String EXTRA_PREFERENCE_POSITION = "EXTRA_PREFERENCE_POSITION";
    public static final String EXTRA_PREFERENCE_DELETE = "EXTRA_PREFERENCE_DELETE";

    private TextView titleTextView;
    private EditText descriptionEditText;
    private Button saveButton;
    private Button deleteButton;

    private String preferenceName;
    private String currentDescription;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_preference);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        titleTextView = findViewById(R.id.edit_preference_title_text);
        descriptionEditText = findViewById(R.id.edit_preference_edit_text);
        saveButton = findViewById(R.id.edit_preference_button);
        deleteButton = findViewById(R.id.delete_preference_button);

        //retrieve data
        Intent intent = getIntent();
        if (intent != null) {
            preferenceName = intent.getStringExtra(EXTRA_PREFERENCE_NAME);
            currentDescription = intent.getStringExtra(EXTRA_PREFERENCE_DESCRIPTION);
            position = intent.getIntExtra(EXTRA_PREFERENCE_POSITION, -1);
        }

        //set the title of the activity to the name of the preference
        setTitle("Edit: " + preferenceName);
        //update text view in layout to display preference name
        if (!TextUtils.isEmpty(preferenceName)) {
            titleTextView.setText(preferenceName);
        }

        //pre-fill the edit text with current description (if any)
        if (TextUtils.isEmpty(currentDescription)) {
            descriptionEditText.setText(currentDescription);
        }

        //if birthday is selected, show a date picker dialogue
        if (preferenceName.contains("Birthday")) {
            showDatePickerDialog();
        }

        //activate save button
        saveButton.setOnClickListener(v -> {
            String newDescription = descriptionEditText.getText().toString().trim();
            if(TextUtils.isEmpty(newDescription)) {
                Toast.makeText(EditPreference.this, "Please enter a description", Toast.LENGTH_SHORT).show();
                return;
            }
            //add the description and position to the activity
            Intent resultIntent = new Intent();
            resultIntent.putExtra(EXTRA_PREFERENCE_DESCRIPTION, newDescription);
            resultIntent.putExtra(EXTRA_PREFERENCE_POSITION, position);
            resultIntent.putExtra(EXTRA_PREFERENCE_DELETE, false);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        //activate delete button
        deleteButton.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra(EXTRA_PREFERENCE_POSITION, position);
            resultIntent.putExtra(EXTRA_PREFERENCE_DELETE, true);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                EditPreference.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        String formattedDate = (selectedMonth + 1) + "/" + selectedDay + "/" + selectedYear;
                        descriptionEditText.setText(formattedDate);
                    }
                },
                year,
                month,
                day
        );
        datePickerDialog.show();
    }
}