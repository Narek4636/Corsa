package com.example.corsa.components;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.example.corsa.Utils;
import com.example.corsa.databinding.ActivityProductionGuessBinding;
import com.example.corsa.modes.ProductionGuessActivity;

public class ProdGuessUtils {
    public static void wrongAns(Context context, ActivityProductionGuessBinding binding,
                                int finalI, int indexAns, TextView[] answers) {
        Utils.vibrate(context);
        for (TextView i : answers){
            i.setEnabled(false);
        }

            binding.wrongAnsProductionGuess.setVisibility(View.VISIBLE);
        answers[indexAns].setTextColor(Color.GREEN);
        answers[finalI].setTextColor(Color.RED);
        binding.imageBlackProductionGuess.setVisibility(View.VISIBLE);
    }


    public static void rightAns(Context context, ActivityProductionGuessBinding binding
            , int indexAns, TextView[] answers) {
        Utils.vibrate(context);
        for (TextView i : answers){
            i.setEnabled(false);
        }

        binding.rightAnsProductionGuess.setVisibility(View.VISIBLE);
        answers[indexAns].setTextColor(Color.GREEN);
        binding.imageBlackProductionGuess.setVisibility(View.VISIBLE);
    }
}
