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

import com.example.bond.R;

public class NewDate extends AppCompatActivity {

    private EditText dateTitleEditText;
    private EditText dateEditText;
    private Button createNewDateButton;

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
        dateEditText = findViewById(R.id.create_new_date_date_edit_text);
        createNewDateButton = findViewById(R.id.create_new_date_button);

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

                //add logic to save date to database
                //TEMPORARY TOAST MESSAGE
                Toast.makeText(NewDate.this, "Date Created: " + title + " on " + date, Toast.LENGTH_SHORT).show();
                finish();
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