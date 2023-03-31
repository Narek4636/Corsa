package com.example.corsa.components;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.example.corsa.Utils;
import com.example.corsa.databinding.ActivityCarGuessBinding;
import com.example.corsa.modes.CarGuessActivity;

import org.w3c.dom.Text;

public class CarGuessUtils {
    public static void rightAns(Context context, ActivityCarGuessBinding binding,
                                TextView[] answers, int indexAns){
        Utils.vibrate(context);
        for(TextView i : answers){
            i.setEnabled(false);
        }

        binding.rightAnsCarGuess.setVisibility(View.VISIBLE);
        answers[indexAns].setTextColor(Color.GREEN);
        binding.imageBlackCarGuess.setVisibility(View.VISIBLE);
    }

    public static void wrongAns(Context context, ActivityCarGuessBinding binding,
                                TextView[] answers, int finalI, int indexAns){
        Utils.vibrate(context);
        for(TextView i : answers){
            i.setEnabled(false);
        }

        binding.wrongAnsCarGuess.setVisibility(View.VISIBLE);
        answers[indexAns].setTextColor(Color.GREEN);
        answers[finalI].setTextColor(Color.RED);
        binding.imageBlackCarGuess.setVisibility(View.VISIBLE);
    }
}
