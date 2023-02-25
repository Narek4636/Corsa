package com.example.corsa;

import android.app.ListActivity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        RecyclerView recyclerView = findViewById(R.id.modes_recycler_view);

        List<Mode> modes = new ArrayList<Mode>();
        modes.add(new Mode("Guess The Car", R.drawable.carguess_icon));
        modes.add(new Mode("Acceleration Times Comparison", R.drawable.acceleration_icon));
        modes.add(new Mode("Nurburgring Times Comparison", R.drawable.nurb_icon));
        modes.add(new Mode("Price Comparison", R.drawable.price_comparison_icon));
        modes.add(new Mode("Power Comparison", R.drawable.power_comp_icon));
        modes.add(new Mode("Guess The Power", R.drawable.power_guess_icon));
        modes.add(new Mode("Guess The Start Of Production", R.drawable.prod_guess));
        modes.add(new Mode("Random", R.drawable.random));


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ModeAdapter(getApplicationContext(), modes));
    }
}