package com.example.predator.l5t1;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

public class AddNewPartActivity extends AppCompatActivity {

    EditText editTextDuration;
    public static final String timeMessage = "com.example.predator.l4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_part);
        final RadioButton radioBtnWorkout = findViewById(R.id.btnWorkout);
        final RadioButton radioBtnPause = findViewById(R.id.btnPause);
        editTextDuration = findViewById(R.id.twTime);





        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int eventDuration;
                final String editTextTime = editTextDuration.getText().toString();
                eventDuration = Integer.parseInt(editTextTime);

                if (radioBtnWorkout.isChecked())
                {
                    Workout workout = new Workout("Workout", eventDuration);
                    returnData(workout);
                }

                if (radioBtnPause.isChecked())
                {
                    Pause pause = new Pause("Pause", eventDuration);
                    returnData(pause);
                }
            }

        });
    }


    private void returnData(Part data)
    {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("PART", data);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

}