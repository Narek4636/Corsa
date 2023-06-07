package com.example.corsa.modes;

import static com.example.corsa.modes.CarGuessActivity.DELAY_GUESS;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
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
import com.example.corsa.components.ProdGuessUtils;
import com.example.corsa.databinding.ActivityProductionGuessBinding;
import com.example.corsa.fragments.StatusBarFragment;
import com.example.corsa.viewModels.CarViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ProductionGuessActivity extends AppCompatActivity {

    List<CarEntity> carList;
    public static final String PROD_GUESS_PREFS = "PROD_GUESS";
    public static final String PROFILE_PREFS = "PROFILE_PREFS";
    public static final String ACCURACY_PREFS = "ACCURACY_PREFS";
    StatusBarFragment statusBar;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_production_guess);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        Intent intent = getIntent();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.status_bar_prod_guess, new StatusBarFragment()).commit();

        if (intent.getStringExtra("b1") != null && intent.getStringExtra("b2") != null &&
                intent.getStringExtra("xp") != null) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("b1ProdGuess", intent.getStringExtra("b1"));
            editor.putString("b2ProdGuess", intent.getStringExtra("b2"));
            editor.putString("xpProdGuess", intent.getStringExtra("xp"));
            editor.apply();
        }

        int bound1;
        int bound2;
        int xp;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        Log.d("TAG", preferences.getString("b1","") + " " + preferences.getString("b2", ""));
        if (Objects.equals(preferences.getString("b1ProdGuess", ""), "") &&
                Objects.equals(preferences.getString("b2ProdGuess", ""), "") &&
                Objects.equals(preferences.getString("xpProdGuess", ""), "")) {
            bound1 = -15;
            bound2 = 15;
            xp = 1;
        } else {
            bound1 = Integer.parseInt(preferences.getString("b1ProdGuess", ""));
            bound2 = Integer.parseInt(preferences.getString("b2ProdGuess", ""));
            xp = Integer.parseInt(preferences.getString("xpProdGuess", ""));
        }

        ActivityProductionGuessBinding binding = ActivityProductionGuessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        carList = new ArrayList<>();

        CarViewModel carListViewModel = new CarViewModel(getApplication());
        carListViewModel.readCars();

        carListViewModel.getCars().observe(this, new Observer<List<CarEntity>>() {
            @Override
            public void onChanged(List<CarEntity> carEntities) {
                carList.addAll(carEntities);

                TextView[] answers = {binding.ans1ProductionGuess, binding.ans2ProductionGuess, binding.ans3ProductionGuess, binding.ans4ProductionGuess};
                Random rand = new Random();
                int[] check = new int[carList.size()];
                int indexPic = rand.nextInt(carList.size());

                CarEntity car = carList.get(indexPic);

                check[0] = car.prodYear;
                int indexAns = rand.nextInt(4);
                binding.nameProdGuess.setText(car.name);
                binding.imageProductionGuess.setImageResource(getResources().getIdentifier(car.imagePath, "drawable", getPackageName()));
                answers[indexAns].setText(Integer.toString(indexAns + 1) + ".   " + Integer.toString(car.prodYear));

                int i = 0;
                while (i < 4) {
                    if (i != indexAns) {
                        int year = car.prodYear + ThreadLocalRandom.current().nextInt(bound1, bound2);
                        Boolean test = true;
                        for (int j = 0; j < check.length; j++) {
                            if (year == check[j] || year > 2023) {
                                test = false;
                                break;
                            }
                        }
                        if (test) {
                            check[i + 1] = year;
                            answers[i].setText(Integer.toString(i + 1) + ".   " + Integer.toString(year));
                            i++;
                        }
                    } else {
                        i++;
                    }
                }

                TextView menu = findViewById(R.id.return_button);
                menu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utils.vibrate(ProductionGuessActivity.this);
                        menu.setEnabled(false);

                        Intent intent = new Intent(ProductionGuessActivity.this, MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        finish();
                    }
                });

                sharedPreferences = getSharedPreferences(ACCURACY_PREFS, MODE_PRIVATE);
                editor = sharedPreferences.edit();

                for (i = 0; i < 4; i++) {
                    if (i != indexAns) {
                        int finalI = i;
                        answers[i].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                editor.putInt("prodGuessAll",sharedPreferences.getInt("prodGuessAll", 0) + 1);
                                editor.apply();

                                ProdGuessUtils.wrongAns(ProductionGuessActivity.this, binding, finalI, indexAns, answers);

                                FragmentManager fragmentManager = getSupportFragmentManager();
                                statusBar = (StatusBarFragment) fragmentManager.findFragmentById(R.id.status_bar_prod_guess);
                                statusBar.rateDown(xp);

                                transition();
                            }
                        });
                    } else if (i == indexAns) {
                        answers[indexAns].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                editor.putInt("prodGuessAll", sharedPreferences.getInt("prodGuessAll", 0) + 1);
                                editor.putInt("prodGuessCorrect", sharedPreferences.getInt("prodGuessCorrect", 0) + 1);
                                editor.apply();

                                ProdGuessUtils.rightAns(ProductionGuessActivity.this, binding, indexAns, answers);

                                FragmentManager fragmentManager = getSupportFragmentManager();
                                statusBar = (StatusBarFragment) fragmentManager.findFragmentById(R.id.status_bar_prod_guess);
                                statusBar.rateUp(xp);
                                statusBar.levelUp();

                                transition();
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Utils.vibrate(ProductionGuessActivity.this);

        Intent intent = new Intent(ProductionGuessActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        finish();
    }

    public void transition() {
        if (!Objects.equals(getIntent().getStringExtra("previousActivity"), "ProductionGuessActivity") && !Objects.equals(getIntent().getStringExtra("previousActivity"), "adapter")) {
            Random rand = new Random();
            Class<?>[] activities = {CarGuessActivity.class, AccelCompActivity.class, NurbCompActivity.class,
                    PowerGuessActivity.class, PowerCompActivity.class, ProductionGuessActivity.class};
//
//                       RANDOMIC MTNELU HAMAR!!!!!!!!!!!!!!!!!!!!!!!!!
//
            Class<?> Activity;
            while (true) {
                Activity = activities[rand.nextInt(activities.length)];
                if (Activity != ProductionGuessActivity.class) {
                    break;
                }
            }

            Intent intent = new Intent(ProductionGuessActivity.this, Activity);
            intent.putExtra("previousActivity", "ProductionGuessActivity");

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
        } else {
            Intent intent = new Intent(ProductionGuessActivity.this, ProductionGuessActivity.class);
            intent.putExtra("previousActivity", "ProductionGuessActivity");

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
    }
}