package com.example.corsa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PriceCompMidModeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_price_comp_mid_mode);

        Button return_button = findViewById(R.id.return_price_comp);
        Button next_button = findViewById(R.id.next_price_comp);

        return_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PriceCompMidModeActivity.this, MainMenu.class);
                startActivity(i);
            }
        });

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PriceCompMidModeActivity.this, PriceCompActivity.class);
                startActivity(i);
            }
        });
    }
}