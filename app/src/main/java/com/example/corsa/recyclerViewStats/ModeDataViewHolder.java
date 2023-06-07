package com.example.corsa.recyclerViewStats;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.corsa.R;

public class ModeDataViewHolder extends RecyclerView.ViewHolder {
    ProgressBar progressBar;
    TextView modeName;
    TextView accuracyPercent;

    public ModeDataViewHolder(@NonNull View itemView) {
        super(itemView);
        progressBar = itemView.findViewById(R.id.accuracy_circle);
        modeName = itemView.findViewById(R.id.mode_name);
        accuracyPercent = itemView.findViewById(R.id.accuracy_percent);
    }
}
