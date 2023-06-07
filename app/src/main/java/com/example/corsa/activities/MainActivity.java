package com.example.corsa.activities;

import static com.example.corsa.modes.ProductionGuessActivity.PROD_GUESS_PREFS;
import static com.example.corsa.modes.ProductionGuessActivity.PROFILE_PREFS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.corsa.R;
import com.example.corsa.databinding.ActivityMainBinding;
import com.example.corsa.fragments.MainMenuFragment;
import com.example.corsa.fragments.ProfileFragment;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_place, new MainMenuFragment()).commit();

//        SharedPreferences preferences = getSharedPreferences(PROFILE_PREFS, MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putBoolean("Logged", false);
//        editor.apply();

        binding.navbar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.single:
                        FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
                        fragmentTransaction1.replace(R.id.fragment_place, new MainMenuFragment()).commit();
                        break;
                    case R.id.multi:
                        Toast.makeText(MainActivity.this, "Will be added soon", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.profile:
                        SharedPreferences preferences = getSharedPreferences(PROFILE_PREFS, MODE_PRIVATE);
                        boolean loginStatus = preferences.getBoolean("Logged", false);

                        Log.i("TAG", String.valueOf(loginStatus));

                        if(loginStatus == false) {
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                            finish();
                        }
                        else if(loginStatus){
                            FragmentTransaction fragmentTransaction3 = fragmentManager.beginTransaction();
                            fragmentTransaction3.replace(R.id.fragment_place, new ProfileFragment()).commit();
                        }

                        break;
                }
                return false;
            }
        });
    }
}