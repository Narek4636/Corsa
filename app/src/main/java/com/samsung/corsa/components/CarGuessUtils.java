package com.samsung.corsa.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.example.corsa.databinding.ActivityCarGuessBinding;
import com.samsung.corsa.Utils;

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

    public static Bitmap crop(int hiddenSize, Context context, int image){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), image);
        int croppedWidth = (int) (bitmap.getWidth() * Math.sqrt(1 - hiddenSize/100d));
        int croppedHeight = (int) (bitmap.getHeight() * Math.sqrt(1 - hiddenSize/100d));

        int x = (int) (Math.random() * (bitmap.getWidth() - croppedWidth));
        int y = (int) (Math.random() * (bitmap.getHeight() - croppedHeight));

        return Bitmap.createBitmap(bitmap, x, y, croppedWidth, croppedHeight);
    }
}
