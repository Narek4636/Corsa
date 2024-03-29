package com.samsung.corsa.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;

import androidx.appcompat.app.AppCompatActivity;

import com.samsung.corsa.JsonCarList;
import com.example.corsa.R;
import com.samsung.corsa.carRoom.CarEntity;
import com.samsung.corsa.viewModels.CarViewModel;
import com.google.gson.Gson;
import com.samsung.corsa.modes.ProductionGuessActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class StartActivity extends AppCompatActivity {
    List<CarEntity> carList;
    public static final String TEXT = "text";
    public static final String SHARED_PREFS = "sharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        View rootView = getWindow().getDecorView().getRootView();

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(200);
        rootView.startAnimation(alphaAnimation);


        SharedPreferences preferences = getSharedPreferences(ProductionGuessActivity.PROFILE_PREFS, MODE_PRIVATE);
        Log.i("TAGAVOR", String.valueOf(preferences.getBoolean("Logged", false)));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3500);

        CarViewModel carViewModel = new CarViewModel(getApplication());
//        carViewModel.deleteAllCars();
        long st = System.currentTimeMillis();

        Gson gson = new Gson();
        String json = null;
        int version = -1;

        try {
            InputStream inputStream = getAssets().open("cars.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");

            JsonCarList carList = gson.fromJson(json, JsonCarList.class);
            List<CarEntity> cars = carList.getCars();

            Log.d("TAG", "Version --> " + carList.getVersion());
            Log.d("TAG", "Login Status --> " + carList.getLoginStatus());

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            int savedVersion = sharedPreferences.getInt("version", -1);

            if (savedVersion == carList.getVersion()) {
                // Version number is the same, do not add to ViewModel
            } else {
                for (CarEntity car : cars) {
                    carViewModel.addCar(car);
                }
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("version", carList.getVersion());
                editor.apply();
            }

//------------------------------------------------------------
            for (CarEntity carEntity : cars) {
                Log.d("TAG", "Name: " + carEntity.name);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Log.d("TAG", "Load time --> " + Long.toString(System.currentTimeMillis() - st) + " millis");
    }
}