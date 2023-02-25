package com.example.corsa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Random;

public class RandomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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