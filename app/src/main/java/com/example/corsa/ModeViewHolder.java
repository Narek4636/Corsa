package com.example.corsa;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ModeViewHolder extends RecyclerView.ViewHolder {

    ImageView icon;
    TextView name;

    public ModeViewHolder(@NonNull View itemView) {
        super(itemView);
        icon = itemView.findViewById(R.id.image_main_menu);
        name = itemView.findViewById(R.id.name_main_menu);
    }
}