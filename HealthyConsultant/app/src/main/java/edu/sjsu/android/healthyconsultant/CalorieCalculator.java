package edu.sjsu.android.healthyconsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class CalorieCalculator extends AppCompatActivity{


    private EditText age_text;
    private EditText weight_text;
    private EditText height_text;
    private TextView result_bmr_text;
    private TextView result_cal_text;
    private Button save_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_calculator);

        age_text = (EditText) findViewById(R.id.age_ed);
        weight_text = (EditText) findViewById(R.id.weight_ed);
        height_text = (EditText) findViewById(R.id.height_ed);
        result_bmr_text = (TextView) findViewById(R.id.bmr_result_tx);
        result_cal_text = (TextView) findViewById(R.id.calorie_result_tx);

        save_btn = (Button)findViewById(R.id.button_save);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.UHeight, ((EditText)findViewById(R.id.height_ed)).getText().toString());
                values.put(DatabaseHelper.UWeight, ((EditText)findViewById(R.id.weight_ed)).getText().toString());
                values.put(DatabaseHelper.UAge, ((EditText)findViewById(R.id.age_ed)).getText().toString());
                Uri uri = getContentResolver().insert(UserContentProvider.CONTENT_URI,values);
                Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_SHORT).show();
            }
        });



    }

    public void onClick(View view) {
        final double weight, height, bmr_male, bmr_female, cal_male, cal_female;
        int  age;

        switch (view.getId()) {
            case R.id.calculate_btn:
                RadioButton male_btn = (RadioButton) findViewById(R.id.male_button);

                RadioButton sed_btn = (RadioButton) findViewById(R.id.sed_btn);
                RadioButton light_btn = (RadioButton) findViewById(R.id.lightly_btn);
                RadioButton moderate_btn = (RadioButton) findViewById(R.id.moderately_btn);
                RadioButton very_btn = (RadioButton) findViewById(R.id.very_btn);
                RadioButton extra_btn = (RadioButton) findViewById(R.id.extra_btn);

                if (age_text.getText().length() == 0 && weight_text.getText().length() == 0 && height_text
                        .getText().length() == 0) {
                    Toast.makeText(this, "Please enter a valid number.", Toast.LENGTH_LONG).show();
                    return;
                }

                   weight = Double.parseDouble(weight_text.getText().toString());
                   height = Double.parseDouble(height_text.getText().toString());
                    age = Integer.parseInt(age_text.getText().toString());

                bmr_male = (10 * weight) + (6.25 * height) - (5 * age) + 5;
                bmr_female = (10 * weight) + (6.25 * height) - (5 * age) - 161;

                if (male_btn.isChecked()) {
                    if (sed_btn.isChecked()) {
                        cal_male = bmr_male * 1.2;
                        result_bmr_text.setText(String.valueOf(bmr_male));
                        result_cal_text.setText(String.valueOf(cal_male));
                        break;
                    }
                    if (light_btn.isChecked()) {
                        cal_male = bmr_male * 1.375;
                        result_bmr_text.setText(String.valueOf(bmr_male));
                        result_cal_text.setText(String.valueOf(cal_male));
                        break;
                    }
                    if (moderate_btn.isChecked()) {
                        cal_male = bmr_male * 1.55;
                        result_bmr_text.setText(String.valueOf(bmr_male));
                        result_cal_text.setText(String.valueOf(cal_male));
                        break;
                    }
                    if (very_btn.isChecked()) {
                        cal_male = bmr_male * 1.725;
                        result_bmr_text.setText(String.valueOf(bmr_male));
                        result_cal_text.setText(String.valueOf(cal_male));
                        break;
                    }
                    if (extra_btn.isChecked()) {
                        cal_male = bmr_male * 1.9;
                        result_bmr_text.setText(String.valueOf(bmr_male));
                        result_cal_text.setText(String.valueOf(cal_male));
                        break;
                    }
                } else if (sed_btn.isChecked()) {
                    cal_female = bmr_female * 1.2;
                    result_bmr_text.setText(String.valueOf(bmr_female));
                    result_cal_text.setText(String.valueOf(cal_female));
                    break;
                }
                if (light_btn.isChecked()) {
                    cal_female = bmr_female * 1.375;
                    result_bmr_text.setText(String.valueOf(bmr_female));
                    result_cal_text.setText(String.valueOf(cal_female));
                    break;
                }
                if (moderate_btn.isChecked()) {
                    cal_female = bmr_female * 1.55;
                    result_bmr_text.setText(String.valueOf(bmr_female));
                    result_cal_text.setText(String.valueOf(cal_female));
                    break;
                }
                if (very_btn.isChecked()) {
                    cal_female = bmr_female * 1.725;
                    result_bmr_text.setText(String.valueOf(bmr_female));
                    result_cal_text.setText(String.valueOf(cal_female));
                    break;
                }
                if (extra_btn.isChecked()) {
                    cal_female = bmr_female * 1.9;
                    result_bmr_text.setText(String.valueOf(bmr_female));
                    result_cal_text.setText(String.valueOf(cal_female));
                    break;
                }
        }
    }
}
