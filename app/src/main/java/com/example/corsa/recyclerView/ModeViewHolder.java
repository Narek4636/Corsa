package com.example.corsa.recyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.corsa.R;

public class ModeViewHolder extends RecyclerView.ViewHolder {

    ImageView icon;
    TextView name;
    TextView dName;

    public ModeViewHolder(@NonNull View itemView) {
        super(itemView);
        icon = itemView.findViewById(R.id.image_view_card);
        name = itemView.findViewById(R.id.name_view_card);
        dName = itemView.findViewById(R.id.difficultyT_view_card);
    }
}