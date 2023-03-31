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

import com.example.corsa.R;
import com.example.corsa.Utils;
import com.example.corsa.carRoom.CarEntity;
import com.example.corsa.components.AccelCompUtils;
import com.example.corsa.databinding.ActivityAccelCompBinding;
import com.example.corsa.viewModels.CarViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class AccelCompActivity extends AppCompatActivity {
    TextView rightPrice;
    TextView wrongPrice;
    ImageView rightPic;
    ImageView wrongPic;
    //    --------------------------------------
    List<CarEntity> carList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accel_comp);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        ActivityAccelCompBinding binding = ActivityAccelCompBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

                binding.image1AccelComp.setImageResource(getResources().getIdentifier(car1.imagePath, "drawable", getPackageName()));
                binding.image2AccelComp.setImageResource(getResources().getIdentifier(car2.imagePath, "drawable", getPackageName()));
                Log.d("TAG", String.valueOf(getResources().getIdentifier(car1.imagePath, "drawable", getPackageName())) + " " + car1.imagePath);
                binding.name1AccelComp.setText(car1.name);
                binding.name2AccelComp.setText(car2.name);
                binding.year1AccelComp.setText(Integer.toString(car1.prodYear));
                binding.year2AccelComp.setText(Integer.toString(car2.prodYear));

                double time1 = car1.accelTime;
                double time2 = car2.accelTime;

                binding.time1AccelComp.setText(Double.toString(car1.accelTime));
                binding.time2AccelComp.setText(Double.toString(car2.accelTime));

                if (time1 < time2) {
                    rightPrice = binding.time1AccelComp;
                    rightPic = binding.image1AccelComp;
                    wrongPrice = binding.time2AccelComp;
                    wrongPic = binding.image2AccelComp;
                } else {
                    rightPrice = binding.time2AccelComp;
                    rightPic = binding.image2AccelComp;
                    wrongPrice = binding.time1AccelComp;
                    wrongPic = binding.image1AccelComp;
                }
                binding.returnButtonAccelComp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utils.vibrate(AccelCompActivity.this);
                        binding.returnButtonAccelComp.setEnabled(false);

                        Intent intent = new Intent(AccelCompActivity.this, MainMenu.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        finish();
                    }
                });

                rightPic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AccelCompUtils.rightAns(AccelCompActivity.this, rightPrice, wrongPic, rightPic, wrongPrice, binding);

                        AccelCompUtils.animate(binding, time1, time2);

                        startTransition();
                    }
                });
                wrongPic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AccelCompUtils.wrongAns(AccelCompActivity.this, rightPrice, rightPic, wrongPic, wrongPrice, binding);

                        AccelCompUtils.animate(binding, time1, time2);

                        startTransition();
                    }
                });
            }
        });


//        carListViewModel.readCars();
//--------------------------------------------------------

    }

    @Override
    public void onBackPressed() {
        Utils.vibrate(AccelCompActivity.this);

        Intent intent = new Intent(AccelCompActivity.this, MainMenu.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        finish();
    }

    public void startTransition() {
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
}