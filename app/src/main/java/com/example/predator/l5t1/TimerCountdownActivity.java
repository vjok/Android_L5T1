package com.example.predator.l5t1;


import android.content.Intent;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TimerCountdownActivity extends AppCompatActivity implements Serializable {


    ArrayList<Part> workouts = new ArrayList<Part>();
    int currentWorkoutIndex = 0;
    TextView twType;
    TextToSpeech mTTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_countdown);

        final Intent intent = getIntent();

        workouts = (ArrayList<Part>) intent.getSerializableExtra(MainActivity.EXTRA_MESSAGE);
        addSpeech();

        startTimer();

    }



    void startTimer()
    {
        final TextView twTime = findViewById(R.id.twTimer);
        final TextView twType = findViewById(R.id.twType);

        final Part current = workouts.get(currentWorkoutIndex);
        final int time = current.getTime();
        final String title = current.getName();
        final String speech = title + Integer.toString(time) + "seconds";

        mTTS.setPitch(0.1f);
        mTTS.setSpeechRate(0.5f);
        mTTS.speak(speech, TextToSpeech.QUEUE_FLUSH, null);

        new CountDownTimer(time*1000, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished) {
                twTime.setText(""+millisUntilFinished /1000);
                twType.setText(title);
            }

            @Override
            public void onFinish() {
                currentWorkoutIndex++;
                if (currentWorkoutIndex <= workouts.size()) {
                    startTimer();
                }
                else{
                    finish();
                }
            }
        }  .start();
    }


    void addSpeech()
    {
        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS)
                {
                    int result = mTTS.setLanguage(Locale.CANADA);

                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
                    {
                        //error
                    }
                }

            }
        });
    }


}
