package com.example.corsa.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.corsa.R;
import com.example.corsa.databinding.FragmentMainMenuBinding;
import com.example.corsa.databinding.FragmentStatusBarBinding;

public class StatusBarFragment extends Fragment {
    public StatusBarFragment() {
        // Required empty public constructor
    }

    SharedPreferences preferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static StatusBarFragment newInstance() {
        StatusBarFragment statusBarFragment = new StatusBarFragment();
        return statusBarFragment;
    }

    FragmentStatusBarBinding binding;

    public void rateUp(int xp) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("rating", String.valueOf(Integer.parseInt(binding.rating.getText().toString()) + xp));
        editor.apply();

        binding.rating.setText(String.valueOf(Integer.parseInt(preferences.getString("rating", ""))));
    }

    public void levelUp() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("level", String.valueOf(Integer.parseInt(binding.level.getText().toString()) + 1));
        editor.apply();

        binding.level.setText(String.valueOf(Integer.parseInt(preferences.getString("level", ""))));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStatusBarBinding.inflate(inflater, container, false);

        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        if(!preferences.getString("rating", "").equals("") &&
                !preferences.getString("level", "").equals("")) {
            binding.rating.setText(String.valueOf(Integer.parseInt(preferences.getString("rating", ""))));
            binding.level.setText(String.valueOf(Integer.parseInt(preferences.getString("level", ""))));
        }

        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}