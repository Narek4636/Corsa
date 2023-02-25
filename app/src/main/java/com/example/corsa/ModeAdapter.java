package com.example.corsa;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ModeAdapter extends RecyclerView.Adapter<ModeViewHolder> {

    Context context;
    List<Mode> modes;

    public ModeAdapter(Context context, List<Mode> modes) {
        this.context = context;
        this.modes = modes;
    }

    @NonNull
    @Override
    public ModeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ModeViewHolder(LayoutInflater.from(context).inflate(R.layout.view_mode_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ModeViewHolder holder, int position) {
        holder.icon.setImageResource(modes.get(position).icon);
        holder.name.setText(modes.get(position).name);
        holder.name.setTypeface(ResourcesCompat.getFont(context.getApplicationContext(), R.font.exo_2_bold));

//        Random rand = new Random();
//        Class<?>[] activities = {CarGuessActivity.class, AccelCompActivity.class, NurbCompActivity.class,
//                PowerGuessActivity.class, PowerCompActivity.class, ProductionGuessActivity.class};
//        Class<?> Activity = activities[rand.nextInt(activities.length)];

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                switch (modes.get(position).name) {
                    case "Guess The Car":
                        intent = new Intent(context, CarGuessActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("previousActivity", "adapter");
                        context.startActivity(intent);
                        break;
                    case "Acceleration Times Comparison":
                        intent = new Intent(context, AccelCompActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("previousActivity", "adapter");
                        context.startActivity(intent);
                        break;
                    case "Nurburgring Times Comparison":
                        intent = new Intent(context, NurbCompActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("previousActivity", "adapter");
                        context.startActivity(intent);
                        break;
                    case "Price Comparison":
                        intent = new Intent(context, PriceCompActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("previousActivity", "adapter");
                        context.startActivity(intent);
                        break;
                    case "Power Comparison":
                        intent = new Intent(context, PowerCompActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("previousActivity", "adapter");
                        context.startActivity(intent);
                        break;
                    case "Guess The Power":
                        intent = new Intent(context, PowerGuessActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("previousActivity", "adapter");
                        context.startActivity(intent);
                        break;
                    case "Guess The Start Of Production":
                        intent = new Intent(context, ProductionGuessActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("previousActivity", "adapter");
                        context.startActivity(intent);
                        break;
                    case "Random":
                        intent = new Intent(context, RandomActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return modes.size();
    }
}
