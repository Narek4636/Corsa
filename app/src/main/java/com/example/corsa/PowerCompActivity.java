package com.example.corsa;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class PowerCompActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_comp);

        ImageView image1 = findViewById(R.id.image1_power_comp);
        ImageView image2 = findViewById(R.id.image2_power_comp);
        TextView name1 = findViewById(R.id.name1_power_comp);
        TextView name2 = findViewById(R.id.name2_power_comp);
        TextView year1 = findViewById(R.id.year1_power_comp);
        TextView year2 = findViewById(R.id.year2_power_comp);
        TextView power1str = findViewById(R.id.power1_power_comp);
        TextView power2str = findViewById(R.id.power2_power_comp);
        TextView hp1 = findViewById(R.id.hp1_power_comp);
        TextView hp2 = findViewById(R.id.hp2_power_comp);
        ImageView image1_black = findViewById(R.id.image1_black_power_comp);
        ImageView image2_black = findViewById(R.id.image2_black_power_comp);

        TextView right_price;
        TextView wrong_price;
        ImageView right_pic;
        ImageView wrong_pic;

        int[] images = {R.drawable.huracan_lp610, R.drawable.bmw_m1, R.drawable.sl65_black_series
                , R.drawable.golf_gti_mk7, R.drawable.tvr_sagaris, R.drawable.bac_mono,
                R.drawable.m4_f82, R.drawable.veyron_16_4, R.drawable.sls, R.drawable.abarth_595, R.drawable.aventador_700, R.drawable.c63_204,
                R.drawable.m5_e60, R.drawable.m5_e39, R.drawable.evo_6, R.drawable.e55_w210, R.drawable.mercedec_190e_eco_2, R.drawable.bmw_m3_e36_1996,
                R.drawable.m5_e34_38, R.drawable.escort_rs_cosworth, R.drawable.lancia_delta_hf_integrale_evo_2, R.drawable.golf_2_gti_g60, R.drawable.golf_5_r32,
                R.drawable.bmw_130i_2005, R.drawable.peugeot_406_coupe_v6};
        int[] hrprs = new int[]{610, 277, 670, 220, 412, 286, 431, 1001, 571, 180, 700, 457, 507, 400, 280, 354, 235, 321, 340, 227, 215, 160, 250, 258, 207};
        String[] names = new String[]{"LAMBORGHINI HURACAN LP610-4", "BMW M1"
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
            if (indexPic2 != indexPic1 && hrprs[indexPic1] != hrprs[indexPic2]) {
                break;
            }
        }
        image1.setImageResource(images[indexPic1]);
        image2.setImageResource(images[indexPic2]);
        name1.setText(names[indexPic1]);
        name2.setText(names[indexPic2]);
        year1.setText(Integer.toString(prod_years[indexPic1]));
        year2.setText(Integer.toString(prod_years[indexPic2]));

        int power1 = hrprs[indexPic1];
        int power2 = hrprs[indexPic2];

        power1str.setText(String.valueOf(hrprs[indexPic1]));
        power2str.setText(String.valueOf(hrprs[indexPic2]));

        if (power1 > power2) {
            right_price = power1str;
            right_pic = image1;
            wrong_price = power2str;
            wrong_pic = image2;
        } else {
            right_price = power2str;
            right_pic = image2;
            wrong_price = power1str;
            wrong_pic = image1;
        }
        right_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                right_price.setVisibility(View.VISIBLE);
                right_price.setTextColor(Color.GREEN);
                wrong_price.setVisibility(View.VISIBLE);
                image1_black.setVisibility(View.VISIBLE);
                image1_black.setAlpha(0.6f);
                image2_black.setAlpha(0.6f);
                hp1.setVisibility(View.VISIBLE);
                hp2.setVisibility(View.VISIBLE);
                Intent intent = new Intent(PowerCompActivity.this, PowerCompMidModeActivity.class);
                startActivity(intent);
            }
        });

        wrong_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                right_price.setVisibility(View.VISIBLE);
                right_price.setTextColor(Color.GREEN);
                wrong_price.setVisibility(View.VISIBLE);
                wrong_price.setTextColor(Color.RED);
                image1_black.setAlpha(0.6f);
                image2_black.setAlpha(0.6f);
                hp1.setVisibility(View.VISIBLE);
                hp2.setVisibility(View.VISIBLE);
                Intent intent = new Intent(PowerCompActivity.this, PowerCompMidModeActivity.class);
                startActivity(intent);
            }
        });
    }
}