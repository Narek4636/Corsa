package com.samsung.corsa.recyclerViewBoard;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.corsa.R;

public class UserViewHolder extends RecyclerView.ViewHolder {

    ImageView pic;
    TextView username;
    TextView rating;
    TextView position;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        pic = itemView.findViewById(R.id.pic_user);
        username = itemView.findViewById(R.id.username_user);
        rating = itemView.findViewById(R.id.rating_user);
        position = itemView.findViewById(R.id.user_id);
    }
}
