package com.samsung.corsa.modes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.corsa.R;

import java.util.Random;

public class RandomActivity extends AppCompatActivity {

    public static final String RANDOM_PREFS = "RANDOM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        Random rand = new Random();
        Class<?>[] activities = {CarGuessActivity.class, AccelCompActivity.class, NurbCompActivity.class,
                PowerGuessActivity.class, PowerCompActivity.class, ProductionGuessActivity.class};
        Class<?> Activity = activities[rand.nextInt(activities.length)];

        Intent intent = new Intent(this, Activity);
        intent.putExtra("previousActivity", getClass().getName());
        startActivityForResult(intent, 1);
        finish();
    }
}