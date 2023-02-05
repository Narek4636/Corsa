package com.example.corsa;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class NurbCompActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurb_comp);

        ImageView image1 = findViewById(R.id.image1_nurb_comp);
        ImageView image2 = findViewById(R.id.image2_nurb_comp);
        TextView name1 = findViewById(R.id.name1_nurb_comp);
        TextView name2 = findViewById(R.id.name2_nurb_comp);
        TextView year1 = findViewById(R.id.year1_nurb_comp);
        TextView year2 = findViewById(R.id.year2_nurb_comp);
        TextView ms1 = findViewById(R.id.ms1_nurb_comp);
        TextView ms2 = findViewById(R.id.ms2_nurb_comp);
        TextView time1str = findViewById(R.id.time1_nurb_comp);
        TextView time2str = findViewById(R.id.time2_nurb_comp);
        ImageView image1_black = findViewById(R.id.image1_black_nurb_comp);
        ImageView image2_black = findViewById(R.id.image2_black_nurb_comp);

        TextView right_price;
        TextView wrong_price;
        ImageView right_pic;
        ImageView wrong_pic;

        int[] images = {R.drawable.huracan_lp610, R.drawable.sl65_black_series
                , R.drawable.golf_gti_mk7, R.drawable.m4_f82, R.drawable.veyron_16_4,
                R.drawable.sls, R.drawable.aventador_700};
        int[] times = new int[]{728, 751, 829, 752, 740, 742, 725};
        String[] timesstr = new String[]{"7:28", "7:51", "8:29", "7:52", "7:40", "7:42", "7:25"};
        String[] names = new String[]{"LAMBORGHINI HURACAN LP610-4", "MERCEDES SL65 AMG BLACK SERIES",
                "VW GOLF GTI", "BMW M4", "BUGATTI VEYRON", "MERCEDES SLS AMG",
                "LAMORGHINI AVENTADOR LP700-4"};
        int[] prod_years = new int[]{2014, 2009, 2013, 2014, 2005, 2010, 2011};

        Random rand = new Random();

        int indexPic1 = rand.nextInt(images.length);
        int indexPic2;
        while (true) {
            indexPic2 = rand.nextInt(images.length);
            if (indexPic2 != indexPic1) {
                break;
            }
        }
        image1.setImageResource(images[indexPic1]);
        image2.setImageResource(images[indexPic2]);
        name1.setText(names[indexPic1]);
        name2.setText(names[indexPic2]);
        year1.setText(Integer.toString(prod_years[indexPic1]));
        year2.setText(Integer.toString(prod_years[indexPic2]));

        int time1 = times[indexPic1];
        int time2 = times[indexPic2];

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


        right_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                right_price.setVisibility(View.VISIBLE);
                right_price.setTextColor(Color.GREEN);
                wrong_price.setVisibility(View.VISIBLE);
                image1_black.setAlpha(0.6f);
                image2_black.setAlpha(0.6f);
                ms1.setVisibility(View.VISIBLE);
                ms2.setVisibility(View.VISIBLE);
                Intent intent = new Intent(NurbCompActivity.this, NurbCompMidModeActivity.class);
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
                ms1.setVisibility(View.VISIBLE);
                ms2.setVisibility(View.VISIBLE);
                Intent intent = new Intent(NurbCompActivity.this, NurbCompMidModeActivity.class);
                startActivity(intent);
            }
        });
    }
}