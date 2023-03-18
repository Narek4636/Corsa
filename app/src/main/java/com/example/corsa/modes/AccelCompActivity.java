package com.example.corsa.modes;

import static com.example.corsa.modes.PowerCompActivity.DELAY_COMP;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.corsa.Car;
import com.example.corsa.R;
import com.example.corsa.Utils;
import com.example.corsa.carRoom.CarDao;
import com.example.corsa.carRoom.CarDatabase;
import com.example.corsa.carRoom.CarDatabaseClient;
import com.example.corsa.carRoom.CarEntity;
import com.example.corsa.carRoom.CarViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class AccelCompActivity extends AppCompatActivity {
    TextView right_price;
    TextView wrong_price;
    ImageView right_pic;
    ImageView wrong_pic;
    //    --------------------------------------
    List<CarEntity> carList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accel_comp);
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

//--------------------------------------------------------
        carList = new ArrayList<>();

        CarViewModel carListViewModel = new CarViewModel(getApplication());
        carListViewModel.readCars();

        carListViewModel.getCars().observe(this, new Observer<List<CarEntity>>() {
            @Override
            public void onChanged(List<CarEntity> carEntities) {
                carList.addAll(carEntities);
                Log.d("TAG", "Number of cars: " + carList.size());

                Random rand = new Random();
                int indexPic1 = rand.nextInt(carList.size());
                CarEntity car1 = carList.get(indexPic1);
                int indexPic2;
                while (true) {
                    indexPic2 = rand.nextInt(carList.size());
                    CarEntity car2 = carList.get(indexPic2);
//                    ARAJNAYIN TARBERAK MINCHEV RANK MTCNEL
                    if (indexPic2 != indexPic1 && car1.accelTime != car2.accelTime && Math.abs(car1.accelTime - car2.accelTime) <= 1.5) {
                        break;
                    }
                }
                CarEntity car2 = carList.get(indexPic2);

                image1.setImageDrawable(Utils.byteToDrawable(AccelCompActivity.this, car1.image));
                image2.setImageDrawable(Utils.byteToDrawable(AccelCompActivity.this, car2.image));
                name1.setText(car1.name);
                name2.setText(car2.name);
                year1.setText(Integer.toString(car1.prodYear));
                year2.setText(Integer.toString(car2.prodYear));

                double time1 = car1.accelTime;
                double time2 = car2.accelTime;

                time1str.setText(Double.toString(car1.accelTime));
                time2str.setText(Double.toString(car2.accelTime));

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
                        Utils.vibrate(AccelCompActivity.this);

                        Intent intent = new Intent(AccelCompActivity.this, MainMenu.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        finish();
                    }
                });

                right_pic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Utils.vibrate(AccelCompActivity.this);

                        right_price.setVisibility(View.VISIBLE);
                        right_price.setTextColor(Color.GREEN);
                        wrong_price.setVisibility(View.VISIBLE);
                        image1_black.setAlpha(0.7f);
                        image2_black.setAlpha(0.7f);
                        sec1.setVisibility(View.VISIBLE);
                        sec2.setVisibility(View.VISIBLE);

                        ValueAnimator animator1 = ValueAnimator.ofFloat((float) time1 / 3, (float) time1);
                        ObjectAnimator alphaAnimator1 = ObjectAnimator.ofFloat(time1str, View.ALPHA, 0f, 1f);
                        ObjectAnimator alphaAnimator1h = ObjectAnimator.ofFloat(sec1, View.ALPHA, 0f, 1f);
                        alphaAnimator1.setDuration(DELAY_COMP / 4);
                        animator1.setDuration(DELAY_COMP);

                        AnimatorSet animatorSet1 = new AnimatorSet();
                        animatorSet1.playTogether(animator1, alphaAnimator1, alphaAnimator1h);

                        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                float value = (float) valueAnimator.getAnimatedValue();
                                time1str.setText(String.format("%.1f", value)); // Update the text view with the animated value
                            }
                        });
                        animatorSet1.start(); // start the animator

                        ValueAnimator animator2 = ValueAnimator.ofFloat((float) time2 / 3, (float) time2);
                        ObjectAnimator alphaAnimator2 = ObjectAnimator.ofFloat(time2str, View.ALPHA, 0f, 1f);
                        ObjectAnimator alphaAnimator2h = ObjectAnimator.ofFloat(sec2, View.ALPHA, 0f, 1f);
                        alphaAnimator2.setDuration(DELAY_COMP / 4);
                        animator2.setDuration(DELAY_COMP);

                        AnimatorSet animatorSet2 = new AnimatorSet();
                        animatorSet2.playTogether(animator2, alphaAnimator2, alphaAnimator2h);

                        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                float value = (float) valueAnimator.getAnimatedValue();
                                time2str.setText(String.format("%.1f", value)); // Update the text view with the animated value
                            }
                        });
                        animatorSet2.start(); // start the animator


                        if (!Objects.equals(getIntent().getStringExtra("previousActivity"), "AccelCompActivity") && !Objects.equals(getIntent().getStringExtra("previousActivity"), "adapter")) {
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
                                    }, DELAY_COMP + 200);
                                }
                            });
                        } else {
                            Intent i = new Intent(AccelCompActivity.this, AccelCompActivity.class);
                            i.putExtra("previousActivity", "AccelCompActivity");

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
                                    }, DELAY_COMP + 200);
                                }
                            });
                        }
                    }
                });
                wrong_pic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Utils.vibrate(AccelCompActivity.this);

                        right_price.setVisibility(View.VISIBLE);
                        right_price.setTextColor(Color.GREEN);
                        wrong_price.setVisibility(View.VISIBLE);
                        wrong_price.setTextColor(Color.RED);
                        image1_black.setAlpha(0.7f);
                        image2_black.setAlpha(0.7f);
                        sec1.setVisibility(View.VISIBLE);
                        sec2.setVisibility(View.VISIBLE);

                        ValueAnimator animator1 = ValueAnimator.ofFloat((float) time1 / 3, (float) time1);
                        ObjectAnimator alphaAnimator1 = ObjectAnimator.ofFloat(time1str, View.ALPHA, 0f, 1f);
                        ObjectAnimator alphaAnimator1h = ObjectAnimator.ofFloat(sec1, View.ALPHA, 0f, 1f);
                        alphaAnimator1.setDuration(DELAY_COMP / 4);
                        animator1.setDuration(DELAY_COMP);

                        AnimatorSet animatorSet1 = new AnimatorSet();
                        animatorSet1.playTogether(animator1, alphaAnimator1, alphaAnimator1h);

                        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                float value = (float) valueAnimator.getAnimatedValue();
                                time1str.setText(String.format("%.1f", value)); // Update the text view with the animated value
                            }
                        });
                        animatorSet1.start(); // start the animator

                        ValueAnimator animator2 = ValueAnimator.ofFloat((float) time2 / 3, (float) time2);
                        ObjectAnimator alphaAnimator2 = ObjectAnimator.ofFloat(time2str, View.ALPHA, 0f, 1f);
                        ObjectAnimator alphaAnimator2h = ObjectAnimator.ofFloat(sec2, View.ALPHA, 0f, 1f);
                        alphaAnimator2.setDuration(DELAY_COMP / 4);
                        animator2.setDuration(DELAY_COMP);

                        AnimatorSet animatorSet2 = new AnimatorSet();
                        animatorSet2.playTogether(animator2, alphaAnimator2, alphaAnimator2h);

                        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                float value = (float) valueAnimator.getAnimatedValue();
                                time2str.setText(String.format("%.1f", value)); // Update the text view with the animated value
                            }
                        });
                        animatorSet2.start(); // start the animator

                        if (!Objects.equals(getIntent().getStringExtra("previousActivity"), "AccelCompActivity") && !Objects.equals(getIntent().getStringExtra("previousActivity"), "adapter")) {
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
                                    }, DELAY_COMP + 200);
                                }
                            });
                        } else {
                            Intent i = new Intent(AccelCompActivity.this, AccelCompActivity.class);
                            i.putExtra("previousActivity", "AccelCompActivity");

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
                                    }, DELAY_COMP + 200);
                                }
                            });
                        }
                    }
                });
            }
        });


//        carListViewModel.readCars();
//--------------------------------------------------------

    }
}