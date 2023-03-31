package com.example.corsa.diffRecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
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

    @Override
    public void onBindViewHolder(@NonNull DiffViewHolder holder, int position) {
        holder.diffName.setText(diffs.get(position).diffName);
        holder.diffName.setTypeface(ResourcesCompat.getFont(context.getApplicationContext(), R.font.exo_2_extrabold));

        holder.xp.setText(diffs.get(position).xp);
        holder.xp.setTypeface(ResourcesCompat.getFont(context.getApplicationContext(), R.font.exo_2_extrabold));

        holder.diffOrHid.setText(diffs.get(position).diffOrHid);
        holder.diffOrHid.setTypeface(ResourcesCompat.getFont(context.getApplicationContext(), R.font.exo_2_extrabold));

        holder.bound1.setText(diffs.get(position).bound1);
        holder.bound1.setTypeface(ResourcesCompat.getFont(context.getApplicationContext(), R.font.exo_2_extrabold));

        holder.bound2.setText(diffs.get(position).bound2);
        holder.bound2.setTypeface(ResourcesCompat.getFont(context.getApplicationContext(), R.font.exo_2_extrabold));

        holder.unit.setText(diffs.get(position).unit);
        holder.unit.setTypeface(ResourcesCompat.getFont(context.getApplicationContext(), R.font.exo_2_extrabold));
    }

    @Override
    public int getItemCount() {
        return diffs.size();
    }
}
