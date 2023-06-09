package com.samsung.corsa.modes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.corsa.R;
import com.example.corsa.databinding.ActivityInteriorGuessBinding;
import com.samsung.corsa.Utils;
import com.samsung.corsa.activities.MainActivity;
import com.samsung.corsa.carRoom.CarEntity;
import com.samsung.corsa.components.InteriorGuessUtils;
import com.samsung.corsa.fragments.StatusBarFragment;
import com.samsung.corsa.viewModels.CarViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class InteriorGuessActivity extends AppCompatActivity {

    List<CarEntity> carList;
    int indexPic;
    public static final String INTERIOR_GUESS_PREFS = "INTERIOR_GUESS";
    StatusBarFragment statusBarFragment;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interior_guess);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.status_bar_interior_guess, new StatusBarFragment()).commit();

        ActivityInteriorGuessBinding binding = ActivityInteriorGuessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TextView[] answers = {binding.ans1InteriorGuess, binding.ans2InteriorGuess, binding.ans3InteriorGuess, binding.ans4InteriorGuess};

        Intent intent = getIntent();

        if(intent.getStringExtra("hid") != null && intent.getStringExtra("xp") != null) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("hid", intent.getStringExtra("hid"));
            editor.putString("xpInteriorGuess", intent.getStringExtra("xp"));
            editor.apply();
        }

        int hid;
        int xp;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        Log.d("TAG", preferences.getString("hid",""));
        if(Objects.equals(preferences.getString("hid", ""), "") &&
                Objects.equals(preferences.getString("xpInteriorGuess", ""), "")) {
            hid = 20;
            xp = 1;
        }
        else {
            hid = Integer.parseInt(preferences.getString("hid", ""));
            xp = Integer.parseInt(preferences.getString("xpInteriorGuess", ""));
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
                    if(Objects.equals(carList.get(indexPic).showInterior, "1"))
                        break;
                }

                check[0] = indexPic;
                int indexAns = rand.nextInt(4);

                Log.d("TAG",carList.get(indexPic).name);

                binding.imageInteriorGuess.setImageBitmap(InteriorGuessUtils.crop(hid, InteriorGuessActivity.this, getResources().getIdentifier(carList.get(indexPic).interior, "drawable", getPackageName())));
                answers[indexAns].setText(Integer.toString(indexAns + 1) + ". " + carList.get(indexPic).name);
                Log.d("TAG", carList.get(indexPic).interior);


                TextView menu = findViewById(R.id.return_button);
                menu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utils.vibrate(InteriorGuessActivity.this);
                        menu.setEnabled(false);

                        Intent intent = new Intent(InteriorGuessActivity.this, MainActivity.class);
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
                            if (indexPic2 == check[j] || !Objects.equals(carList.get(indexPic2).showInterior, "1")) {
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

                sharedPreferences = getSharedPreferences(ProductionGuessActivity.ACCURACY_PREFS, MODE_PRIVATE);
                editor = sharedPreferences.edit();

                for (i = 0; i < 4; i++) {
                    if (i != indexAns) {
                        int finalI = i;
                        answers[i].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                editor.putInt("interiorGuessAll", sharedPreferences.getInt("interiorGuessAll", 0) + 1);
                                editor.apply();

                                InteriorGuessUtils.wrongAns(InteriorGuessActivity.this, binding, answers, finalI, indexAns);

                                statusBarFragment = (StatusBarFragment) fragmentManager.findFragmentById(R.id.status_bar_interior_guess);
                                statusBarFragment.rateDown(xp);

                                binding.imageInteriorGuess.setImageResource(getResources().getIdentifier(carList.get(indexPic).imagePath, "drawable", getPackageName()));
                                animate();
                            }
                        });
                    }
                    else if(i == indexAns){
                        answers[indexAns].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                editor.putInt("interiorGuessAll", sharedPreferences.getInt("interiorGuessAll", 0) + 1);
                                editor.putInt("interiorGuessCorrect", sharedPreferences.getInt("interiorGuessCorrect", 0) + 1);
                                editor.apply();

                                InteriorGuessUtils.rightAns(InteriorGuessActivity.this, binding, answers, indexAns);

                                statusBarFragment = (StatusBarFragment) fragmentManager.findFragmentById(R.id.status_bar_interior_guess);
                                statusBarFragment.levelUp();
                                statusBarFragment.rateUp(xp);

                                binding.imageInteriorGuess.setImageResource(getResources().getIdentifier(carList.get(indexPic).imagePath, "drawable", getPackageName()));
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
        Utils.vibrate(InteriorGuessActivity.this);

        Intent intent = new Intent(InteriorGuessActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        finish();
    }
//----------------------------------------------
    public void animate(){
        if(!Objects.equals(getIntent().getStringExtra("previousActivity"), "InteriorGuessActivity") && !Objects.equals(getIntent().getStringExtra("previousActivity"), "adapter")) {
            Random rand = new Random();
            Class<?>[] activities = {CarGuessActivity.class, AccelCompActivity.class, NurbCompActivity.class,
                    PowerGuessActivity.class, PowerCompActivity.class, ProductionGuessActivity.class, InteriorGuessActivity.class};
//
//                       RANDOMIC MTNELU HAMAR!!!!!!!!!!!!!!!!!!!!!!!!!
//
            Class<?> Activity;
            while (true) {
                Activity = activities[rand.nextInt(activities.length)];
                if (Activity != InteriorGuessActivity.class) {
                    break;
                }
            }

            Intent intent = new Intent(InteriorGuessActivity.this, Activity);
            intent.putExtra("previousActivity", "InteriorGuessActivity");

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
                    }, CarGuessActivity.DELAY_GUESS);
                }
            });
        }
        else {
            Intent i = new Intent(InteriorGuessActivity.this, InteriorGuessActivity.class);
            i.putExtra("previousActivity", "InteriorGuessActivity");

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
                    }, CarGuessActivity.DELAY_GUESS);
                }
            });
        }
    }
}