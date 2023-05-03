package com.example.corsa.fragments;

import android.os.Bundle;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static StatusBarFragment newInstance (){
        StatusBarFragment statusBarFragment = new StatusBarFragment();
        return statusBarFragment;
    }

    FragmentStatusBarBinding binding;

    public void rateUp(int xp){
        binding.rating.setText(String.valueOf(Integer.parseInt(binding.rating.getText().toString()) + xp));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStatusBarBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}