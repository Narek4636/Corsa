package com.example.corsa;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class AccelCompActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accel_comp);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        ImageView image1 = findViewById(R.id.image1_accel_comp);
        ImageView image2 = findViewById(R.id.image2_accel_comp);
        TextView name1 = findViewById(R.id.name1_accel_comp);
        TextView name2 = findViewById(R.id.name2_accel_comp);
        TextView year1 = findViewById(R.id.year1_accel_comp);
        TextView year2 = findViewById(R.id.year2_accel_comp);
        TextView sec1 = findViewById(R.id.sec1_accel_comp);
        TextView sec2 = findViewById(R.id.sec2_accel_comp);
        TextView time1str = findViewById(R.id.time1_accel_comp);
        TextView time2str = findViewById(R.id.time2_accel_comp);
        ImageView image1_black = findViewById(R.id.image1_black_accel_comp);
        ImageView image2_black = findViewById(R.id.image2_black_accel_comp);
        TextView menu = findViewById(R.id.return_button_accel_comp);

        TextView right_price;
        TextView wrong_price;
        ImageView right_pic;
        ImageView wrong_pic;

        List<AccelComp> items = new ArrayList<AccelComp>();

        items.add(new AccelComp(R.drawable.huracan_lp610, 2.9, "2.9", "LAMBORGHINI HURACAN LP610-4", 2014));
        items.add(new AccelComp(R.drawable.bmw_m1, 6.2, "6.2", "BMW M1", 1978));
        items.add(new AccelComp(R.drawable.sl65_black_series, 3.8, "3.8", "MERCEDES SL65 AMG BLACK SERIES", 2009));
        items.add(new AccelComp(R.drawable.sl65_black_series, 3.8, "3.8", "MERCEDES SL65 AMG BLACK SERIES", 2009));

        int[] images = {R.drawable.huracan_lp610, R.drawable.bmw_m1, R.drawable.sl65_black_series
                , R.drawable.golf_gti_mk7, R.drawable.tvr_sagaris, R.drawable.bac_mono,
                R.drawable.m4_f82, R.drawable.veyron_16_4, R.drawable.sls, R.drawable.abarth_595, R.drawable.aventador_700, R.drawable.c63_204,
                R.drawable.m5_e60, R.drawable.m5_e39, R.drawable.evo_6, R.drawable.e55_w210, R.drawable.mercedec_190e_eco_2, R.drawable.bmw_m3_e36_1996,
                R.drawable.m5_e34_38, R.drawable.escort_rs_cosworth, R.drawable.lancia_delta_hf_integrale_evo_2, R.drawable.golf_2_gti_g60, R.drawable.golf_5_r32,
                R.drawable.bmw_130i_2005, R.drawable.peugeot_406_coupe_v6};
        double[] times = new double[]{2.9, 6.2, 3.8, 6.5, 3.7, 2.9, 4.2, 2.5, 3.9, 6.9, 2.9, 4.6, 4.5, 5.2, 5.0, 5.6, 7.3, 5.5,
                5.9, 6.6, 6.6, 8.3, 6.2, 5.8, 7.8};
        String[] timesstr = new String[]{"2.9", "6.2", "3.8", "6.5", "3.7", "2.9", "4.2", "2.5", "3.9", "6.9", "2.9", "4.6", "4.5", "5.2", "5.0", "5.6",
                "7.3", "5.5", "5.9", "6.6", "6.6", "8.3", "6.2", "5.8", "7.8"};
        String[] names = new String[]{"LAMBORGHINI HURACAN LP610-4", "fBMW M1"
                , "MERCEDES SL65 AMG BLACK SERIES", "VW GOLF GTI", "TVR SAGARIS", "BAC MONO",
                "BMW M4", "BUGATTI VEYRON", "MERCEDES SLS AMG", "ABARTH 595 COMPETIZIONE",
                "LAMBORGHINI AVENTADOR LP700-4", "MERCEDES C63 AMG", "BMW M5", "BMW M5", "MITSUBISHI LANCER EVO VI", "MERCEDES E55 AMG",
                "MERCEDES 190E 2.5-16V EVO II", "BMW M3", "BMW M5 3.8", "FORD ESCORT RS COSWORTH", "LANCIA DELTA HF INTEGRALE EVO II",
                "VW GOLF GTI G60", "VW GOLF R32", "BMW 130i", "PEUGEOT 406 COUPE V6"};
        int[] prod_years = new int[]{2014, 1978, 2009, 2013, 2005, 2011, 2014, 2005, 2010, 2015, 2011, 2007, 2005, 1998, 1999, 1999,
                1990, 1996, 1992, 1994, 1994, 1990, 2005, 2005, 2000};

        Random rand = new Random();

        int indexPic1 = rand.nextInt(images.length);
        int indexPic2;
        while (true) {
            indexPic2 = rand.nextInt(images.length);
            if (indexPic2 != indexPic1 && times[indexPic2] != times[indexPic1]) {
                break;
            }
        }
        image1.setImageResource(images[indexPic1]);
        image2.setImageResource(images[indexPic2]);
        name1.setText(names[indexPic1]);
        name2.setText(names[indexPic2]);
        year1.setText(Integer.toString(prod_years[indexPic1]));
        year2.setText(Integer.toString(prod_years[indexPic2]));

        double time1 = times[indexPic1];
        double time2 = times[indexPic2];

        time1str.setText(timesstr[indexPic1]);
        time2str.setText(timesstr[indexPic2]);

        if (time1 < time2) {
            right_price = time1str;
            right_pic = image1;
            wrong_price = time2str;
            wrong_pic = image2;
        } else {
            right_price = time2str;
            right_pic = image2;
            wrong_price = time1str;
            wrong_pic = image1;
        }

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccelCompActivity.this, MainMenu.class);
                startActivity(intent);
                finish();
            }
        });

        right_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                right_price.setVisibility(View.VISIBLE);
                right_price.setTextColor(Color.GREEN);
                wrong_price.setVisibility(View.VISIBLE);
                image1_black.setAlpha(0.7f);
                image2_black.setAlpha(0.7f);
                sec1.setVisibility(View.VISIBLE);
                sec2.setVisibility(View.VISIBLE);

                if(!Objects.equals(getIntent().getStringExtra("previousActivity"), "AccelCompActivity") && !Objects.equals(getIntent().getStringExtra("previousActivity"), "adapter")) {
                    Random rand = new Random();
                    Class<?>[] activities = {CarGuessActivity.class, AccelCompActivity.class, NurbCompActivity.class,
                            PowerGuessActivity.class, PowerCompActivity.class, ProductionGuessActivity.class};
//
//                       RANDOMIC MTNELU HAMAR!!!!!!!!!!!!!!!!!!!!!!!!!
//
                    Class<?> Activity;
                    while (true) {
                        Activity = activities[rand.nextInt(activities.length)];
                        if (Activity != AccelCompActivity.class) {
                            break;
                        }
                    }

                    Intent intent = new Intent(AccelCompActivity.this, Activity);
                    intent.putExtra("previousActivity", "AccelCompActivity");
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent i = new Intent(AccelCompActivity.this, AccelCompActivity.class);
                    i.putExtra("previousActivity", "AccelCompActivity");
                    startActivity(i);
                    finish();
                }
            }
        });
        wrong_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                right_price.setVisibility(View.VISIBLE);
                right_price.setTextColor(Color.GREEN);
                wrong_price.setVisibility(View.VISIBLE);
                wrong_price.setTextColor(Color.RED);
                image1_black.setAlpha(0.7f);
                image2_black.setAlpha(0.7f);
                sec1.setVisibility(View.VISIBLE);
                sec2.setVisibility(View.VISIBLE);

                if(!Objects.equals(getIntent().getStringExtra("previousActivity"), "AccelCompActivity") && !Objects.equals(getIntent().getStringExtra("previousActivity"), "adapter")) {
                    Random rand = new Random();
                    Class<?>[] activities = {CarGuessActivity.class, AccelCompActivity.class, NurbCompActivity.class,
                            PowerGuessActivity.class, PowerCompActivity.class, ProductionGuessActivity.class};
//
//                       RANDOMIC MTNELU HAMAR!!!!!!!!!!!!!!!!!!!!!!!!!
//
                    Class<?> Activity;
                    while (true) {
                        Activity = activities[rand.nextInt(activities.length)];
                        if (Activity != AccelCompActivity.class) {
                            break;
                        }
                    }

                    Intent intent = new Intent(AccelCompActivity.this, Activity);
                    intent.putExtra("previousActivity", "AccelCompActivity");
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent i = new Intent(AccelCompActivity.this, AccelCompActivity.class);
                    i.putExtra("previousActivity", "AccelCompActivity");
                    startActivity(i);
                    finish();
                }
            }
        });
    }
}