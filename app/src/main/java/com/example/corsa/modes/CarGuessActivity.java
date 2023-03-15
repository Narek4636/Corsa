package com.example.corsa.modes;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.corsa.R;
import com.example.corsa.Utils;

import java.util.Objects;
import java.util.Random;

public class CarGuessActivity extends AppCompatActivity {

    final static int DELAY_GUESS = 700;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_guess);

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        ImageView image = findViewById(R.id.image_car_guess);
        ImageView image_black = findViewById(R.id.image_black_car_guess);
        TextView ans1 = findViewById(R.id.ans1_car_guess);
        TextView ans2 = findViewById(R.id.ans2_car_guess);
        TextView ans3 = findViewById(R.id.ans3_car_guess);
        TextView ans4 = findViewById(R.id.ans4_car_guess);
        TextView right_ans = findViewById(R.id.right_ans_car_guess);
        TextView wrong_ans = findViewById(R.id.wrong_ans_car_guess);
        TextView menu = findViewById(R.id.return_button_car_guess);
        TextView[] answers = {ans1, ans2, ans3, ans4};

        Random rand = new Random();

        int[] images = {R.drawable.huracan_lp610_image_guess, R.drawable.bmw_m1_image_guess, R.drawable.mercedes_sl65_black_series_image_guess
                , R.drawable.golf_gti_mk7_image_guess, R.drawable.tvr_sagaris_image_guess};
        String[] names = new String[]{"LAMBORGHINI HURACAN LP610-4", "BMW M1"
                , "MERCEDES SL65 AMG BLACK SERIES", "VW GOLF GTI", "TVR SAGARIS"};
        int[] check;
        check = new int[names.length];

        int indexPic = rand.nextInt(images.length);
        check[0] = indexPic;
        int indexAns = rand.nextInt(4);
        int img = images[indexPic];
        image.setImageResource(img);
        answers[indexAns].setText(Integer.toString(indexAns + 1) + ". " + names[indexPic]);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.vibrate(CarGuessActivity.this);

                Intent intent = new Intent(CarGuessActivity.this, MainMenu.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                finish();
            }
        });

        int i = 0;
        while (i < 4) {
            if (i != indexAns) {
                int indexPic2 = rand.nextInt(names.length);
                Boolean test = true;
                for (int j = 0; j < check.length; j++) {
                    if (indexPic2 == check[j]) {
                        test = false;
                        break;
                    }
                }
                if (test) {
                    check[i + 1] = indexPic2;
                    answers[i].setText(Integer.toString(i + 1) + ". " + names[indexPic2]);
                    i++;
                }
            } else {
                i++;
            }
        }

        for (i = 0; i < 4; i++) {
            if (i != indexAns) {
                int finalI = i;
                answers[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Utils.vibrate(CarGuessActivity.this);

                        wrong_ans.setVisibility(View.VISIBLE);
                        answers[indexAns].setTextColor(Color.GREEN);
                        answers[finalI].setTextColor(Color.RED);
                        image_black.setVisibility(View.VISIBLE);

                        if(!Objects.equals(getIntent().getStringExtra("previousActivity"), "CarGuessActivity") && !Objects.equals(getIntent().getStringExtra("previousActivity"), "adapter")) {
                            Random rand = new Random();
                            Class<?>[] activities = {CarGuessActivity.class, AccelCompActivity.class, NurbCompActivity.class,
                                    PowerGuessActivity.class, PowerCompActivity.class, ProductionGuessActivity.class};
//
//                       RANDOMIC MTNELU HAMAR!!!!!!!!!!!!!!!!!!!!!!!!!
//
                            Class<?> Activity;
                            while (true) {
                                Activity = activities[rand.nextInt(activities.length)];
                                if (Activity != CarGuessActivity.class) {
                                    break;
                                }
                            }

                            Intent intent = new Intent(CarGuessActivity.this, Activity);
                            intent.putExtra("previousActivity", "CarGuessActivity");

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
                                    }, DELAY_GUESS);
                                }
                            });
                        }
                        else {
                            Intent i = new Intent(CarGuessActivity.this, CarGuessActivity.class);
                            i.putExtra("previousActivity", "CarGuessActivity");

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            startActivity(i);
                                            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                                            finish();
                                        }
                                    }, DELAY_GUESS);
                                }
                            });
                        }
                    }
                });
            }
            else if(i == indexAns){
                answers[indexAns].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Utils.vibrate(CarGuessActivity.this);

                        right_ans.setVisibility(View.VISIBLE);
                        answers[indexAns].setTextColor(Color.GREEN);
                        image_black.setVisibility(View.VISIBLE);

                        if(!Objects.equals(getIntent().getStringExtra("previousActivity"), "CarGuessActivity") && !Objects.equals(getIntent().getStringExtra("previousActivity"), "adapter")) {
                            Random rand = new Random();
                            Class<?>[] activities = {CarGuessActivity.class, AccelCompActivity.class, NurbCompActivity.class,
                                    PowerGuessActivity.class, PowerCompActivity.class, ProductionGuessActivity.class};
//
//                       RANDOMIC MTNELU HAMAR!!!!!!!!!!!!!!!!!!!!!!!!!
//
                            Class<?> Activity;
                            while (true) {
                                Activity = activities[rand.nextInt(activities.length)];
                                if (Activity != CarGuessActivity.class) {
                                    break;
                                }
                            }

                            Intent intent = new Intent(CarGuessActivity.this, Activity);
                            intent.putExtra("previousActivity", "CarGuessActivity");

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
                                    }, DELAY_GUESS);
                                }
                            });
                        }
                        else {
                            Intent i = new Intent(CarGuessActivity.this, CarGuessActivity.class);
                            i.putExtra("previousActivity", "CarGuessActivity");

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            startActivity(i);
                                            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                                            finish();
                                        }
                                    }, DELAY_GUESS);
                                }
                            });
                        }
                    }
                });
            }
        }
    }
}