package com.example.corsa.modes;

import static com.example.corsa.modes.CarGuessActivity.DELAY_GUESS;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import com.example.corsa.activities.MainActivity;
import com.example.corsa.R;
import com.example.corsa.Utils;
import com.example.corsa.carRoom.CarEntity;
import com.example.corsa.components.PWGuess;
import com.example.corsa.components.PowerGuessUtils;
import com.example.corsa.databinding.ActivityPowerGuessBinding;
import com.example.corsa.fragments.StatusBarFragment;
import com.example.corsa.viewModels.CarViewModel;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class PowerGuessActivity extends AppCompatActivity {
    ArrayList<CarEntity> carList;
    StatusBarFragment statusBar;
    public static final String POWER_GUESS_PREFS = "POWER_GUESS";

    static ArrayList<PWGuess> xp_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_guess);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        xp_list.add(new PWGuess(50, 70, 1));
        xp_list.add(new PWGuess(20, 50, 2));
        xp_list.add(new PWGuess(5, 20, 5));
        xp_list.add(new PWGuess(0, 5, 10));

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.status_bar_power_guess, new StatusBarFragment()).commit();

        ActivityPowerGuessBinding binding = ActivityPowerGuessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        carList = new ArrayList<>();

        CarViewModel carListViewModel = new CarViewModel(getApplication());
        carListViewModel.readCars();

        carListViewModel.getCars().observe(this, new Observer<List<CarEntity>>() {
            @Override
            public void onChanged(List<CarEntity> carEntities) {
                carList.addAll(carEntities);

                Random r = new Random();
                int i = r.nextInt(carList.size());
                CarEntity car = carList.get(i);
                binding.imagePowerGuess.setImageResource(getResources().getIdentifier(car.imagePath, "drawable", getPackageName()));
                binding.namePowerGuess.setText(car.name);
                binding.yearPowerGuess.setText(String.valueOf(car.prodYear));
                binding.powerPowerGuess.setText("100");

                binding.sliderPowerGuess.addOnChangeListener(new Slider.OnChangeListener() {
                    @Override
                    public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                        binding.powerPowerGuess.setText(String.valueOf(value).substring(0, String.valueOf(value).length() - 2));
                    }
                });

//              MENU
                TextView menu = findViewById(R.id.return_button);
                menu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utils.vibrate(PowerGuessActivity.this);
                        menu.setEnabled(false);

                        Intent intent = new Intent(PowerGuessActivity.this, MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        finish();
                    }
                });


                binding.submitPowerGuess.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PowerGuessUtils.submit(PowerGuessActivity.this, binding, car);

                        FragmentManager fragmentManager = getSupportFragmentManager();
                        statusBar = (StatusBarFragment) fragmentManager.findFragmentById(R.id.status_bar_power_guess);

                        if(getXP((int) binding.sliderPowerGuess.getValue(), car.power) > 0) {
                            statusBar.rateUp(getXP((int) binding.sliderPowerGuess.getValue(), car.power));
                            statusBar.levelUp();
                        }
                        else{
                            statusBar.rateDown(6);
                        }

                        transition();
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        Utils.vibrate(PowerGuessActivity.this);

        Intent intent = new Intent(PowerGuessActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        finish();
    }

    public static int getXP(int player, int real) {
        for (PWGuess i : xp_list) {
            int sub = Math.abs(player - real);
            if (sub >= i.b1 && sub <= i.b2)
                return i.xp;
        }
        return 0;
    }

    public void transition() {
        if (!Objects.equals(getIntent().getStringExtra("previousActivity"), "PowerGuessActivity") && !Objects.equals(getIntent().getStringExtra("previousActivity"), "adapter")) {
            Random rand = new Random();
            Class<?>[] activities = {CarGuessActivity.class, AccelCompActivity.class, NurbCompActivity.class,
                    PowerGuessActivity.class, PowerCompActivity.class, ProductionGuessActivity.class};
//
//                       RANDOMIC MTNELU HAMAR!!!!!!!!!!!!!!!!!!!!!!!!!
//
            Class<?> Activity;
            while (true) {
                Activity = activities[rand.nextInt(activities.length)];
                if (Activity != PowerGuessActivity.class) {
                    break;
                }
            }

            Intent intent = new Intent(PowerGuessActivity.this, Activity);
            intent.putExtra("previousActivity", "PowerGuessActivity");

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
                    }, DELAY_GUESS + 200);
                }
            });
        } else {
            Intent intent = new Intent(PowerGuessActivity.this, PowerGuessActivity.class);
            intent.putExtra("previousActivity", "PowerGuessActivity");

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
                    }, DELAY_GUESS + 200);
                }
            });
        }
    }
}