package com.samsung.corsa.fragments;

import static android.content.Context.MODE_PRIVATE;
import static com.samsung.corsa.recyclerViewModes.ModeAdapter.PREFS_NAME;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.corsa.R;
import com.example.corsa.databinding.FragmentMainMenuBinding;
import com.samsung.corsa.Utils;
import com.samsung.corsa.recyclerViewModes.Mode;
import com.samsung.corsa.recyclerViewModes.ModeAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainMenuFragment extends Fragment {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMainMenuBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    FragmentMainMenuBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Icon vibroIcon = new Icon(binding.vibroIconMainMenu);
        vibroIcon.image.setImageResource(Utils.lastImage);

        binding.vibroIconMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!vibroIcon.turned_On) {
                    vibroIcon.image.setImageResource(R.drawable.vobration_off_icon);
                    Utils.lastImage = R.drawable.vobration_off_icon;
                    Utils.shouldVibrate = false;
                    vibroIcon.turned_On = true;
                } else if (vibroIcon.turned_On) {
                    vibroIcon.image.setImageResource(R.drawable.vobration_icon);
                    Utils.lastImage = R.drawable.vobration_icon;
                    Utils.shouldVibrate = true;
                    vibroIcon.turned_On = false;
                }
            }
        });

        List<Mode> modes = new ArrayList<Mode>();
        modes.add(new Mode("Guess The Car", R.drawable.car_guess_icon, 21, "DIFFICULTY"));
        modes.add(new Mode("Acceleration Comparison", R.drawable.acceleration_icon, 18, "DIFFICULTY"));
        modes.add(new Mode("Guess The Car By Interior", R.drawable.interior_guess_icon, 18, "DIFFICULTY"));
        modes.add(new Mode("Nurburgring Comparison", R.drawable.nurb_icon, 19, "DIFFICULTY"));
        modes.add(new Mode("Power Comparison", R.drawable.power_comp_icon, 20, "DIFFICULTY"));
        modes.add(new Mode("Guess The Power", R.drawable.power_guess_icon, 21, "XP CHART"));
        modes.add(new Mode("Guess The Start Of Production", R.drawable.prod_guess_icon, 16, "DIFFICULTY"));
        modes.add(new Mode("Random", R.drawable.random_icon, 21, "DIFFICULTY"));


        binding.modesRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        binding.modesRecyclerView.setAdapter(new ModeAdapter(this.getActivity().getApplicationContext(), modes));
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
    //    ---------------------------------------------------------
}