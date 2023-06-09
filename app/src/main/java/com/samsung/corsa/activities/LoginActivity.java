package com.samsung.corsa.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.corsa.R;
import com.example.corsa.databinding.ActivityLoginBinding;
import com.samsung.corsa.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.samsung.corsa.modes.ProductionGuessActivity;

public class LoginActivity extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://corsa-f31dd-default-rtdb.firebaseio.com/");
    SharedPreferences preferences, preferencesAcc;
    SharedPreferences.Editor editor, editorAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.loginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.usernameLogin.getText().toString();
                String password = binding.passwordLogin.getText().toString();

                if (username.equals("") || password.equals("")) {
                    Toast.makeText(LoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(username)) {
                                preferences = getSharedPreferences(ProductionGuessActivity.PROFILE_PREFS, MODE_PRIVATE);
                                preferencesAcc = getSharedPreferences(ProductionGuessActivity.ACCURACY_PREFS, MODE_PRIVATE);
                                editor = preferences.edit();
                                editorAcc = preferencesAcc.edit();
                                editor.putString("Username", username);
                                editor.apply();

                                String getPassword = snapshot.child(username).child("password").getValue(String.class);
                                String getRating = snapshot.child(username).child("rating").getValue(String.class);
                                String getDate = snapshot.child(username).child("regDate").getValue(String.class);
                                String getLevel = snapshot.child(username).child("level").getValue(String.class);
//                                -------------ACCURACY--------------------
                                int accelCompCorrect = Integer.parseInt(snapshot.child(username).child("accelCompCorrect").getValue(String.class));
                                int accelCompAll = Integer.parseInt(snapshot.child(username).child("accelCompAll").getValue(String.class));

                                int carGuessCorrect = Integer.parseInt(snapshot.child(username).child("carGuessCorrect").getValue(String.class));
                                int carGuessAll = Integer.parseInt(snapshot.child(username).child("carGuessAll").getValue(String.class));

                                int nurbCompCorrect = Integer.parseInt(snapshot.child(username).child("nurbCompCorrect").getValue(String.class));
                                int nurbCompAll = Integer.parseInt(snapshot.child(username).child("nurbCompAll").getValue(String.class));

                                int powerCompCorrect = Integer.parseInt(snapshot.child(username).child("powerCompCorrect").getValue(String.class));
                                int powerCompAll = Integer.parseInt(snapshot.child(username).child("powerCompAll").getValue(String.class));

                                int prodGuessCorrect = Integer.parseInt(snapshot.child(username).child("prodGuessCorrect").getValue(String.class));
                                int prodGuessAll = Integer.parseInt(snapshot.child(username).child("prodGuessAll").getValue(String.class));

                                int interiorGuessCorrect = Integer.parseInt(snapshot.child(username).child("interiorGuessCorrect").getValue(String.class));
                                int interiorGuessAll = Integer.parseInt(snapshot.child(username).child("interiorGuessAll").getValue(String.class));
//                                -------------------------------------------------------------

                                if (getPassword.equals(password)) {
                                    Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();

                                    editor.putBoolean("Logged", true);
                                    editor.putString("rating", getRating);
                                    editor.putString("level", getLevel);
                                    editor.putString("regDate", getDate);
                                    editor.apply();
//                                   -----------------ACCURACY-----------------------------------------
                                    editorAcc.putInt("accelCompCorrect", accelCompCorrect);
                                    editorAcc.putInt("accelCompAll", accelCompAll);

                                    editorAcc.putInt("carGuessCorrect", carGuessCorrect);
                                    editorAcc.putInt("carGuessAll", carGuessAll);

                                    editorAcc.putInt("nurbCompCorrect", nurbCompCorrect);
                                    editorAcc.putInt("nurbCompAll", nurbCompAll);

                                    editorAcc.putInt("powerCompCorrect", powerCompCorrect);
                                    editorAcc.putInt("powerCompAll", powerCompAll);

                                    editorAcc.putInt("prodGuessCorrect", prodGuessCorrect);
                                    editorAcc.putInt("prodGuessAll", prodGuessAll);

                                    editorAcc.putInt("interiorGuessCorrect", interiorGuessCorrect);
                                    editorAcc.putInt("interiorGuessAll", interiorGuessAll);
                                    editorAcc.apply();

                                    Log.i("TAGVOR", getRating);

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                                    ;
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "Wrong username", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        binding.createLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        TextView menu = findViewById(R.id.return_button);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.vibrate(LoginActivity.this);
                menu.setEnabled(false);

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                finish();
            }
        });
    }


    @Override
    public void onBackPressed() {
        Utils.vibrate(this);
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        finish();
    }
}