package com.example.bond.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bond.DAO.UserDAO;
import com.example.bond.Database.BondAppDatabase;
import com.example.bond.Entities.User;
import com.example.bond.R;
import com.example.bond.Utils.PasswordUtils;

public class LoginPage extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    private BondAppDatabase db;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login_page), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //set up backwards navigation
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //connect xml components to file
        usernameEditText = findViewById(R.id.username_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        loginButton = findViewById(R.id.login_button);

        //initialize database and DAO
        db = BondAppDatabase.getDatabase(getApplicationContext());
        userDAO = db.userDAO();

        //activate login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogin();
            }
        });
    }

    //gets input from user and validates it
    private void handleLogin() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        //validates
        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(LoginPage.this, "Please enter both username and password.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        //run database query
        BondAppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                //retrieve user from database
                User user = userDAO.getUserByUserName(username);
                if (user == null) {
                    runOnUiThread(() ->
                        Toast.makeText(LoginPage.this, "Username not found.", Toast.LENGTH_SHORT).show()
                    );
                    return;
                }

                //verify password using PasswordUtils
                boolean isValid = PasswordUtils.verifyPassword(password, user.getPasswordHash());
                if (!isValid) {
                    runOnUiThread(() ->
                        Toast.makeText(LoginPage.this, "Incorrect password.", Toast.LENGTH_SHORT).show()
                    );
                    return;
                }

                //successful login: store userID for later use
                SharedPreferences prefs = getSharedPreferences("my_app_prefs", MODE_PRIVATE);
                prefs.edit().putInt("current_user_id", user.getUserID()).apply();

                runOnUiThread(() -> {
                    Toast.makeText(LoginPage.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                    //navigate to user dashboard
                    Intent intent = new Intent(LoginPage.this, UserDashboard.class);
                    startActivity(intent);
                    finish();
                });
            }
        });
    }
}