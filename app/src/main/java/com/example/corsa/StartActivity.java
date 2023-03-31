package com.example.corsa;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.corsa.carRoom.CarEntity;
import com.example.corsa.viewModels.CarViewModel;
import com.example.corsa.modes.MainMenu;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class StartActivity extends AppCompatActivity {
    List<CarEntity> carList;
    public final static int NOT_NULL = 1;
    public static final String TEXT = "text";
    public static final String SHARED_PREFS = "sharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(StartActivity.this, MainMenu.class);
                startActivity(intent);
                finish();
            }
        }, 2200);

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

//        carViewModel.addCar(new CarEntity("LAMBORGHINI HURACAN LP610-4", 610, 2014, "729", 2.9, Utils.getDrawableBytes(StartActivity.this, R.drawable.huracan_lp610)));
//        carViewModel.addCar(new CarEntity("BMW M1", 277, 1978, "0", 6.2, Utils.getDrawableBytes(StartActivity.this, R.drawable.bmw_m1)));
//        carViewModel.addCar(new CarEntity("MERCEDES SL65 AMG BLACK SERIES", 670, 2009, "751", 3.8, Utils.getDrawableBytes(StartActivity.this, R.drawable.sl65_black_series)));
//        carViewModel.addCar(new CarEntity("VW GOLF GTI", 220, 2013, "829", 6.5, Utils.getDrawableBytes(StartActivity.this, R.drawable.golf_gti_mk7)));
//        carViewModel.addCar(new CarEntity("TVR SAGARIS", 412, 2005, "0", 3.9, Utils.getDrawableBytes(StartActivity.this, R.drawable.tvr_sagaris)));
//        carViewModel.addCar(new CarEntity("BAC MONO", 286, 2011, "0", 2.9, Utils.getDrawableBytes(StartActivity.this, R.drawable.bac_mono)));
//        carViewModel.addCar(new CarEntity("BMW M4", 431, 2014, "752", 2.9, Utils.getDrawableBytes(StartActivity.this, R.drawable.m4_f82)));
//        carViewModel.addCar(new CarEntity("BUGATTI VEYRON", 1001, 2005, "740", 2.5, Utils.getDrawableBytes(StartActivity.this, R.drawable.veyron_16_4)));
//        carViewModel.addCar(new CarEntity("MERCEDES SLS AMG", 571, 2010, "740", 3.9, Utils.getDrawableBytes(StartActivity.this, R.drawable.sls_amg)));
//        carViewModel.addCar(new CarEntity("ABARTH 595 COMPETIZIONE", 180, 2015, "0", 6.9, Utils.getDrawableBytes(StartActivity.this, R.drawable.abarth_595_comp)));
//        carViewModel.addCar(new CarEntity("LAMBORGHINI AVENTADOR LP700-4", 700, 2011, "725", 2.9, Utils.getDrawableBytes(StartActivity.this, R.drawable.aventador_700)));
//        carViewModel.addCar(new CarEntity("MERCEDES C63 AMG", 457, 2007, "805", 4.5, Utils.getDrawableBytes(StartActivity.this, R.drawable.c63_204)));
//        carViewModel.addCar(new CarEntity("BMW M5", 400, 1998, "820", 5.4, Utils.getDrawableBytes(StartActivity.this, R.drawable.m5_e39)));
//        carViewModel.addCar(new CarEntity("BMW M5", 507, 2005, "813", 4.5, Utils.getDrawableBytes(StartActivity.this, R.drawable.m5_e60)));
//        carViewModel.addCar(new CarEntity("BMW M5", 340, 1992, "0", 6.0, Utils.getDrawableBytes(StartActivity.this, R.drawable.m5_e34_38)));
//        carViewModel.addCar(new CarEntity("BMW M5", 560, 2011, "755", 4.3, Utils.getDrawableBytes(StartActivity.this, R.drawable.m5_f10)));
//        carViewModel.addCar(new CarEntity("LANCIA DELTA HF INTEGRALE EVO II", 215, 1994, "0", 5.7, Utils.getDrawableBytes(StartActivity.this, R.drawable.delta_integrale)));
//        carViewModel.addCar(new CarEntity("FORD ESCORT RS COSWORTH", 227, 1994, "0", 6.1, Utils.getDrawableBytes(StartActivity.this, R.drawable.escort_cosworth)));
//        carViewModel.addCar(new CarEntity("VW GOLF R32", 250, 2005, "0", 6.2, Utils.getDrawableBytes(StartActivity.this, R.drawable.golf_r32_mk5)));
//        carViewModel.addCar(new CarEntity("VW GOLF GTI G60", 160, 1990, "0", 8.3, Utils.getDrawableBytes(StartActivity.this, R.drawable.golf_g60)));
//        carViewModel.addCar(new CarEntity("MERCEDES 190E 2.5-16V EVO II", 235, 1990, "0", 7.1, Utils.getDrawableBytes(StartActivity.this, R.drawable.merc_190e_evo2)));
//        carViewModel.addCar(new CarEntity("BMW 130i", 258, 2005, "0", 5.8, Utils.getDrawableBytes(StartActivity.this, R.drawable.bmw_130i_2005)));
//        carViewModel.addCar(new CarEntity("MERCEDES E55 AMG", 354, 1999, "0", 5.6, Utils.getDrawableBytes(StartActivity.this, R.drawable.e55_w210)));
        Log.d("TAG", "Load time --> " + Long.toString(System.currentTimeMillis() - st) + " millis");
    }
}