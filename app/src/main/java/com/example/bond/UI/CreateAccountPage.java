package com.example.bond.UI;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bond.Database.BondAppDatabase;
import com.example.bond.Entities.User;
import com.example.bond.R;

public class CreateAccountPage extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button createAccountButton;

    private BondAppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_account_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.create_account), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //connects xml components to file
        usernameEditText = findViewById(R.id.create_username_edit_text);
        emailEditText = findViewById(R.id.create_email_edit_text);
        passwordEditText = findViewById(R.id.create_password_edit_text);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text);
        createAccountButton = findViewById(R.id.create_account_button);

        //get the database
        db = BondAppDatabase.getDatabase(getApplicationContext());

        //activate create account button
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCreateAccount();
            }
        });
    }

    //validates input and creates account
    private void handleCreateAccount() {
        //get input
        final String userName = usernameEditText.getText().toString().trim();
        final String emailAddress = emailEditText.getText().toString().trim();
        final String passwordHash = passwordEditText.getText().toString().trim();
        final String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        //validate
        if(TextUtils.isEmpty(userName) || TextUtils.isEmpty(emailAddress) ||
        TextUtils.isEmpty(passwordHash) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(CreateAccountPage.this, "Please fill out all fields.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (!passwordHash.equals(confirmPassword)) {
            Toast.makeText(CreateAccountPage.this, "Passwords do not match.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        //ADD THE HASHING OF PASSWORDS HERE. This part is incomplete!!

        //create new user
        final User newUser = new User(
                0,
                userName,
                emailAddress,
                passwordHash,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        //maybe need to add some validation to ensure no duplicate user names or emails??
        BondAppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.userDAO().insertUser(newUser);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CreateAccountPage.this, "Success! Account Created",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
    }
}