package com.example.corsa.modes;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.corsa.R;
import com.example.corsa.Utils;
import com.example.corsa.carRoom.CarEntity;
import com.example.corsa.components.CarGuessUtils;
import com.example.corsa.databinding.ActivityCarGuessBinding;
import com.example.corsa.viewModels.CarViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class CarGuessActivity extends AppCompatActivity {

    final static int DELAY_GUESS = 850;
    List<CarEntity> carList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_guess);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        ActivityCarGuessBinding binding = ActivityCarGuessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TextView[] answers = {binding.ans1CarGuess, binding.ans2CarGuess, binding.ans3CarGuess, binding.ans4CarGuess};

//        ----------------------------------------------------------

        carList = new ArrayList<>();
        CarViewModel carListViewModel = new CarViewModel(getApplication());
        carListViewModel.readCars();

        carListViewModel.getCars().observe(this, new Observer<List<CarEntity>>() {
            @Override
            public void onChanged(List<CarEntity> carEntities) {
                carList.addAll(carEntities);

                Random rand = new Random();
                int[] check;
                check = new int[carList.size()];

                int indexPic = rand.nextInt(carList.size());
                check[0] = indexPic;
                int indexAns = rand.nextInt(4);

                binding.imageCarGuess.setImageResource(getResources().getIdentifier(carList.get(indexPic).imageGuess, "drawable", getPackageName()));
                answers[indexAns].setText(Integer.toString(indexAns + 1) + ". " + carList.get(indexPic).name);

                binding.returnButtonCarGuess.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utils.vibrate(CarGuessActivity.this);
                        binding.returnButtonCarGuess.setEnabled(false);

                        Intent intent = new Intent(CarGuessActivity.this, MainMenu.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        finish();
                    }
                });

                int i = 0;
                while (i < 4) {
                    if (i != indexAns) {
                        int indexPic2 = rand.nextInt(carList.size());
                        Boolean test = true;
                        for (int j = 0; j < check.length; j++) {
                            if (indexPic2 == check[j]) {
                                test = false;
                                break;
                            }
                        }
                        if (test) {
                            check[i + 1] = indexPic2;
                            answers[i].setText(Integer.toString(i + 1) + ". " + carList.get(indexPic2).name);
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
                                CarGuessUtils.wrongAns(CarGuessActivity.this, binding, answers, finalI, indexAns);

                                binding.imageCarGuess.setImageResource(getResources().getIdentifier(carList.get(indexPic).imagePath, "drawable", getPackageName()));
                                animate();
                            }
                        });
                    }
                    else if(i == indexAns){
                        answers[indexAns].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                CarGuessUtils.rightAns(CarGuessActivity.this, binding, answers, indexAns);

                                binding.imageCarGuess.setImageResource(getResources().getIdentifier(carList.get(indexPic).imagePath, "drawable", getPackageName()));
                                animate();
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Utils.vibrate(CarGuessActivity.this);

        Intent intent = new Intent(CarGuessActivity.this, MainMenu.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        finish();
    }

    public void animate(){
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
}