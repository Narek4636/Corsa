package com.example.corsa.modes;

import static com.example.corsa.modes.ProductionGuessActivity.ACCURACY_PREFS;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import com.example.corsa.activities.MainActivity;
import com.example.corsa.R;
import com.example.corsa.Utils;
import com.example.corsa.carRoom.CarEntity;
import com.example.corsa.components.PowerCompUtils;
import com.example.corsa.databinding.ActivityPowerCompBinding;
import com.example.corsa.fragments.StatusBarFragment;
import com.example.corsa.viewModels.CarViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class PowerCompActivity extends AppCompatActivity {

    public final static int DELAY_COMP = 2500;
    TextView rightPrice;
    TextView wrongPrice;
    ImageView rightPic;
    ImageView wrongPic;
    ArrayList<CarEntity> carList;
    StatusBarFragment statusBar;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public static final String POWER_COMP_PREFS = "POWER_COMP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_comp);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.status_bar_power_comp, new StatusBarFragment()).commit();

        Intent intent = getIntent();

        if (intent.getStringExtra("b1") != null && intent.getStringExtra("b2") != null
                && intent.getStringExtra("xp") != null) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("b1PowerComp", intent.getStringExtra("b1"));
            editor.putString("b2PowerComp", intent.getStringExtra("b2"));
            editor.putString("xpPowerComp", intent.getStringExtra("xp"));
            editor.apply();
        }

        Double bound1;
        Double bound2;
        int xp;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        Log.d("TAG", preferences.getString("b1","") + " " + preferences.getString("b2", ""));
        if (Objects.equals(preferences.getString("b1PowerComp", ""), "") &&
                Objects.equals(preferences.getString("b2PowerComp", ""), "") &&
                Objects.equals(preferences.getString("xpPowerComp", ""), "")) {
            bound1 = 50.0;
            bound2 = 70.0;
            xp = 1;
        } else {
            bound1 = Double.parseDouble(preferences.getString("b1PowerComp", ""));
            bound2 = Double.parseDouble(preferences.getString("b2PowerComp", ""));
            xp = Integer.parseInt(preferences.getString("xpPowerComp", ""));
        }

        ActivityPowerCompBinding binding = ActivityPowerCompBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        carList = new ArrayList<>();

        CarViewModel carListViewModel = new CarViewModel(getApplication());
        carListViewModel.readCars();

        carListViewModel.getCars().observe(this, new Observer<List<CarEntity>>() {
            @Override
            public void onChanged(List<CarEntity> carEntities) {
                carList.addAll(carEntities);

                Random rand = new Random();
                int indexPic1;
                int indexPic2;
                while (true) {
                    indexPic1 = rand.nextInt(carList.size());
                    CarEntity car1 = carList.get(indexPic1);
                    boolean test = false;

                    for (CarEntity i : carList) {
                        indexPic2 = rand.nextInt(carList.size());
                        CarEntity car2 = carList.get(indexPic2);
                        if (indexPic2 != indexPic1 && !Objects.equals(car1.power, car2.power) &&
                                Math.abs(car1.power - car2.power) <= bound2 &&
                                Math.abs(car1.power - car2.power) >= bound1) {
                            test = true;
                            break;
                        }
                    }
                    if (test) {
                        while (true) {
                            indexPic2 = rand.nextInt(carList.size());
                            int sub = Math.abs(carList.get(indexPic1).power - carList.get(indexPic2).power);
                            if (indexPic2 != indexPic1 && !Objects.equals(carList.get(indexPic1).power, carList.get(indexPic2).power) &&
                                    sub >= bound1 && sub <= bound2) {
                                break;
                            }
                        }
                        break;
                    }
                }
                CarEntity car1 = carList.get(indexPic1);
                CarEntity car2 = carList.get(indexPic2);

                binding.image1PowerComp.setImageResource(getResources().getIdentifier(car1.imagePath, "drawable", getPackageName()));
                binding.image2PowerComp.setImageResource(getResources().getIdentifier(car2.imagePath, "drawable", getPackageName()));
                binding.name1PowerComp.setText(car1.name);
                binding.name2PowerComp.setText(car2.name);
                binding.year1PowerComp.setText(String.valueOf(car1.prodYear));
                binding.year2PowerComp.setText(String.valueOf(car2.prodYear));

                int power1 = car1.power;
                int power2 = car2.power;

                binding.power1PowerComp.setText(String.valueOf(car1.power));
                binding.power2PowerComp.setText(String.valueOf(car2.power));

                if (power1 > power2) {
                    rightPrice = binding.power1PowerComp;
                    rightPic = binding.image1PowerComp;
                    wrongPrice = binding.power2PowerComp;
                    wrongPic = binding.image2PowerComp;
                } else {
                    rightPrice = binding.power2PowerComp;
                    rightPic = binding.image2PowerComp;
                    wrongPrice = binding.power1PowerComp;
                    wrongPic = binding.image1PowerComp;
                }

                TextView menu = findViewById(R.id.return_button);
                menu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utils.vibrate(PowerCompActivity.this);
                        menu.setEnabled(false);

                        Intent intent = new Intent(PowerCompActivity.this, MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        finish();
                    }
                });

                sharedPreferences = getSharedPreferences(ACCURACY_PREFS, MODE_PRIVATE);
                editor = sharedPreferences.edit();

                rightPic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editor.putInt("powerCompAll", sharedPreferences.getInt("powerCompAll",0)+1);
                        editor.putInt("powerCompCorrect", sharedPreferences.getInt("powerCompCorrect",0)+1);
                        editor.apply();

                        PowerCompUtils.rightAns(PowerCompActivity.this, binding, rightPic, wrongPic, rightPrice, wrongPrice);
                        PowerCompUtils.animation(binding, power1, power2);

                        FragmentManager fragmentManager = getSupportFragmentManager();
                        statusBar = (StatusBarFragment) fragmentManager.findFragmentById(R.id.status_bar_power_comp);
                        statusBar.rateUp(xp);
                        statusBar.levelUp();

                        transition();
                    }
                });

                wrongPic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editor.putInt("powerCompAll", sharedPreferences.getInt("powerCompAll",0)+1);
                        editor.apply();

                        PowerCompUtils.wrongAns(PowerCompActivity.this, binding, rightPic, wrongPic, rightPrice, wrongPrice);
                        PowerCompUtils.animation(binding, power1, power2);

                        FragmentManager fragmentManager = getSupportFragmentManager();
                        statusBar = (StatusBarFragment) fragmentManager.findFragmentById(R.id.status_bar_power_comp);
                        statusBar.rateDown(xp);

                        transition();
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        Utils.vibrate(PowerCompActivity.this);

        Intent intent = new Intent(PowerCompActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        finish();
    }

    //    -------------------------------------------------------------------TRANSITION----------------------------------------------------------
    public void transition() {
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
                    }, DELAY_COMP + 200);
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
                    }, DELAY_COMP + 200);
                }
            });
        }
    }
}