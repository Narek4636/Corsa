package com.example.corsa.modes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;

import com.example.corsa.R;
import com.example.corsa.Utils;
import com.example.corsa.carRoom.CarDao;
import com.example.corsa.carRoom.CarDatabase;
import com.example.corsa.carRoom.CarDatabaseClient;
import com.example.corsa.carRoom.CarEntity;
import com.example.corsa.recycleView.Mode;
import com.example.corsa.recycleView.ModeAdapter;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainMenu extends AppCompatActivity {

    private CarDao carDao;
    private List<CarEntity> carsList;
    private CarDatabaseClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        RecyclerView recyclerView = findViewById(R.id.modes_recycler_view);
        ImageView vibro_image = findViewById(R.id.vibro_icon_main_menu);
        Icon vibro_icon = new Icon(vibro_image);

        vibro_icon.image.setImageResource(Utils.lastImage);

        vibro_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vibro_icon.turned_On == false) {
                    vibro_icon.image.setImageResource(R.drawable.vobration_off_icon);
                    Utils.lastImage = R.drawable.vobration_off_icon;
                    Utils.shouldVibrate = false;
                    vibro_icon.turned_On = true;
                } else if (vibro_icon.turned_On == true) {
                    vibro_icon.image.setImageResource(R.drawable.vobration_icon);
                    Utils.lastImage = R.drawable.vobration_icon;
                    Utils.shouldVibrate = true;
                    vibro_icon.turned_On = false;
                }
            }
        });

        List<Mode> modes = new ArrayList<Mode>();
        modes.add(new Mode("Guess The Car", R.drawable.car_guess_icon));
        modes.add(new Mode("Acceleration Times Comparison", R.drawable.acceleration_icon));
        modes.add(new Mode("Nurburgring Times Comparison", R.drawable.nurb_icon));
        modes.add(new Mode("Price Comparison", R.drawable.price_comparison_icon));
        modes.add(new Mode("Power Comparison", R.drawable.power_comp_icon));
        modes.add(new Mode("Guess The Power", R.drawable.power_guess_icon));
        modes.add(new Mode("Guess The Start Of Production", R.drawable.prod_guess_icon));
        modes.add(new Mode("Random", R.drawable.random_icon));


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ModeAdapter(getApplicationContext(), modes));

        client = CarDatabaseClient.getInstance(this);
        carDao = client.getCarDatabase().carDao();

        new DatabaseThread().execute();
    }

    private class DatabaseThread extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            CarEntity c1 = new CarEntity("LAMBORGHINI HURACAN LP610-4", 610, 2014, "729", 2.9, Utils.getDrawableBytes(MainMenu.this, R.drawable.huracan_lp610));
            CarEntity c2 = new CarEntity("BMW M1", 277, 1978, "101", 6.2, Utils.getDrawableBytes(MainMenu.this, R.drawable.bmw_m1));
            CarEntity c3 = new CarEntity("MERCEDES SL65 AMG BLACK SERIES", 670, 2009, "751", 3.8, Utils.getDrawableBytes(MainMenu.this, R.drawable.sl65_black_series));

            long r1 = client.getCarDatabase().carDao().insertCar(c1);
            long r2 = client.getCarDatabase().carDao().insertCar(c2);
            long r3 = client.getCarDatabase().carDao().insertCar(c3);
            int update1 = client.getCarDatabase().carDao().updateCar(c1);
            int update2 = client.getCarDatabase().carDao().updateCar(c2);
            int update3 = client.getCarDatabase().carDao().updateCar(c3);
            return null;
        }
    }


    public class Icon {
        ImageView image;
        Boolean turned_On = false;
        int count = 0;

        public Icon(ImageView image) {
            this.image = image;
        }

        public void on() {
            turned_On = true;
        }

        public void off() {
            turned_On = false;
        }
    }
}