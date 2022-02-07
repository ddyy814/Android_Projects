package edu.sjsu.android.healthyconsultant;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;

public class MealPlan extends AppCompatActivity{

    EditText height_ed, calorie_ed;
    ImageView imageView;
    Button suggestion_btn;
    DatabaseHelper databaseHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meal_plan);

        height_ed = (EditText)findViewById(R.id.height_input);
        calorie_ed = (EditText)findViewById(R.id.cal_input);
        imageView = (ImageView)findViewById(R.id.suggestion_view);

        suggestion_btn = (Button)findViewById(R.id.suggestion_btn);

        suggestion_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setImageBitmap(databaseHelper.getImage(171));
            }
        });
    }
}
