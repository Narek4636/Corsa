package com.example.corsa.modes;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
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

public class PowerCompActivity extends AppCompatActivity {

    public final static int DELAY_COMP = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_comp);
//        try {
//            Thread.sleep(2500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);


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
        TextView menu = findViewById(R.id.return_button_power_comp);

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
                Utils.vibrate(PowerCompActivity.this);

                right_price.setVisibility(View.VISIBLE);
                right_price.setTextColor(Color.GREEN);
                wrong_price.setVisibility(View.VISIBLE);
                image1_black.setVisibility(View.VISIBLE);
                image1_black.setAlpha(0.6f);
                image2_black.setAlpha(0.6f);
                hp1.setVisibility(View.VISIBLE);
                hp2.setVisibility(View.VISIBLE);


                ValueAnimator animator1 = ValueAnimator.ofInt(power1 * 3 / 4, power1);
                ObjectAnimator alphaAnimator1 = ObjectAnimator.ofFloat(power1str, View.ALPHA, 0f, 1f);
                ObjectAnimator alphaAnimator1h = ObjectAnimator.ofFloat(hp1, View.ALPHA, 0f, 1f);
                alphaAnimator1.setDuration(DELAY_COMP / 4);
                animator1.setDuration(DELAY_COMP);

                AnimatorSet animatorSet1 = new AnimatorSet();
                animatorSet1.playTogether(animator1, alphaAnimator1, alphaAnimator1h);

                animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int currentValue = (int) valueAnimator.getAnimatedValue();
                        power1str.setText(String.valueOf(currentValue));
                    }
                });
                animatorSet1.start(); // start the animator

                ValueAnimator animator2 = ValueAnimator.ofInt(power2 * 3 / 4, power2);
                ObjectAnimator alphaAnimator2 = ObjectAnimator.ofFloat(power2str, View.ALPHA, 0f, 1f);
                ObjectAnimator alphaAnimator2h = ObjectAnimator.ofFloat(hp2, View.ALPHA, 0f, 1f);
                alphaAnimator2.setDuration(DELAY_COMP / 3);
                animator2.setDuration(DELAY_COMP);

                AnimatorSet animatorSet2 = new AnimatorSet();
                animatorSet2.playTogether(animator2, alphaAnimator2, alphaAnimator2h);

                animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int currentValue = (int) valueAnimator.getAnimatedValue();
                        power2str.setText(String.valueOf(currentValue));
                    }
                });
                animatorSet2.start(); // start the animator


                if (!Objects.equals(getIntent().getStringExtra("previousActivity"), "PowerCompActivity") && !Objects.equals(getIntent().getStringExtra("previousActivity"), "adapter")) {
                    Random rand = new Random();
                    Class<?>[] activities = {CarGuessActivity.class, AccelCompActivity.class, NurbCompActivity.class,
                            PowerGuessActivity.class, PowerCompActivity.class, ProductionGuessActivity.class};
//
//                       RANDOMIC MTNELU HAMAR!!!!!!!!!!!!!!!!!!!!!!!!!
//
                    Class<?> Activity;
                    while (true) {
                        Activity = activities[rand.nextInt(activities.length)];
                        if (Activity != PowerCompActivity.class) {
                            break;
                        }
                    }

                    Intent intent = new Intent(PowerCompActivity.this, Activity);
                    intent.putExtra("previousActivity", "PowerCompActivity");

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
                            }, DELAY_COMP+200);
                        }
                    });
                } else {
                    Intent i = new Intent(PowerCompActivity.this, PowerCompActivity.class);
                    i.putExtra("previousActivity", "PowerCompActivity");

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
                            }, DELAY_COMP+200);
                        }
                    });
                }
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.vibrate(PowerCompActivity.this);

                Intent intent = new Intent(PowerCompActivity.this, MainMenu.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                finish();
            }
        });

        wrong_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.vibrate(PowerCompActivity.this);

                right_price.setVisibility(View.VISIBLE);
                right_price.setTextColor(Color.GREEN);
                wrong_price.setVisibility(View.VISIBLE);
                wrong_price.setTextColor(Color.RED);
                image1_black.setAlpha(0.6f);
                image2_black.setAlpha(0.6f);
                hp1.setVisibility(View.VISIBLE);
                hp2.setVisibility(View.VISIBLE);


                ValueAnimator animator1 = ValueAnimator.ofInt(power1 * 3 / 4, power1);
                ObjectAnimator alphaAnimator1 = ObjectAnimator.ofFloat(power1str, View.ALPHA, 0f, 1f);
                ObjectAnimator alphaAnimator1h = ObjectAnimator.ofFloat(hp1, View.ALPHA, 0f, 1f);
                alphaAnimator1.setDuration(DELAY_COMP / 4);
                animator1.setDuration(DELAY_COMP);

                AnimatorSet animatorSet1 = new AnimatorSet();
                animatorSet1.playTogether(animator1, alphaAnimator1, alphaAnimator1h);

                animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int currentValue = (int) valueAnimator.getAnimatedValue();
                        power1str.setText(String.valueOf(currentValue));
                    }
                });
                animatorSet1.start(); // start the animator

                ValueAnimator animator2 = ValueAnimator.ofInt(power2 * 3 / 4, power2);
                ObjectAnimator alphaAnimator2 = ObjectAnimator.ofFloat(power2str, View.ALPHA, 0f, 1f);
                ObjectAnimator alphaAnimator2h = ObjectAnimator.ofFloat(hp2, View.ALPHA, 0f, 1f);
                alphaAnimator2.setDuration(DELAY_COMP / 3);
                animator2.setDuration(DELAY_COMP);

                AnimatorSet animatorSet2 = new AnimatorSet();
                animatorSet2.playTogether(animator2, alphaAnimator2, alphaAnimator2h);

                animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int currentValue = (int) valueAnimator.getAnimatedValue();
                        power2str.setText(String.valueOf(currentValue));
                    }
                });
                animatorSet2.start(); // start the animator

                if (!Objects.equals(getIntent().getStringExtra("previousActivity"), "PowerCompActivity") && !Objects.equals(getIntent().getStringExtra("previousActivity"), "adapter")) {
                    Random rand = new Random();
                    Class<?>[] activities = {CarGuessActivity.class, AccelCompActivity.class, NurbCompActivity.class,
                            PowerGuessActivity.class, PowerCompActivity.class, ProductionGuessActivity.class};
//
//                       RANDOMIC MTNELU HAMAR!!!!!!!!!!!!!!!!!!!!!!!!!
//
                    Class<?> Activity;
                    while (true) {
                        Activity = activities[rand.nextInt(activities.length)];
                        if (Activity != PowerCompActivity.class) {
                            break;
                        }
                    }

                    Intent intent = new Intent(PowerCompActivity.this, Activity);
                    intent.putExtra("previousActivity", "PowerCompActivity");

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
                            }, DELAY_COMP+200);
                        }
                    });
                } else {
                    Intent i = new Intent(PowerCompActivity.this, PowerCompActivity.class);
                    i.putExtra("previousActivity", "PowerCompActivity");

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
                            }, DELAY_COMP+200);
                        }
                    });
                }
            }
        });
    }
}