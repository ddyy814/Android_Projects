package edu.sjsu.android.healthyconsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignupActivity extends AppCompatActivity {
    private edu.sjsu.android.healthyconsultant.DatabaseHelper db;
    private EditText firstName, lastName, username, password;
    private Button signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        db = new edu.sjsu.android.healthyconsultant.DatabaseHelper(this);
        firstName = (EditText)findViewById(R.id.signup_firstName);
        lastName = (EditText)findViewById(R.id.signup_lastName);
        username = (EditText)findViewById(R.id.signup_un);
        password = (EditText)findViewById(R.id.signup_pw);
        signupBtn = (Button)findViewById(R.id.button_signup);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
    }

    private boolean validateFirstName(String s) {
        if (s.isEmpty()) {
            firstName.setText("");
            firstName.setHintTextColor(Color.RED);
            firstName.setHint("Please enter your first name");
            return false;
        }
        else if (!s.matches("[a-zA-Z]*")) {
            firstName.setText("");
            firstName.setHintTextColor(Color.RED);
            firstName.setHint("Invalid first name");
            return false;
        }
        firstName.setText(s);
        return true;
    }

    private boolean validateLastName(String s) {
        if (s.isEmpty()) {
            lastName.setText("");
            lastName.setHintTextColor(Color.RED);
            lastName.setHint("Please enter your last name");
            return false;
        }
        else if (!s.matches("[a-zA-Z]*")) {
            lastName.setText("");
            lastName.setHintTextColor(Color.RED);
            lastName.setHint("Invalid last name");
            return false;
        }
        lastName.setText(s);
        return true;
    }

    private boolean validateUsername(String s) {
        if (s.isEmpty()) {
            username.setText("");
            username.setHintTextColor(Color.RED);
            username.setHint("Please enter a username");
            return false;
        }
        else if (!s.matches("[a-zA-Z0-9]*")) {
            username.setText("");
            username.setHintTextColor(Color.RED);
            username.setHint("Use only letters/numbers");
            return false;
        }
        else if (db.existUsername(s)) {
            username.setText("");
            username.setHintTextColor(Color.RED);
            username.setHint("Username already exists");
            return false;
        }
        username.setText(s);
        return true;
    }

    private boolean validatePassword(String s) {
        if (s.isEmpty()) {
            password.setText("");
            password.setHintTextColor(Color.RED);
            password.setHint("Please enter a password");
            return false;
        }
        else if (s.length() < 8 || s.length() > 20) {
            password.setText("");
            password.setHintTextColor(Color.RED);
            password.setHint("Please use 8-20 characters");
            return false;
        }
        return true;
    }

    private void signup() {
        String input1 = firstName.getText().toString().trim();
        String input2 = lastName.getText().toString().trim();
        String input3 = username.getText().toString().trim();
        String input4 = password.getText().toString();
        validateFirstName(input1);
        validateLastName(input2);
        validateUsername(input3);
        validatePassword(input4);
        if (validateFirstName(input1) && validateLastName(input2) && validateUsername(input3) && validatePassword(input4)) {
            db.addUser(input3, input4, input1, input2);
            Intent intent = new Intent(this, WelcomeActivity.class);
         //  intent.putExtra("name", input1 + " " + input2);
            startActivity(intent);
        }
    }

    public void switchToLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


}
