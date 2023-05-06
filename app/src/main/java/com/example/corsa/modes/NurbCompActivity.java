package com.example.corsa.modes;

import static com.example.corsa.modes.PowerCompActivity.DELAY_COMP;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import com.example.corsa.activities.MainActivity;
import com.example.corsa.NurbTimes;
import com.example.corsa.R;
import com.example.corsa.Utils;
import com.example.corsa.carRoom.CarEntity;
import com.example.corsa.components.NurbCompUtils;
import com.example.corsa.databinding.ActivityNurbCompBinding;
import com.example.corsa.fragments.StatusBarFragment;
import com.example.corsa.viewModels.CarViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class NurbCompActivity extends AppCompatActivity {
    
    TextView rightPrice;
    TextView wrongPrice;
    ImageView rightPic;
    ImageView wrongPic;
    ArrayList<CarEntity> carList;
    public static final String NURB_COMP_PREFS = "NURB_COMP";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurb_comp);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.status_bar_nurb_comp, new StatusBarFragment()).commit();

        Intent intent = getIntent();

        if(intent.getStringExtra("b1") != null && intent.getStringExtra("b2") != null) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("b1NurbComp", intent.getStringExtra("b1"));
            editor.putString("b2NurbComp", intent.getStringExtra("b2"));
            editor.apply();
        }

        int bound1;
        int bound2;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        Log.d("TAG", preferences.getString("b1","") + " " + preferences.getString("b2", ""));
        if(Objects.equals(preferences.getString("b1NurbComp", ""), "") &&
                Objects.equals(preferences.getString("b2NurbComp", ""), "")){
            bound1 = 20;
            bound2 = 40;
        }
            else{
            bound1 = Integer.parseInt(preferences.getString("b1NurbComp", ""));
            bound2 = Integer.parseInt(preferences.getString("b2NurbComp", ""));
        }

        ActivityNurbCompBinding binding = ActivityNurbCompBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        carList = new ArrayList<>();

        CarViewModel carListViewModel = new CarViewModel(getApplication());
        carListViewModel.readCars();

        carListViewModel.getCars().observe(this, new Observer<List<CarEntity>>() {
            @Override
            public void onChanged(List<CarEntity> carEntities) {
                carList.addAll(carEntities);

                ArrayList<CarEntity> hasTime = new ArrayList<>();
                for(CarEntity i : carList){
                    if(!Objects.equals(i.nurbTime, "1")){
                        hasTime.add(i);
                    }
                }

                Random rand = new Random();

                int indexPic1 = rand.nextInt(hasTime.size());
                int indexPic2 = 0;

                Log.d("TAG", String.valueOf(hasTime.get(indexPic1).prodYear));
                Log.d("TAG", String.valueOf(hasTime.get(indexPic2).prodYear));

                while (true) {
                    indexPic2 = rand.nextInt(hasTime.size());
                    NurbTimes t1 = new NurbTimes(hasTime.get(indexPic1).nurbTime);
                    NurbTimes t2 = new NurbTimes(hasTime.get(indexPic2).nurbTime);
                    int sec1 = t1.MM *60 + t1.SS;
                    int sec2 = t2.MM *60 + t2.SS;
                    int sub = Math.abs(sec1 - sec2);
                    if (indexPic2 != indexPic1 &&
                        sub >= bound1 && sub <= bound2) {
                        break;
                    }
                }
                CarEntity car2 = hasTime.get(indexPic2);
                CarEntity car1 = hasTime.get(indexPic1);

                binding.image1NurbComp.setImageResource(getResources().getIdentifier(car1.imagePath, "drawable", getPackageName()));
                binding.image2NurbComp.setImageResource(getResources().getIdentifier(car2.imagePath, "drawable", getPackageName()));
                binding.name1NurbComp.setText(car1.name);
                binding.name2NurbComp.setText(car2.name);
                binding.year1NurbComp.setText(Integer.toString(car1.prodYear));
                binding.year2NurbComp.setText(Integer.toString(car2.prodYear));

                NurbTimes time1 = new NurbTimes(car1.nurbTime);
                NurbTimes time2 = new NurbTimes(car2.nurbTime);
                int sum1 = time1.MM * 60 + time1.SS;
                int sum2 = time2.MM * 60 + time2.SS;

                binding.time1NurbComp.setText(car1.nurbTime);
                binding.time2NurbComp.setText(car2.nurbTime);

                if (sum1 < sum2) {
                    rightPrice = binding.time1NurbComp;
                    rightPic = binding.image1NurbComp;
                    wrongPrice = binding.time2NurbComp;
                    wrongPic = binding.image2NurbComp;
                } else {
                    rightPrice = binding.time2NurbComp;
                    rightPic = binding.image2NurbComp;
                    wrongPrice = binding.time1NurbComp;
                    wrongPic = binding.image1NurbComp;
                }

                TextView menu = findViewById(R.id.return_button);
                menu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utils.vibrate(NurbCompActivity.this);
                        menu.setEnabled(false);

                        Intent intent = new Intent(NurbCompActivity.this, MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        finish();
                    }
                });

                rightPic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NurbCompUtils.rightAns(NurbCompActivity.this,binding,
                                rightPic, wrongPic, rightPrice, wrongPrice);

                        NurbCompUtils.animate(binding, sum1, sum2);

                        transition();
                    }
                });

                wrongPic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NurbCompUtils.wrongAns(NurbCompActivity.this, binding,rightPic,wrongPic,rightPrice,wrongPrice);

                        NurbCompUtils.animate(binding, sum1, sum2);

                        transition();
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        Utils.vibrate(NurbCompActivity.this);

        Intent intent = new Intent(NurbCompActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        finish();
    }

    //    -----------------------------------------TRANSITION------------------------------------------------------------------------------------------------------------------------------
    public void transition(){
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
}