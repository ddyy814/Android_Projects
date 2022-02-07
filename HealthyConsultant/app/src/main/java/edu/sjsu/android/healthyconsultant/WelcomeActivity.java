package edu.sjsu.android.healthyconsultant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;



public class WelcomeActivity extends AppCompatActivity {

    private TextView welcomeMessage;
    ImageButton bmi_btn, cal_btn;
    Button meal_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        welcomeMessage = (TextView)findViewById(R.id.textView_welcome);
        welcomeMessage.setText("Welcome") ;

        bmi_btn = (ImageButton)findViewById(R.id.bmi_btn);
        cal_btn = (ImageButton)findViewById(R.id.cal_btn);
        meal_btn = (Button)findViewById(R.id.meal_btn);

        bmi_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, BmiCalculator.class);
                startActivity(intent);
            }
        });

        cal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, CalorieCalculator.class);
                startActivity(intent);
            }
        });

        meal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, MealPlan.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.calorie_intent:
                Intent newActivity = new Intent(WelcomeActivity.this, CalorieCalculator.class);
                startActivity(newActivity);
                return true;

            case R.id.bmi_intent:
                Intent calActivity = new Intent(WelcomeActivity.this, BmiCalculator.class);
                startActivity(calActivity);
                return true;

            case R.id.uninstall:
                Uri packageUri = Uri.parse("package edu.sjsu.android.healthyconsultant");
                Intent uninstallintent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE, packageUri);
                startActivity(uninstallintent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
