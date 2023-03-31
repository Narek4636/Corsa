package com.example.corsa.diffRecyclerView;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.corsa.R;

public class DiffViewHolder extends RecyclerView.ViewHolder {

    TextView diffName;
    TextView xp;
    TextView diffOrHid;
    TextView bound1;
    TextView bound2;
    TextView unit;

    public DiffViewHolder(@NonNull View itemView) {
        super(itemView);
        diffName = itemView.findViewById(R.id.diffName_menu_card);
        xp = itemView.findViewById(R.id.xp_menu_card);
        diffOrHid = itemView.findViewById(R.id.difference_menu_card);
        bound1 = itemView.findViewById(R.id.bound1_menu_card);
        bound2 = itemView.findViewById(R.id.bound2_menu_card);
        unit = itemView.findViewById(R.id.unit_menu_card);
    }
}
