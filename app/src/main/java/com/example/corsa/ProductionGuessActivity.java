package com.example.corsa;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ProductionGuessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_production_guess);

        ImageView image = findViewById(R.id.image_production_guess);
        ImageView image_black = findViewById(R.id.image_black_production_guess);
        TextView ans1 = findViewById(R.id.ans1_production_guess);
        TextView ans2 = findViewById(R.id.ans2_production_guess);
        TextView ans3 = findViewById(R.id.ans3_production_guess);
        TextView ans4 = findViewById(R.id.ans4_production_guess);
        Button continue_button = findViewById(R.id.continue_production_guess);
        TextView right_ans = findViewById(R.id.right_ans_production_guess);
        TextView wrong_ans = findViewById(R.id.wrong_ans_production_guess);
        TextView[] answers = {ans1, ans2, ans3, ans4};

        Random rand = new Random();

        int[] images = {R.drawable.huracan_lp610, R.drawable.bmw_m1, R.drawable.sl65_black_series
                , R.drawable.golf_gti_mk7, R.drawable.tvr_sagaris, R.drawable.bac_mono,
                R.drawable.m4_f82, R.drawable.veyron_16_4, R.drawable.sls, R.drawable.abarth_595};
        int[] prod_years = new int[]{2014, 1978, 2009, 2013, 2005, 2011, 2014, 2005, 2010, 2015};

        int[] check = new int[images.length];

        int indexPic = rand.nextInt(images.length);

        check[0] = prod_years[indexPic];
        int indexAns = rand.nextInt(4);
        int img = images[indexPic];
        image.setImageResource(img);
        answers[indexAns].setText(Integer.toString(indexAns + 1) + ". " + Integer.toString(prod_years[indexPic]));

        int i = 0;
        while (i < 4) {
            if (i != indexAns) {
                int year = prod_years[indexPic] + ThreadLocalRandom.current().nextInt(-10, 11);
                Boolean test = true;
                for (int j = 0; j < check.length; j++) {
                    if (year == check[j] || year > 2023) {
                        test = false;
                        break;
                    }
                }
                if (test) {
                    check[i + 1] = year;
                    answers[i].setText(Integer.toString(i + 1) + ". " + Integer.toString(year));
                    i++;
                }
            }
            else {
                i++;
            }
        }

        for (i = 0; i < 4; i++) {
            if (i != indexAns) {
                int finalI = i;
                answers[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        wrong_ans.setVisibility(View.VISIBLE);
                        answers[indexAns].setTextColor(Color.GREEN);
                        answers[finalI].setTextColor(Color.RED);
                        image_black.setVisibility(View.VISIBLE);
                        Intent i = new Intent(ProductionGuessActivity.this, ProductionGuessMidModeActivity.class);
                        startActivity(i);
                    }
                });
            } else if (i == indexAns) {
                answers[indexAns].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        right_ans.setVisibility(View.VISIBLE);
                        answers[indexAns].setTextColor(Color.GREEN);
                        image_black.setVisibility(View.VISIBLE);
                        Intent i = new Intent(ProductionGuessActivity.this, ProductionGuessMidModeActivity.class);
                        startActivity(i);
                    }
                });
            }
        }
    }
}