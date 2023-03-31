package com.example.corsa;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.corsa.databinding.StatusBarLayoutBinding;

public class StatusBar extends Fragment {

    private StatusBarLayoutBinding binding;

    public StatusBar() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public void levelUp(int up){
        int current = Integer.parseInt(binding.level.getText().toString());
        current += up;
        binding.level.setText(current);
    }

    public void ratingIncrease(int up){
        int current = Integer.parseInt(binding.rating.getText().toString());
        current += up;
        binding.rating.setText(current);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = StatusBarLayoutBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}