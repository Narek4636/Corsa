package com.samsung.corsa.components;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.example.corsa.databinding.ActivityPowerGuessBinding;
import com.samsung.corsa.Utils;
import com.samsung.corsa.carRoom.CarEntity;

public class PowerGuessUtils {
    public static void submit(Context context, ActivityPowerGuessBinding binding, CarEntity car) {
        Utils.vibrate(context);
        binding.submitPowerGuess.setEnabled(false);

        double x = binding.sliderPowerGuess.getValue();
        int sub = (int) Math.abs(car.power - x);

        if(sub <= 70) {
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
