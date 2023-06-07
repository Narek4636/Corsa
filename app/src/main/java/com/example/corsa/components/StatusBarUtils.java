package com.example.corsa.components;

import static com.example.corsa.modes.CarGuessActivity.DELAY_GUESS;
import static com.example.corsa.modes.PowerCompActivity.DELAY_COMP;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.view.View;

import com.example.corsa.R;
import com.example.corsa.databinding.FragmentStatusBarBinding;

public class StatusBarUtils {
    public static void rateUpAnimate(FragmentStatusBarBinding binding, int rating, int xp) {

        ValueAnimator animator1 = ValueAnimator.ofInt(rating, rating + xp);
        animator1.setDuration(DELAY_GUESS);

        AnimatorSet animatorSet1 = new AnimatorSet();
        animatorSet1.playTogether(animator1);

        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int currentValue = (int) valueAnimator.getAnimatedValue();
                binding.rating.setText(String.valueOf(currentValue));
            }
        });
        animatorSet1.start();
        binding.rating.setTextColor(Color.parseColor("#006400"));
        binding.level.setTextColor(Color.parseColor("#006400"));
    }

    public static void rateDownAnimate(FragmentStatusBarBinding binding, int rating, int xp) {

        ValueAnimator animator1 = ValueAnimator.ofInt(rating, ((int) rating - xp / 2));
        animator1.setDuration(DELAY_GUESS);

        AnimatorSet animatorSet1 = new AnimatorSet();
        animatorSet1.playTogether(animator1);

        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int currentValue = (int) valueAnimator.getAnimatedValue();
                binding.rating.setText(String.valueOf(currentValue));
            }
        });
        animatorSet1.start();
        binding.rating.setTextColor(Color.RED);
        binding.level.setTextColor(Color.RED);
    }


}
