package com.example.corsa.components;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.corsa.Utils;
import com.example.corsa.carRoom.CarEntity;
import com.example.corsa.databinding.ActivityPowerGuessBinding;
import com.example.corsa.modes.PowerGuessActivity;

public class PowerGuessUtils {
    public static void submit(Context context, ActivityPowerGuessBinding binding, CarEntity car){
        Utils.vibrate(context);
        binding.submitPowerGuess.setEnabled(false);

        float x = binding.sliderPowerGuess.getValue();
        if(Math.abs(car.power - x) <= 50){
            binding.rightAnsPowerGuess.setVisibility(View.VISIBLE);
            binding.rightPowerPowerGuess.setText(Integer.toString(car.power));
            binding.rightPowerPowerGuess.setTextColor(Color.GREEN);
            binding.imageBlackPowerGuess.setVisibility(View.VISIBLE);
        }
        else{
            binding.powerPowerGuess.setTextColor(Color.RED);
            binding.wrongAnsPowerGuess.setVisibility(View.VISIBLE);
            binding.rightPowerPowerGuess.setText(Integer.toString(car.power));
            binding.imageBlackPowerGuess.setVisibility(View.VISIBLE);
        }
    }
}
