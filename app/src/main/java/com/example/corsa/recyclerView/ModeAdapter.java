package com.example.corsa.recyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.PopupWindowCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.corsa.databinding.ActivityMainMenuBinding;
import com.example.corsa.modes.AccelCompActivity;
import com.example.corsa.modes.CarGuessActivity;
import com.example.corsa.modes.NurbCompActivity;
import com.example.corsa.modes.PowerCompActivity;
import com.example.corsa.modes.PowerGuessActivity;
import com.example.corsa.modes.ProductionGuessActivity;
import com.example.corsa.R;
import com.example.corsa.modes.RandomActivity;
import com.example.corsa.Utils;

import java.util.List;

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
    public void onBindViewHolder(@NonNull ModeViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.icon.setImageResource(modes.get(position).icon);
        holder.name.setText(modes.get(position).name);
        holder.name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, modes.get(position).textSize);
        holder.dName.setText(modes.get(position).dName);
        holder.name.setTypeface(ResourcesCompat.getFont(context.getApplicationContext(), R.font.exo_2_bold));

        Button button = holder.itemView.findViewById(R.id.difficulty_view_card);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupWindow popupWindow = new PopupWindow(context);
                View popupView = LayoutInflater.from(context).inflate(R.layout.view_menu_card, null);
                popupWindow.setContentView(popupView);


            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.vibrate(context);
                holder.itemView.setEnabled(false);

                Intent intent;
                switch (modes.get(position).name) {
                    case "Guess The Car":
                        intent = new Intent(context, CarGuessActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("previousActivity", "adapter");
                        context.startActivity(intent);
                        break;
                    case "Acceleration Comparison":
                        intent = new Intent(context, AccelCompActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("previousActivity", "adapter");
                        context.startActivity(intent);
                        break;
                    case "Nurburgring Comparison":
                        intent = new Intent(context, NurbCompActivity.class);
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
                        TextView diff = holder.itemView.findViewById(R.id.difficulty_view_card);
                        diff.setText("XP CHART");
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
