package com.samsung.corsa.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.corsa.databinding.FragmentStatusBarBinding;
import com.samsung.corsa.components.StatusBarUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.samsung.corsa.modes.ProductionGuessActivity;

public class StatusBarFragment extends Fragment {
    public StatusBarFragment() {
        // Required empty public constructor
    }

    SharedPreferences preferences, preferences2;
    SharedPreferences.Editor editor;
    FragmentStatusBarBinding binding;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://corsa-f31dd-default-rtdb.firebaseio.com/");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static StatusBarFragment newInstance() {
        StatusBarFragment statusBarFragment = new StatusBarFragment();
        return statusBarFragment;
    }

    public void rateUp(int xp) {
        preferences = getContext().getSharedPreferences(ProductionGuessActivity.PROFILE_PREFS, MODE_PRIVATE);
        preferences2 = getContext().getSharedPreferences(ProductionGuessActivity.ACCURACY_PREFS, MODE_PRIVATE);
        editor = preferences.edit();

        StatusBarUtils.rateUpAnimate(binding, Integer.parseInt(preferences.getString("rating", "")), xp);

        editor.putString("rating", String.valueOf(Integer.parseInt(binding.rating.getText().toString()) + xp));
        editor.apply();

        binding.rating.setText(String.valueOf(Integer.parseInt(preferences.getString("rating", ""))));
//      RATINGY UPDATE ENQ ANUM FIREBASE-UM
        if (preferences.getBoolean("Logged", false)) {
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    reference.child("users").child(preferences.getString("Username", "")).child("rating").setValue(preferences.getString("rating", ""));
//                  --------------------------------------ACCURACIES-----------------------------------------------------------------------------------------------------------------
                    reference.child("users").child(preferences.getString("Username", "")).child("accelCompCorrect").setValue(String.valueOf(preferences2.getInt("accelCompCorrect", 0)));
                    reference.child("users").child(preferences.getString("Username", "")).child("accelCompAll").setValue(String.valueOf(preferences2.getInt("accelCompAll", 0)));

                    reference.child("users").child(preferences.getString("Username", "")).child("carGuessCorrect").setValue(String.valueOf(preferences2.getInt("carGuessCorrect", 0)));
                    reference.child("users").child(preferences.getString("Username", "")).child("carGuessAll").setValue(String.valueOf(preferences2.getInt("carGuessAll", 0)));

                    reference.child("users").child(preferences.getString("Username", "")).child("nurbCompCorrect").setValue(String.valueOf(preferences2.getInt("nurbCompCorrect", 0)));
                    reference.child("users").child(preferences.getString("Username", "")).child("nurbCompAll").setValue(String.valueOf(preferences2.getInt("nurbCompAll", 0)));

                    reference.child("users").child(preferences.getString("Username", "")).child("powerCompCorrect").setValue(String.valueOf(preferences2.getInt("powerCompCorrect", 0)));
                    reference.child("users").child(preferences.getString("Username", "")).child("powerCompAll").setValue(String.valueOf(preferences2.getInt("powerCompAll", 0)));

                    reference.child("users").child(preferences.getString("Username", "")).child("prodGuessCorrect").setValue(String.valueOf(preferences2.getInt("prodGuessCorrect", 0)));
                    reference.child("users").child(preferences.getString("Username", "")).child("prodGuessAll").setValue(String.valueOf(preferences2.getInt("prodGuessAll", 0)));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
//        -----------------------------------
    }

    public void rateDown(int xp) {
        preferences = getContext().getSharedPreferences(ProductionGuessActivity.PROFILE_PREFS, MODE_PRIVATE);
        preferences2 = getContext().getSharedPreferences(ProductionGuessActivity.ACCURACY_PREFS, MODE_PRIVATE);
        editor = preferences.edit();

        if(Integer.parseInt(preferences.getString("rating", "")) - xp / 2 < 0){
            xp = 2*Integer.parseInt(preferences.getString("rating", ""));
        }

        StatusBarUtils.rateDownAnimate(binding, Integer.parseInt(preferences.getString("rating", "")), xp);

        editor.putString("rating", String.valueOf(Integer.parseInt(binding.rating.getText().toString()) - xp / 2));
        editor.apply();

        binding.rating.setText(preferences.getString("rating", ""));
        //      RATINGY UPDATE ENQ ANUM FIREBASE-UM
        if (preferences.getBoolean("Logged", false)) {
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    reference.child("users").child(preferences.getString("Username", "")).child("rating").setValue(preferences.getString("rating", ""));
//                  --------------------------------------ACCURACIES-----------------------------------------------------------------------------------------------------------------
                    reference.child("users").child(preferences.getString("Username", "")).child("accelCompCorrect").setValue(String.valueOf(preferences2.getInt("accelCompCorrect", 0)));
                    reference.child("users").child(preferences.getString("Username", "")).child("accelCompAll").setValue(String.valueOf(preferences2.getInt("accelCompAll", 0)));

                    reference.child("users").child(preferences.getString("Username", "")).child("carGuessCorrect").setValue(String.valueOf(preferences2.getInt("carGuessCorrect", 0)));
                    reference.child("users").child(preferences.getString("Username", "")).child("carGuessAll").setValue(String.valueOf(preferences2.getInt("carGuessAll", 0)));

                    reference.child("users").child(preferences.getString("Username", "")).child("nurbCompCorrect").setValue(String.valueOf(preferences2.getInt("nurbCompCorrect", 0)));
                    reference.child("users").child(preferences.getString("Username", "")).child("nurbCompAll").setValue(String.valueOf(preferences2.getInt("nurbCompAll", 0)));

                    reference.child("users").child(preferences.getString("Username", "")).child("powerCompCorrect").setValue(String.valueOf(preferences2.getInt("powerCompCorrect", 0)));
                    reference.child("users").child(preferences.getString("Username", "")).child("powerCompAll").setValue(String.valueOf(preferences2.getInt("powerCompAll", 0)));

                    reference.child("users").child(preferences.getString("Username", "")).child("prodGuessCorrect").setValue(String.valueOf(preferences2.getInt("prodGuessCorrect", 0)));
                    reference.child("users").child(preferences.getString("Username", "")).child("prodGuessAll").setValue(String.valueOf(preferences2.getInt("prodGuessAll", 0)));
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
//        -----------------------------------
    }

    public void levelUp() {
        preferences = getContext().getSharedPreferences(ProductionGuessActivity.PROFILE_PREFS, MODE_PRIVATE);
        editor = preferences.edit();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("level", String.valueOf(Integer.parseInt(binding.level.getText().toString()) + 1));
        editor.apply();

        binding.level.setText(String.valueOf(Integer.parseInt(preferences.getString("level", ""))));

        //      LEVELY UPDATE ENQ ANUM FIREBASE-UM
        if (preferences.getBoolean("Logged", false)) {
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    reference.child("users").child(preferences.getString("Username", "")).child("level").setValue(preferences.getString("level", ""));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
//        -----------------------------------
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStatusBarBinding.inflate(inflater, container, false);

        preferences = getContext().getSharedPreferences(ProductionGuessActivity.PROFILE_PREFS, MODE_PRIVATE);
        editor = preferences.edit();

        if (preferences.getString("rating", "").equals("")) {
            editor.putString("rating", "0");
            editor.apply();
        }
        if (preferences.getString("level", "").equals("")) {
            editor.putString("level", "0");
            editor.apply();
        }

        binding.rating.setText(preferences.getString("rating", ""));
        binding.level.setText(preferences.getString("level", ""));

        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}