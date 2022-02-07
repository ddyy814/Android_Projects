package edu.sjsu.android.healthyconsultant;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import edu.sjsu.android.healthyconsultant.R;
import edu.sjsu.android.healthyconsultant.SignupActivity;
import edu.sjsu.android.healthyconsultant.WelcomeActivity;

public class LoginActivity extends AppCompatActivity {
    private edu.sjsu.android.healthyconsultant.DatabaseHelper db;
    private EditText username, password;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new edu.sjsu.android.healthyconsultant.DatabaseHelper(this);
        username = (EditText) findViewById(R.id.login_un);
        password = (EditText) findViewById(R.id.login_pw);
        loginBtn = (Button)findViewById(R.id.button_login);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    private boolean validateUsername(String s) {
        if (s.isEmpty()) {
            username.setHintTextColor(Color.RED);
            username.setHint("Please enter a username");
            return false;
        }
        else if (!db.existUsername(s)) {
            username.setText("");
            username.setHintTextColor(Color.RED);
            username.setHint("Wrong username or password");
            password.setText("");
            password.setHintTextColor(Color.RED);
            password.setHint("Wrong username or password");
            return false;
        }
        return true;
    }

    private boolean validatePassword(String un, String pw) {
        if (pw.isEmpty()) {
            password.setHintTextColor(Color.RED);
            password.setHint("Please enter a password");
            return false;
        }
        else if (!db.checkPassword(un, pw)) {
            username.setText("");
            username.setHintTextColor(Color.RED);
            username.setHint("Wrong username or password");
            password.setText("");
            password.setHintTextColor(Color.RED);
            password.setHint("Wrong username or password");
            return false;
        }
        return true;
    }

    private void login() {
        String input1 = username.getText().toString();
        String input2 = password.getText().toString();
        if (validateUsername(input1) && validatePassword(input1, input2)) {
            String fullName = db.getFullName(input1);
            Intent intent = new Intent(this, WelcomeActivity.class);
            intent.putExtra("name", fullName);
            startActivity(intent);
        }
    }

    public void switchToSignup(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
}
