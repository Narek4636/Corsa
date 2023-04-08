package com.example.corsa.modes;

import static com.example.corsa.recyclerView.ModeAdapter.PREFS_NAME;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.corsa.R;
import com.example.corsa.Utils;
import com.example.corsa.carRoom.CarDao;
import com.example.corsa.carRoom.CarDatabaseClient;
import com.example.corsa.carRoom.CarEntity;
import com.example.corsa.databinding.ActivityMainMenuBinding;
import com.example.corsa.recyclerView.Mode;
import com.example.corsa.recyclerView.ModeAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainMenu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        ActivityMainMenuBinding binding = ActivityMainMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Icon vibroIcon = new Icon(binding.vibroIconMainMenu);



        vibroIcon.image.setImageResource(Utils.lastImage);

        binding.vibroIconMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vibroIcon.turned_On == false) {
                    vibroIcon.image.setImageResource(R.drawable.vobration_off_icon);
                    Utils.lastImage = R.drawable.vobration_off_icon;
                    Utils.shouldVibrate = false;
                    vibroIcon.turned_On = true;
                } else if (vibroIcon.turned_On == true) {
                    vibroIcon.image.setImageResource(R.drawable.vobration_icon);
                    Utils.lastImage = R.drawable.vobration_icon;
                    Utils.shouldVibrate = true;
                    vibroIcon.turned_On = false;
                }
            }
        });

        List<Mode> modes = new ArrayList<Mode>();
        modes.add(new Mode("Guess The Car", R.drawable.car_guess_icon,21, "DIFFICULTY"));
        modes.add(new Mode("Acceleration Comparison", R.drawable.acceleration_icon, 18,"DIFFICULTY"));
        modes.add(new Mode("Nurburgring Comparison", R.drawable.nurb_icon, 19, "DIFFICULTY"));
        modes.add(new Mode("Power Comparison", R.drawable.power_comp_icon, 20, "DIFFICULTY"));
        modes.add(new Mode("Guess The Power", R.drawable.power_guess_icon, 21, "XP CHART"));
        modes.add(new Mode("Guess The Start Of Production", R.drawable.prod_guess_icon, 16, "DIFFICULTY"));
        modes.add(new Mode("Random", R.drawable.random_icon, 21, "DIFFICULTY"));


        binding.modesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.modesRecyclerView.setAdapter(new ModeAdapter(getApplicationContext(), modes));
    }
    public class Icon {
        ImageView image;
        Boolean turned_On = false;
        int count = 0;

        public Icon(ImageView image) {
            this.image = image;
        }

        public void on() {
            turned_On = true;
        }

        public void off() {
            turned_On = false;
        }
    }
}