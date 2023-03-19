package com.example.corsa.modes;

import static com.example.corsa.modes.PowerCompActivity.DELAY_COMP;

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

import com.example.corsa.NurbTimes;
import com.example.corsa.R;
import com.example.corsa.Utils;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class NurbCompActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurb_comp);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

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
        TextView menu = findViewById(R.id.return_button_nurb_comp);

        TextView right_price;
        TextView wrong_price;
        ImageView right_pic;
        ImageView wrong_pic;

        int[] images = {R.drawable.huracan_lp610, R.drawable.sl65_black_series
                , R.drawable.golf_gti_mk7, R.drawable.m4_f82, R.drawable.veyron_16_4,
                R.drawable.test_pic, R.drawable.aventador_700};
//        int[] times = new int[]{728, 751, 829, 752, 740, 742, 725};
        ArrayList<NurbTimes> times = new ArrayList<NurbTimes>();
        times.add(new NurbTimes("728"));
        times.add(new NurbTimes("751"));
        times.add(new NurbTimes("829"));
        times.add(new NurbTimes("752"));
        times.add(new NurbTimes("740"));
        times.add(new NurbTimes("742"));
        times.add(new NurbTimes("725"));
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

        NurbTimes time1 = times.get(indexPic1);
        NurbTimes time2 = times.get(indexPic2);
        int sum1 = time1.MM * 60 + time1.SS;
        int sum2 = time2.MM * 60 + time2.SS;

        time1str.setText(timesstr[indexPic1]);
        time2str.setText(timesstr[indexPic2]);

        if (sum1 < sum2) {
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
                Utils.vibrate(NurbCompActivity.this);
                menu.setEnabled(false);

                Intent intent = new Intent(NurbCompActivity.this, MainMenu.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                finish();
            }
        });

        right_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.vibrate(NurbCompActivity.this);
                right_pic.setEnabled(false);

                right_price.setVisibility(View.VISIBLE);
                right_price.setTextColor(Color.GREEN);
                wrong_price.setVisibility(View.VISIBLE);
                image1_black.setAlpha(0.6f);
                image2_black.setAlpha(0.6f);
                ms1.setVisibility(View.VISIBLE);
                ms2.setVisibility(View.VISIBLE);

                ValueAnimator animator1 = ValueAnimator.ofInt(sum1 * 3 / 4, sum1);
                ObjectAnimator alphaAnimator1 = ObjectAnimator.ofFloat(time1str, View.ALPHA, 0f, 1f);
                ObjectAnimator alphaAnimator1h = ObjectAnimator.ofFloat(ms1, View.ALPHA, 0f, 1f);
                alphaAnimator1.setDuration(DELAY_COMP / 4);
                animator1.setDuration(DELAY_COMP);

                AnimatorSet animatorSet1 = new AnimatorSet();
                animatorSet1.playTogether(animator1, alphaAnimator1, alphaAnimator1h);

                animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int seconds = (int) valueAnimator.getAnimatedValue();
                        long currentMinute = TimeUnit.SECONDS.toMinutes(seconds);
                        long currentSecond = seconds - TimeUnit.SECONDS.toMinutes(seconds) * 60;

                        String timeString1 = String.format("%d:%02d", currentMinute, currentSecond);
                        time1str.setText(timeString1);
                    }
                });
                animatorSet1.start(); // start the animator

                ValueAnimator animator2 = ValueAnimator.ofInt(sum2 * 3 / 4, sum2);
                ObjectAnimator alphaAnimator2 = ObjectAnimator.ofFloat(time2str, View.ALPHA, 0f, 1f);
                ObjectAnimator alphaAnimator2h = ObjectAnimator.ofFloat(ms2, View.ALPHA, 0f, 1f);
                alphaAnimator2.setDuration(DELAY_COMP / 4);
                animator2.setDuration(DELAY_COMP);

                AnimatorSet animatorSet2 = new AnimatorSet();
                animatorSet2.playTogether(animator2, alphaAnimator2, alphaAnimator2h);

                animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int seconds = (int) valueAnimator.getAnimatedValue();
                        long currentMinute = TimeUnit.SECONDS.toMinutes(seconds);
                        long currentSecond = seconds - TimeUnit.SECONDS.toMinutes(seconds) * 60;

                        String timeString2 = String.format("%d:%02d", currentMinute, currentSecond);
                        time2str.setText(timeString2);
                    }
                });
                animatorSet2.start(); // start the animator

                if(!Objects.equals(getIntent().getStringExtra("previousActivity"), "NurbCompActivity") && !Objects.equals(getIntent().getStringExtra("previousActivity"), "adapter")) {
                    Random rand = new Random();
                    Class<?>[] activities = {CarGuessActivity.class, AccelCompActivity.class, NurbCompActivity.class,
                            PowerGuessActivity.class, PowerCompActivity.class, ProductionGuessActivity.class};
//
//                       RANDOMIC MTNELU HAMAR!!!!!!!!!!!!!!!!!!!!!!!!!
//
                    Class<?> Activity;
                    while (true) {
                        Activity = activities[rand.nextInt(activities.length)];
                        if (Activity != NurbCompActivity.class) {
                            break;
                        }
                    }

                    Intent intent = new Intent(NurbCompActivity.this, Activity);
                    intent.putExtra("previousActivity", "NurbCompActivity");

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
                }
                else {
                    Intent intent = new Intent(NurbCompActivity.this, NurbCompActivity.class);
                    intent.putExtra("previousActivity", "NurbCompActivity");

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
                }
            }
        });

        wrong_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.vibrate(NurbCompActivity.this);
                wrong_pic.setEnabled(false);

                right_price.setVisibility(View.VISIBLE);
                right_price.setTextColor(Color.GREEN);
                wrong_price.setVisibility(View.VISIBLE);
                wrong_price.setTextColor(Color.RED);
                image1_black.setAlpha(0.6f);
                image2_black.setAlpha(0.6f);
                ms1.setVisibility(View.VISIBLE);
                ms2.setVisibility(View.VISIBLE);

                ValueAnimator animator2 = ValueAnimator.ofInt(sum2 * 3 / 4, sum2);
                ObjectAnimator alphaAnimator2 = ObjectAnimator.ofFloat(time2str, View.ALPHA, 0f, 1f);
                ObjectAnimator alphaAnimator2h = ObjectAnimator.ofFloat(ms2, View.ALPHA, 0f, 1f);
                alphaAnimator2.setDuration(DELAY_COMP / 4);
                animator2.setDuration(DELAY_COMP);

                AnimatorSet animatorSet2 = new AnimatorSet();
                animatorSet2.playTogether(animator2, alphaAnimator2, alphaAnimator2h);

                animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int seconds = (int) valueAnimator.getAnimatedValue();
                        long currentMinute = TimeUnit.SECONDS.toMinutes(seconds);
                        long currentSecond = seconds - TimeUnit.SECONDS.toMinutes(seconds) * 60;

                        String timeString2 = String.format("%d:%02d", currentMinute, currentSecond);
                        time2str.setText(timeString2);
                    }
                });
                animatorSet2.start(); // start the animator

                ValueAnimator animator1 = ValueAnimator.ofInt(sum1 * 3 / 4, sum1);
                ObjectAnimator alphaAnimator1 = ObjectAnimator.ofFloat(time1str, View.ALPHA, 0f, 1f);
                ObjectAnimator alphaAnimator1h = ObjectAnimator.ofFloat(ms1, View.ALPHA, 0f, 1f);
                alphaAnimator1.setDuration(DELAY_COMP / 4);
                animator1.setDuration(DELAY_COMP);

                AnimatorSet animatorSet1 = new AnimatorSet();
                animatorSet1.playTogether(animator1, alphaAnimator1, alphaAnimator1h);

                animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int seconds = (int) valueAnimator.getAnimatedValue();
                        long currentMinute = TimeUnit.SECONDS.toMinutes(seconds);
                        long currentSecond = seconds - TimeUnit.SECONDS.toMinutes(seconds) * 60;

                        String timeString1 = String.format("%d:%02d", currentMinute, currentSecond);
                        time1str.setText(timeString1);
                    }
                });
                animatorSet1.start(); // start the animator

                if(!Objects.equals(getIntent().getStringExtra("previousActivity"), "NurbCompActivity") && !Objects.equals(getIntent().getStringExtra("previousActivity"), "adapter")) {
                    Random rand = new Random();
                    Class<?>[] activities = {CarGuessActivity.class, AccelCompActivity.class, NurbCompActivity.class,
                            PowerGuessActivity.class, PowerCompActivity.class, ProductionGuessActivity.class};
//
//                       RANDOMIC MTNELU HAMAR!!!!!!!!!!!!!!!!!!!!!!!!!
//
                    Class<?> Activity;
                    while (true) {
                        Activity = activities[rand.nextInt(activities.length)];
                        if (Activity != NurbCompActivity.class) {
                            break;
                        }
                    }

                    Intent intent = new Intent(NurbCompActivity.this, Activity);
                    intent.putExtra("previousActivity", "NurbCompActivity");

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
                }
                else {
                    Intent intent = new Intent(NurbCompActivity.this, NurbCompActivity.class);
                    intent.putExtra("previousActivity", "NurbCompActivity");

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
                }
            }
        });
    }
}