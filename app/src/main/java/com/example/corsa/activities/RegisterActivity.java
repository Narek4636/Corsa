package com.example.corsa.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Database;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.corsa.R;
import com.example.corsa.Utils;
import com.example.corsa.databinding.ActivityPowerGuessBinding;
import com.example.corsa.databinding.ActivityRegisterBinding;
import com.example.corsa.fragments.LoginFragment;
import com.example.corsa.fragments.MainMenuFragment;
import com.example.corsa.modes.PowerGuessActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://corsa-f31dd-default-rtdb.firebaseio.com/");

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

                if (email.equals("") || password1.equals("") || password2.equals("") || username.equals("")) {
                    Toast.makeText(RegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
                else if (!password1.equals(password2)) {
                    Toast.makeText(RegisterActivity.this, "Password aren't matching", Toast.LENGTH_SHORT).show();
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
                                databaseReference.child("users").child(username).child("rating").setValue("0");

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
        TextView menu = findViewById(R.id.return_button);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.vibrate(RegisterActivity.this);
                menu.setEnabled(false);

                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
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