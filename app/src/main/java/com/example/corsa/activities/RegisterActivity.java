package com.example.corsa.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.corsa.R;
import com.example.corsa.Utils;
import com.example.corsa.databinding.ActivityRegisterBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://corsa-f31dd-default-rtdb.firebaseio.com/");

    boolean isConnected(){

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo!=null){
            if(networkInfo.isConnected())
                return true;
            else
                return false;
        }else
            return false;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        ActivityRegisterBinding binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.registerRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.emailRegister.getText().toString();
                String password1 = binding.password1Register.getText().toString();
                String password2 = binding.password2Register.getText().toString();
                String username = binding.usernameRegister.getText().toString();

                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                Calendar c = Calendar.getInstance();
                String date = sdf.format(c.getTime());

                if (email.equals("") || password1.equals("") || password2.equals("") || username.equals("")) {
                    Toast.makeText(RegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
                else if (!password1.equals(password2)) {
                    Toast.makeText(RegisterActivity.this, "Passwords aren't matching", Toast.LENGTH_SHORT).show();
                }
                else if(password1.length() < 8){
                    Toast.makeText(RegisterActivity.this, "Password should have at least 8 characters", Toast.LENGTH_SHORT).show();;
                }
                else if(username.length() > 25){
                    Toast.makeText(RegisterActivity.this, "Username is too long", Toast.LENGTH_SHORT).show();;
                }
                else {
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Log.i("TAG","tag");

                            if (snapshot.hasChild(username)) {
                                Toast.makeText(RegisterActivity.this, "There is already a user with same username", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                databaseReference.child("users").child(username).child("username").setValue(username);
                                databaseReference.child("users").child(username).child("password").setValue(password1);
                                databaseReference.child("users").child(username).child("email").setValue(email);
                                databaseReference.child("users").child(username).child("regDate").setValue("Registered " + date);
                                databaseReference.child("users").child(username).child("rating").setValue("0");
                                databaseReference.child("users").child(username).child("level").setValue("0");
//                                ------------------------------ACCURACIES----------------------------------------
                                databaseReference.child("users").child(username).child("accelCompCorrect").setValue("0");
                                databaseReference.child("users").child(username).child("accelCompAll").setValue("0");

                                databaseReference.child("users").child(username).child("carGuessCorrect").setValue("0");
                                databaseReference.child("users").child(username).child("carGuessAll").setValue("0");

                                databaseReference.child("users").child(username).child("nurbCompCorrect").setValue("0");
                                databaseReference.child("users").child(username).child("nurbCompAll").setValue("0");

                                databaseReference.child("users").child(username).child("powerCompCorrect").setValue("0");
                                databaseReference.child("users").child(username).child("powerCompAll").setValue("0");

                                databaseReference.child("users").child(username).child("prodGuessCorrect").setValue("0");
                                databaseReference.child("users").child(username).child("prodGuessAll").setValue("0");
//                               ---------------------------------------------------------------------------------

                                Toast.makeText(RegisterActivity.this, "Profile registered successfully!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

//        ---------------------
        TextView back = findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.vibrate(RegisterActivity.this);
                back.setEnabled(false);

                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Utils.vibrate(this);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        finish();
    }
}