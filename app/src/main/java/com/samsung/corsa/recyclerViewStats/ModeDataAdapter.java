package com.samsung.corsa.recyclerViewStats;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.corsa.R;
import com.samsung.corsa.modes.ProductionGuessActivity;

import java.util.List;

public class ModeDataAdapter extends RecyclerView.Adapter<ModeDataViewHolder> {
    Context context;
    List<ModeData> list;
    String accuracy;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public ModeDataAdapter(Context context, List<ModeData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ModeDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ModeDataViewHolder(LayoutInflater.from(context).inflate(R.layout.view_accuracy_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ModeDataViewHolder holder, int position) {
        sharedPreferences = context.getSharedPreferences(ProductionGuessActivity.ACCURACY_PREFS, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        int accuracy;
        if (sharedPreferences.getInt(list.get(position).allAnswers,0) != 0) {
            accuracy = 100 * (int) sharedPreferences.getInt(list.get(position).correctAnswers, 0) / sharedPreferences.getInt(list.get(position).allAnswers, 0);
        }
        else{
            accuracy = 0;
        }
        Log.i("TAGAZ",list.get(position).allAnswers + "\n" + sharedPreferences.getInt(list.get(position).correctAnswers, 0));

        holder.accuracyPercent.setText(String.valueOf(accuracy) + "%");
        holder.progressBar.setProgress(accuracy);
        holder.modeName.setText(list.get(position).modeName);

        holder.modeName.setTypeface(ResourcesCompat.getFont(context.getApplicationContext(), R.font.exo_2_extrabold));
        holder.accuracyPercent.setTypeface(ResourcesCompat.getFont(context.getApplicationContext(), R.font.exo_2_extrabold));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
