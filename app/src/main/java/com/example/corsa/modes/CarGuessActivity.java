package com.example.corsa.modes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import com.example.corsa.activities.MainActivity;
import com.example.corsa.R;
import com.example.corsa.Utils;
import com.example.corsa.carRoom.CarEntity;
import com.example.corsa.components.CarGuessUtils;
import com.example.corsa.databinding.ActivityCarGuessBinding;
import com.example.corsa.fragments.StatusBarFragment;
import com.example.corsa.viewModels.CarViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class CarGuessActivity extends AppCompatActivity {

    final static int DELAY_GUESS = 850;
    List<CarEntity> carList;
    int indexPic;
    public static final String CAR_GUESS_PREFS = "CAR_GUESS";

//    LCNEL BAZAN NKARNEROV

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_guess);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.status_bar_car_guess, new StatusBarFragment()).commit();
        StatusBarFragment fragment = (StatusBarFragment) getSupportFragmentManager().findFragmentById(R.id.status_bar_car_guess);
// -----------------------------------------------------------------------------------------------------------------------------------

        ActivityCarGuessBinding binding = ActivityCarGuessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TextView[] answers = {binding.ans1CarGuess, binding.ans2CarGuess, binding.ans3CarGuess, binding.ans4CarGuess};

//        ----------------------------------------------------------
        Intent intent = getIntent();

        if(intent.getStringExtra("hid") != null) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("hid", intent.getStringExtra("hid"));
            editor.apply();
        }

        int hid;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        Log.d("TAG", preferences.getString("hid",""));
        if(Objects.equals(preferences.getString("hid", ""), ""))
            hid = 25;
        else {
            hid = Integer.parseInt(preferences.getString("hid", ""));
        }

        carList = new ArrayList<>();
        CarViewModel carListViewModel = new CarViewModel(getApplication());
        carListViewModel.readCars();

        carListViewModel.getCars().observe(this, new Observer<List<CarEntity>>() {
            @Override
            public void onChanged(List<CarEntity> carEntities) {
                carList.addAll(carEntities);

                Random rand = new Random();
                int[] check = new int[carList.size()];

                while(true) {
                    indexPic = rand.nextInt(carList.size());
                    if(Objects.equals(carList.get(indexPic).carGuessAllow, "1"))
                        break;
                }

                check[0] = indexPic;
                int indexAns = rand.nextInt(4);

                Log.d("TAG",carList.get(indexPic).name);

                binding.imageCarGuess.setImageBitmap(CarGuessUtils.crop(Integer.parseInt(preferences.getString("hid","")), CarGuessActivity.this, getResources().getIdentifier(carList.get(indexPic).imagePath, "drawable", getPackageName())));
                answers[indexAns].setText(Integer.toString(indexAns + 1) + ". " + carList.get(indexPic).name);
//                Log.d("TAG", preferences.getString("hid",""));


                TextView menu = findViewById(R.id.return_button);
                menu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utils.vibrate(CarGuessActivity.this);
                        menu.setEnabled(false);

                        Intent intent = new Intent(CarGuessActivity.this, MainActivity.class);
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
                            if (indexPic2 == check[j] || !Objects.equals(carList.get(indexPic2).carGuessAllow, "1")) {
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
                                fragment.rateUp(5);

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

        Intent intent = new Intent(CarGuessActivity.this, MainActivity.class);
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