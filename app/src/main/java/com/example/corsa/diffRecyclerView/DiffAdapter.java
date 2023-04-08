package com.example.corsa.diffRecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.corsa.R;

import java.util.List;

public class DiffAdapter extends RecyclerView.Adapter<DiffViewHolder> {

    Context context;
    List<Diff> diffs;

    public DiffAdapter(Context context, List<Diff> diffs) {
        this.context = context;
        this.diffs = diffs;
    }



    @NonNull
    @Override
    public DiffViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DiffViewHolder(LayoutInflater.from(context).inflate(R.layout.view_menu_card, parent, false));
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull DiffViewHolder holder, int position) {
        AppCompatButton bg = holder.itemView.findViewById(R.id.bg_diff_window);
        TextView unit = holder.itemView.findViewById(R.id.unit_menu_card);


        holder.diffName.setText(diffs.get(position).diffName);

        switch (diffs.get(position).diffName){
            case "BEGINNER":
                bg.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.darker_green));
                unit.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.darker_green));
                Log.d("COLOR", holder.diffName.toString());
                break;
            case "SKILLED":
                bg.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.gulf_orange));
                unit.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.gulf_orange));
                break;
            case "HARD":
                bg.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.red));
                unit.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.red));
                break;
            case "EXTREME":
                bg.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.dark_red));
                unit.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.dark_red));
                break;
        }
        holder.diffName.setTypeface(ResourcesCompat.getFont(context.getApplicationContext(), R.font.exo_2_extrabold));

        holder.rating.setTypeface(ResourcesCompat.getFont(context.getApplicationContext(), R.font.exo_2_extrabold));

        holder.xp.setText(String.valueOf(diffs.get(position).xp) + "XP");
        holder.xp.setTypeface(ResourcesCompat.getFont(context.getApplicationContext(), R.font.exo_2_extrabold));

        holder.diffOrHid.setText(diffs.get(position).diffOrHid);
        holder.diffOrHid.setTypeface(ResourcesCompat.getFont(context.getApplicationContext(), R.font.exo_2_extrabold));

        holder.bound1.setText(String.valueOf(diffs.get(position).bound1));
        holder.bound1.setTypeface(ResourcesCompat.getFont(context.getApplicationContext(), R.font.exo_2_extrabold));

        holder.bound2.setText(String.valueOf(diffs.get(position).bound2));
        holder.bound2.setTypeface(ResourcesCompat.getFont(context.getApplicationContext(), R.font.exo_2_extrabold));

        holder.unit.setText(diffs.get(position).unit);
        holder.unit.setTypeface(ResourcesCompat.getFont(context.getApplicationContext(), R.font.exo_2_extrabold));
    }

    @Override
    public int getItemCount() {
        return diffs.size();
    }
}
