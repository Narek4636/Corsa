package com.example.corsa.modes;

import static com.example.corsa.modes.CarGuessActivity.DELAY_GUESS;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.corsa.R;
import com.example.corsa.Utils;
import com.google.android.material.slider.Slider;

import java.util.Objects;
import java.util.Random;

public class PowerGuessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_guess);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        Slider slider = findViewById(R.id.slider_power_guess);
        TextView submit = findViewById(R.id.submit_power_guess);
        TextView power = findViewById(R.id.power_power_guess);
        TextView name = findViewById(R.id.name_power_guess);
        TextView prod_year = findViewById(R.id.year_power_guess);
        TextView right = findViewById(R.id.right_ans_power_guess);
        TextView wrong = findViewById(R.id.wrong_ans_power_guess);
        TextView answer = findViewById(R.id.right_power_power_guess);
        ImageView image = findViewById(R.id.image_power_guess);
        ImageView image_black = findViewById(R.id.image_black_power_guess);
        TextView menu = findViewById(R.id.return_button_power_guess);

        int[] images = {R.drawable.huracan_lp610, R.drawable.bmw_m1, R.drawable.sl65_black_series
                , R.drawable.golf_gti_mk7, R.drawable.tvr_sagaris, R.drawable.bac_mono,
                R.drawable.m4_f82, R.drawable.veyron_16_4, R.drawable.test_pic, R.drawable.abarth_595_comp};
        int[] hrprs = new int[]{610, 277, 670, 220, 412, 286, 431, 1001, 571, 180};
        String[] names = new String[]{"LAMBORGHINI HURACAN LP610-4", "BMW M1"
                , "MERCEDES SL65 AMG BLACK SERIES", "VW GOLF GTI", "TVR SAGARIS",
        "BAC MONO", "BMW M4", "BUGATTI VEYRON 16.4", "MERCEDES-BENZ SLS AMG", "ABARTH 595 COMPETIZIONE"};
        int[] prod_years = new int[]{2014, 1978, 2009, 2013, 2005, 2011, 2014, 2005, 2010, 2015};

        Random r = new Random();
        int i = r.nextInt(images.length);
        image.setImageResource(images[i]);
        name.setText(names[i]);
        prod_year.setText(String.valueOf(prod_years[i]));
        power.setText("100");

        slider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                power.setText(String.valueOf(value).substring(0,String.valueOf(value).length()-2));
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.vibrate(PowerGuessActivity.this);

                Intent intent = new Intent(PowerGuessActivity.this, MainMenu.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.vibrate(PowerGuessActivity.this);

                float x = slider.getValue();
                if(Math.abs(hrprs[i] - x) <= 50){
                    right.setVisibility(View.VISIBLE);
                    answer.setText(Integer.toString(hrprs[i]));
                    answer.setTextColor(Color.GREEN);
                    image_black.setVisibility(View.VISIBLE);
                }
                else{
                    power.setTextColor(Color.RED);
                    wrong.setVisibility(View.VISIBLE);
                    answer.setText(Integer.toString(hrprs[i]));
                    image_black.setVisibility(View.VISIBLE);
                }
                if(!Objects.equals(getIntent().getStringExtra("previousActivity"), "PowerGuessActivity") && !Objects.equals(getIntent().getStringExtra("previousActivity"), "adapter")) {
                    Random rand = new Random();
                    Class<?>[] activities = {CarGuessActivity.class, AccelCompActivity.class, NurbCompActivity.class,
                            PowerGuessActivity.class, PowerCompActivity.class, ProductionGuessActivity.class};
//
//                       RANDOMIC MTNELU HAMAR!!!!!!!!!!!!!!!!!!!!!!!!!
//
                    Class<?> Activity;
                    while (true) {
                        Activity = activities[rand.nextInt(activities.length)];
                        if (Activity != PowerGuessActivity.class) {
                            break;
                        }
                    }

                    Intent intent = new Intent(PowerGuessActivity.this, Activity);
                    intent.putExtra("previousActivity", "PowerGuessActivity");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                                    finish();
                                }
                            }, DELAY_GUESS+200);
                        }
                    });
                }
                else {
                    Intent intent = new Intent(PowerGuessActivity.this, PowerGuessActivity.class);
                    intent.putExtra("previousActivity", "PowerGuessActivity");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                                    finish();
                                }
                            }, DELAY_GUESS+200);
                        }
                    });
                }
            }
        });
    }
}