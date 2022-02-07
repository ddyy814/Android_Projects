package edu.sjsu.android.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button buttonPlus, buttonMinus, buttonMultiply, buttonDivide, buttonEqual, buttonC;
    EditText result;

    double valueOne, valueTwo;
    boolean valueAddition, valueSubtract, valueMultiply, valueDivision;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonPlus = (Button) findViewById(R.id.plus);
        buttonMinus = (Button) findViewById(R.id.minus);
        buttonMultiply = (Button) findViewById(R.id.multiply);
        buttonDivide = (Button) findViewById(R.id.divide);
        buttonEqual = (Button) findViewById(R.id.equal);
        buttonC = (Button) findViewById(R.id.clean);
        result = (EditText) findViewById(R.id.results);

        buttonPlus.setOnClickListener(v -> {
                valueOne = Double.parseDouble(result.getText() + "");
                valueAddition = true;
                result.setText(null);

        });

        buttonMinus.setOnClickListener(v -> {
                valueOne = Double.parseDouble(result.getText() + "");
                valueSubtract = true;
                result.setText("");
            }
        );
        buttonMultiply.setOnClickListener(v -> {
            valueOne = Double.parseDouble(result.getText() + "");
            valueMultiply = true;
            result.setText("");
        });
        buttonDivide.setOnClickListener(v -> {
            valueOne = Double.parseDouble(result.getText() + "");
            valueDivision = true;
            result.setText("");
        });
        buttonEqual.setOnClickListener(v -> {
            valueTwo = Double.parseDouble(result.getText() + "");
            if(valueAddition){
                result.setText(String.valueOf(valueOne + valueTwo).replace(".0", ""));
                valueAddition = false;
            }
            if(valueSubtract){
                result.setText(String.valueOf(valueOne - valueTwo).replace(".0", ""));
                valueSubtract = false;
            }
            if(valueMultiply){
                result.setText(String.valueOf(valueOne * valueTwo).replace(".0", ""));
                valueMultiply = false;
            }

            if(valueDivision){
                result.setText(String.valueOf(valueOne / valueTwo).replace(".0", ""));
                valueDivision = false;
            }
        });
        buttonC.setOnClickListener(v -> result.setText(""));
    }

}