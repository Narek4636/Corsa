package com.example.corsa;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.corsa.databinding.ActivityAccelCompBinding;

import java.io.ByteArrayOutputStream;

public class Utils {
    public static int vibrationIntensity = 92;
    public static int vibrationLength = 55;
    public static boolean shouldVibrate = true;

//    MainMenu
    public static int lastImage = R.drawable.vobration_icon;

    private void setVibrationIntensity(int intensity) {
        vibrationIntensity = intensity;
    }

    private int getVibrationIntensity() {
        return vibrationIntensity;
    }

    public static void vibrate(Context context) {
        if(shouldVibrate) {
            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator.hasVibrator()) {
                VibrationEffect vibrationEffect = VibrationEffect.createOneShot(vibrationLength, vibrationIntensity);
                vibrator.vibrate(vibrationEffect);
            }
        }
    }

    public static byte[] getDrawableBytes(Context context, int drawableId) {
        Drawable drawable = context.getResources().getDrawable(drawableId);
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static Drawable byteToDrawable(Context context, byte[] imageBytes) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        BitmapDrawable drawable = new BitmapDrawable(context.getResources(), bitmap);
        return drawable;
    }
}
