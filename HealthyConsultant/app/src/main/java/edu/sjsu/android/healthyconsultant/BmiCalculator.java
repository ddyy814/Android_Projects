package edu.sjsu.android.healthyconsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BmiCalculator extends AppCompatActivity {

    // init variables
    private EditText et1, et2;
    private TextView tv_result;
    private Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_calculator);

        et1 = (EditText)findViewById(R.id.ed_weight);
        et2 = (EditText)findViewById(R.id.ed_height);
        tv_result = (TextView)findViewById(R.id.tv_result);
        btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bmi();
            }
        });
    }

    // create method to calculate BMI
    private void bmi(){
        // init
        float a, b , c;
        // formula
        a = Float.parseFloat(et2.getText().toString()) / 100;
        b = Float.parseFloat(et1.getText().toString());
        c = b / (a * a);
        tv_result.setText("" + c);

        // determine standard of BMI
        if(c <= 18.5){
            Toast.makeText(getApplicationContext(), "You are underweight", Toast.LENGTH_SHORT).show();
        }
        if(c > 18.5 && c < 25){
            Toast.makeText(getApplicationContext(), "You are Normal", Toast.LENGTH_SHORT).show();
        }
        if(c >= 25){
            Toast.makeText(getApplicationContext(), "You are Overweight", Toast.LENGTH_SHORT).show();
        }
    }
}
