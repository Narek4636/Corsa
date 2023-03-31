package com.example.corsa.components;

import static com.example.corsa.modes.PowerCompActivity.DELAY_COMP;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.room.ColumnInfo;

import com.example.corsa.Utils;
import com.example.corsa.databinding.ActivityPowerCompBinding;
import com.example.corsa.modes.PowerCompActivity;

public class PowerCompUtils {
    public static void rightAns(Context context, ActivityPowerCompBinding binding,
                                ImageView rightPic, ImageView wrongPic, TextView rightPrice, TextView wrongPrice
                                ){
        Utils.vibrate(context);
        rightPic.setEnabled(false);
        wrongPic.setEnabled(false);

        rightPrice.setVisibility(View.VISIBLE);
        rightPrice.setTextColor(Color.GREEN);
        wrongPrice.setVisibility(View.VISIBLE);
        binding.image1BlackPowerComp.setVisibility(View.VISIBLE);
        binding.image1BlackPowerComp.setAlpha(0.6f);
        binding.image2BlackPowerComp.setAlpha(0.6f);
        binding.hp1PowerComp.setVisibility(View.VISIBLE);
        binding.hp2PowerComp.setVisibility(View.VISIBLE);
    }


    public static void wrongAns(Context context, ActivityPowerCompBinding binding, ImageView rightPic,
                                ImageView wrongPic, TextView rightPrice, TextView wrongPrice
                                ){
        Utils.vibrate(context);
        wrongPic.setEnabled(false);
        rightPic.setEnabled(false);

        rightPrice.setVisibility(View.VISIBLE);
        rightPrice.setTextColor(Color.GREEN);
        wrongPrice.setVisibility(View.VISIBLE);
        wrongPrice.setTextColor(Color.RED);
        binding.image1BlackPowerComp.setAlpha(0.6f);
        binding.image2BlackPowerComp.setAlpha(0.6f);
        binding.hp1PowerComp.setVisibility(View.VISIBLE);
        binding.hp2PowerComp.setVisibility(View.VISIBLE);
    }


    public static void animation(ActivityPowerCompBinding binding, int power1, int power2){
        ValueAnimator animator1 = ValueAnimator.ofInt(power1 * 3 / 4, power1);
        ObjectAnimator alphaAnimator1 = ObjectAnimator.ofFloat(binding.power1PowerComp, View.ALPHA, 0f, 1f);
        ObjectAnimator alphaAnimator1h = ObjectAnimator.ofFloat(binding.hp1PowerComp, View.ALPHA, 0f, 1f);
        alphaAnimator1.setDuration(DELAY_COMP / 4);
        animator1.setDuration(DELAY_COMP);

        AnimatorSet animatorSet1 = new AnimatorSet();
        animatorSet1.playTogether(animator1, alphaAnimator1, alphaAnimator1h);

        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int currentValue = (int) valueAnimator.getAnimatedValue();
                binding.power1PowerComp.setText(String.valueOf(currentValue));
            }
        });
        animatorSet1.start(); // start the animator

        ValueAnimator animator2 = ValueAnimator.ofInt(power2 * 3 / 4, power2);
        ObjectAnimator alphaAnimator2 = ObjectAnimator.ofFloat(binding.power2PowerComp, View.ALPHA, 0f, 1f);
        ObjectAnimator alphaAnimator2h = ObjectAnimator.ofFloat(binding.hp2PowerComp, View.ALPHA, 0f, 1f);
        alphaAnimator2.setDuration(DELAY_COMP / 3);
        animator2.setDuration(DELAY_COMP);

        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(animator2, alphaAnimator2, alphaAnimator2h);

        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int currentValue = (int) valueAnimator.getAnimatedValue();
                binding.power2PowerComp.setText(String.valueOf(currentValue));
            }
        });
        animatorSet2.start(); // start the animator
    }
}
