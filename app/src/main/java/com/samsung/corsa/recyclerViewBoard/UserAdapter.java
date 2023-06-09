package com.samsung.corsa.recyclerViewBoard;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.corsa.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {

    Context context;
    List<User> users;

    public UserAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(LayoutInflater.from(context).inflate(R.layout.view_user_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.username.setText(users.get(position).username);
        holder.rating.setText(users.get(position).rating);
        holder.position.setText(String.valueOf(position + 1) + ".");
        holder.username.setTypeface(ResourcesCompat.getFont(context.getApplicationContext(), R.font.exo_2_extrabold));
        holder.rating.setTypeface(ResourcesCompat.getFont(context.getApplicationContext(), R.font.exo_2_extrabold));

        switch (position){
            case 0:
                holder.position.setTextColor(Color.parseColor("#FFD700"));
                break;
            case 1:
                holder.position.setTextColor(Color.parseColor("#C0C0C0"));
                break;
            case 2:
                holder.position.setTextColor(Color.parseColor("#CD7F32"));
                break;
            default:
                holder.position.setTextColor(Color.WHITE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
