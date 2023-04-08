package com.example.corsa;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_status_bar, container, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}