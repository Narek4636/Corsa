package com.example.corsa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenu extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button imageGuess = findViewById(R.id.car_guess);
        Button priceComp = findViewById(R.id.price_comp);
        Button powerComp = findViewById(R.id.power_comp);
        Button powerGuess = findViewById(R.id.power_guess);
        Button prodGuess = findViewById(R.id.prod_guess);
        Button nurburgGuess = findViewById(R.id.nurburg_guess);
        Button accelComp = findViewById(R.id.accel_comp);
        Button random = findViewById(R.id.random);

        imageGuess.setOnClickListener(this);
        priceComp.setOnClickListener(this);
        powerGuess.setOnClickListener(this);
        powerComp.setOnClickListener(this);
        prodGuess.setOnClickListener(this);
        nurburgGuess.setOnClickListener(this);
        accelComp.setOnClickListener(this);
        random.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.car_guess:
                Intent imageGuess = new Intent(MainMenu.this, CarGuessActivity.class);
                startActivity(imageGuess);
                break;

            case R.id.price_comp:
                Intent priceComp = new Intent(MainMenu.this, PriceCompActivity.class);
                startActivity(priceComp);
                break;

            case R.id.power_guess:
                Intent powerGuess = new Intent(MainMenu.this, PowerGuessActivity.class);
                startActivity(powerGuess);
                break;
            case R.id.power_comp:
                Intent powerComp = new Intent(MainMenu.this, PowerCompActivity.class);
                startActivity(powerComp);
                break;
            case R.id.prod_guess:
                Intent prodGuess = new Intent(MainMenu.this, ProductionGuessActivity.class);
                startActivity(prodGuess);
                break;
            case R.id.nurburg_guess:
                Intent nurbGuess = new Intent(MainMenu.this, NurbCompActivity.class);
                startActivity(nurbGuess);
                break;
            case R.id.accel_comp:
                Intent accelComp = new Intent(MainMenu.this, AccelCompActivity.class);
                startActivity(accelComp);
                break;
        }
    }
}