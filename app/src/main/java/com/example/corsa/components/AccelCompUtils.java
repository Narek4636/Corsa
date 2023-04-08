package com.example.corsa.components;

import static android.content.Intent.getIntent;

import static com.example.corsa.modes.PowerCompActivity.DELAY_COMP;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.corsa.R;
import com.example.corsa.Utils;
import com.example.corsa.databinding.ActivityAccelCompBinding;
import com.example.corsa.modes.AccelCompActivity;
import com.example.corsa.modes.CarGuessActivity;
import com.example.corsa.modes.NurbCompActivity;
import com.example.corsa.modes.PowerCompActivity;
import com.example.corsa.modes.PowerGuessActivity;
import com.example.corsa.modes.ProductionGuessActivity;

import java.util.Objects;
import java.util.Random;

public class AccelCompUtils {

    public static void rightAns(Context context, TextView rightPrice, ImageView rightPic,
                                         ImageView wrongPic, TextView wrongPrice,
                                         ActivityAccelCompBinding binding){
        Utils.vibrate(context);
        rightPic.setEnabled(false);
        wrongPic.setEnabled(false);

        rightPrice.setVisibility(View.VISIBLE);
        rightPrice.setTextColor(Color.GREEN);
        wrongPrice.setVisibility(View.VISIBLE);
        binding.image1BlackAccelComp.setAlpha(0.7f);
        binding.image2BlackAccelComp.setAlpha(0.7f);
        binding.sec1AccelComp.setVisibility(View.VISIBLE);
        binding.sec2AccelComp.setVisibility(View.VISIBLE);
    }

    public static void wrongAns(Context context, TextView rightPrice, ImageView rightPic,
                                         ImageView wrongPic, TextView wrongPrice,
                                         ActivityAccelCompBinding binding){
        Utils.vibrate(context);
        wrongPic.setEnabled(false);
        rightPic.setEnabled(false);

        rightPrice.setVisibility(View.VISIBLE);
        rightPrice.setTextColor(Color.GREEN);
        wrongPrice.setVisibility(View.VISIBLE);
        wrongPrice.setTextColor(Color.RED);
        binding.image1BlackAccelComp.setAlpha(0.7f);
        binding.image2BlackAccelComp.setAlpha(0.7f);
        binding.sec1AccelComp.setVisibility(View.VISIBLE);
        binding.sec2AccelComp.setVisibility(View.VISIBLE);
    }

    public static void animate(ActivityAccelCompBinding binding, double time1, double time2){
        ValueAnimator animator1 = ValueAnimator.ofFloat((float) time1 * 4 / 10, (float) time1);
        ObjectAnimator alphaAnimator1 = ObjectAnimator.ofFloat(binding.time1AccelComp, View.ALPHA, 0f, 1f);
        ObjectAnimator alphaAnimator1h = ObjectAnimator.ofFloat(binding.sec1AccelComp, View.ALPHA, 0f, 1f);
        alphaAnimator1.setDuration(DELAY_COMP / 4);
        animator1.setDuration(DELAY_COMP);

        AnimatorSet animatorSet1 = new AnimatorSet();
        animatorSet1.playTogether(animator1, alphaAnimator1, alphaAnimator1h);

        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                binding.time1AccelComp.setText(String.format("%.1f", value)); // Update the text view with the animated value
            }
        });
        animatorSet1.start(); // start the animator

        ValueAnimator animator2 = ValueAnimator.ofFloat((float) time2 / 3, (float) time2);
        ObjectAnimator alphaAnimator2 = ObjectAnimator.ofFloat(binding.time2AccelComp, View.ALPHA, 0f, 1f);
        ObjectAnimator alphaAnimator2h = ObjectAnimator.ofFloat(binding.sec2AccelComp, View.ALPHA, 0f, 1f);
        alphaAnimator2.setDuration(DELAY_COMP / 4);
        animator2.setDuration(DELAY_COMP);

        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(animator2, alphaAnimator2, alphaAnimator2h);

        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                binding.time2AccelComp.setText(String.format("%.1f", value)); // Update the text view with the animated value
            }
        });
        animatorSet2.start(); // start the animator
    }
}
