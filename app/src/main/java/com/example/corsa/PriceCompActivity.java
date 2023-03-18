package com.example.corsa;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.corsa.modes.MainMenu;

import java.util.Random;

public class PriceCompActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_comp);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        ImageView image1 = findViewById(R.id.image1_price_comp);
        ImageView image2 = findViewById(R.id.image2_price_comp);
        TextView name1 = findViewById(R.id.name1_price_comp);
        TextView name2 = findViewById(R.id.name2_price_comp);
        TextView year1 = findViewById(R.id.year1_price_comp);
        TextView year2 = findViewById(R.id.year2_price_comp);
        ImageView image1_black = findViewById(R.id.image1_black_price_comp);
        ImageView image2_black = findViewById(R.id.image2_black_price_comp);
        TextView price1str = findViewById(R.id.price1_price_comp);
        TextView price2str = findViewById(R.id.price2_price_comp);
        TextView menu = findViewById(R.id.return_button_price_comp);

        TextView right_price;
        TextView wrong_price;
        ImageView right_pic;
        ImageView wrong_pic;

//        gnery prcnel

        int[] images = {R.drawable.huracan_lp610, R.drawable.bmw_m1, R.drawable.sl65_black_series
                , R.drawable.golf_gti_mk7, R.drawable.tvr_sagaris, R.drawable.bac_mono,
                R.drawable.m4_f82, R.drawable.veyron_16_4, R.drawable.test_pic, R.drawable.abarth_595_comp, R.drawable.aventador_700, R.drawable.c63_204,
                R.drawable.m5_e60, R.drawable.m5_e39, R.drawable.evo_6, R.drawable.e55_w210, R.drawable.merc_190e_evo2, R.drawable.test_pic,
                R.drawable.m5_e34_38, R.drawable.test_pic, R.drawable.test_pic, R.drawable.golf_g60, R.drawable.golf_r32_mk5,
                R.drawable.bmw_130i_2005, R.drawable.test_pic};
        int[] prices = new int[]{250000, 450000
                , 320000, 30000, 90000, 150000, 62000, 2000000, 225000, 33000, 240000, 30000, 29000, 32500, 37000, 15000};
        String[] pricesstr = new String[]{"250.000", "450.000"
                , "320.000", "30.000", "90.000", "150.000", "62.000", "2.000.000", "225.000", "33.000", "240.000", "30.000", "29.000", "32.500", "37.000",
        "15.000"};
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
        while(true){
            indexPic2 = rand.nextInt(images.length);
            if(indexPic2 != indexPic1){
                break;
            }
        }
        image1.setImageResource(images[indexPic1]);
        image2.setImageResource(images[indexPic2]);
        name1.setText(names[indexPic1]);
        name2.setText(names[indexPic2]);
        year1.setText(Integer.toString(prod_years[indexPic1]));
        year2.setText(Integer.toString(prod_years[indexPic2]));

        int price1 = prices[indexPic1];
        int price2 = prices[indexPic2];

        price1str.setText(pricesstr[indexPic1] + "$");
        price2str.setText(pricesstr[indexPic2] + "$");

        if(price1 > price2){
            right_price = price1str;
            right_pic = image1;
            wrong_price = price2str;
            wrong_pic = image2;
        }
        else{
            right_price = price2str;
            right_pic = image2;
            wrong_price = price1str;
            wrong_pic = image1;
        }
        right_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                right_price.setVisibility(View.VISIBLE);
                right_price.setTextColor(Color.GREEN);
                wrong_price.setVisibility(View.VISIBLE);
                image1_black.setAlpha(0.6f);
                image2_black.setAlpha(0.6f);
                Intent intent = new Intent(PriceCompActivity.this, PriceCompActivity.class);
                startActivity(intent);
                finish();
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PriceCompActivity.this, MainMenu.class);
                startActivity(intent);
                finish();
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
                Intent intent = new Intent(PriceCompActivity.this, PriceCompActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}