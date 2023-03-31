package com.example.corsa.components;

import static com.example.corsa.modes.PowerCompActivity.DELAY_COMP;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.corsa.R;
import com.example.corsa.Utils;
import com.example.corsa.databinding.ActivityNurbCompBinding;
import com.example.corsa.modes.MainMenu;
import com.example.corsa.modes.NurbCompActivity;

import java.util.concurrent.TimeUnit;

public class NurbCompUtils {
    public static void rightAns(Context context, ActivityNurbCompBinding binding,
                                ImageView rightPic, ImageView wrongPic, TextView rightPrice,
                                TextView wrongPrice){
        Utils.vibrate(context);
        rightPic.setEnabled(false);
        wrongPic.setEnabled(false);

        rightPrice.setVisibility(View.VISIBLE);
        rightPrice.setTextColor(Color.GREEN);
        wrongPrice.setVisibility(View.VISIBLE);
        binding.image1BlackNurbComp.setAlpha(0.6f);
        binding.image2BlackNurbComp.setAlpha(0.6f);
        binding.ms1NurbComp.setVisibility(View.VISIBLE);
        binding.ms2NurbComp.setVisibility(View.VISIBLE);
    }



    public static void wrongAns(Context context, ActivityNurbCompBinding binding, ImageView rightPic,
                                ImageView wrongPic, TextView rightPrice,
                                TextView wrongPrice){
        Utils.vibrate(context);
        wrongPic.setEnabled(false);
        rightPic.setEnabled(false);

        rightPrice.setVisibility(View.VISIBLE);
        rightPrice.setTextColor(Color.GREEN);
        wrongPrice.setVisibility(View.VISIBLE);
        wrongPrice.setTextColor(Color.RED);
        binding.image1BlackNurbComp.setAlpha(0.6f);
        binding.image2BlackNurbComp.setAlpha(0.6f);
        binding.ms1NurbComp.setVisibility(View.VISIBLE);
        binding.ms2NurbComp.setVisibility(View.VISIBLE);
    }



    public static void animate(ActivityNurbCompBinding binding, int sum1, int sum2){
        ValueAnimator animator1 = ValueAnimator.ofInt(sum1 * 3 / 4, sum1);
        ObjectAnimator alphaAnimator1 = ObjectAnimator.ofFloat(binding.time1NurbComp, View.ALPHA, 0f, 1f);
        ObjectAnimator alphaAnimator1h = ObjectAnimator.ofFloat(binding.ms1NurbComp, View.ALPHA, 0f, 1f);
        alphaAnimator1.setDuration(DELAY_COMP / 4);
        animator1.setDuration(DELAY_COMP);

        AnimatorSet animatorSet1 = new AnimatorSet();
        animatorSet1.playTogether(animator1, alphaAnimator1, alphaAnimator1h);

        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int seconds = (int) valueAnimator.getAnimatedValue();
                long currentMinute = TimeUnit.SECONDS.toMinutes(seconds);
                long currentSecond = seconds - TimeUnit.SECONDS.toMinutes(seconds) * 60;

                String timeString1 = String.format("%d:%02d", currentMinute, currentSecond);
                binding.time1NurbComp.setText(timeString1);
            }
        });
        animatorSet1.start(); // start the animator

        ValueAnimator animator2 = ValueAnimator.ofInt(sum2 * 3 / 4, sum2);
        ObjectAnimator alphaAnimator2 = ObjectAnimator.ofFloat(binding.time2NurbComp, View.ALPHA, 0f, 1f);
        ObjectAnimator alphaAnimator2h = ObjectAnimator.ofFloat(binding.ms2NurbComp, View.ALPHA, 0f, 1f);
        alphaAnimator2.setDuration(DELAY_COMP / 4);
        animator2.setDuration(DELAY_COMP);

        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(animator2, alphaAnimator2, alphaAnimator2h);

        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int seconds = (int) valueAnimator.getAnimatedValue();
                long currentMinute = TimeUnit.SECONDS.toMinutes(seconds);
                long currentSecond = seconds - TimeUnit.SECONDS.toMinutes(seconds) * 60;

                String timeString2 = String.format("%d:%02d", currentMinute, currentSecond);
                binding.time2NurbComp.setText(timeString2);
            }
        });
        animatorSet2.start(); // start the animator
    }
}
