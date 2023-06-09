package com.samsung.corsa.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.corsa.R;
import com.example.corsa.databinding.FragmentProfileBinding;
import com.samsung.corsa.recyclerViewStats.ModeData;
import com.samsung.corsa.recyclerViewStats.ModeDataAdapter;
import com.samsung.corsa.recyclerViewBoard.User;
import com.samsung.corsa.recyclerViewBoard.UserAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.samsung.corsa.modes.ProductionGuessActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;
    SharedPreferences preferences, preferencesAcc;
    SharedPreferences.Editor editor;
    DatabaseReference reference;
    StorageReference storageReference;
    ArrayList<User> users;
    UserAdapter userAdapter;
    TextView userId;
    List<ModeData> modeDataList;

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        preferences = getContext().getSharedPreferences(ProductionGuessActivity.PROFILE_PREFS, MODE_PRIVATE);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userId = view.findViewById(R.id.user_id);
        preferences = getContext().getSharedPreferences(ProductionGuessActivity.PROFILE_PREFS, MODE_PRIVATE);
        preferencesAcc = getContext().getSharedPreferences(ProductionGuessActivity.ACCURACY_PREFS, MODE_PRIVATE);
        editor = preferencesAcc.edit();

//        -------------------------OVERALL------------------------------
        int overAllAccuracy;
        int correctSum = preferencesAcc.getInt("accelCompCorrect", 0) +
                preferencesAcc.getInt("carGuessCorrect", 0) +
                preferencesAcc.getInt("nurbCompCorrect", 0) +
                preferencesAcc.getInt("powerCompCorrect", 0) +
                preferencesAcc.getInt("interiorGuessCorrect", 0) +
                preferencesAcc.getInt("prodGuessCorrect", 0);

        int allSum = preferencesAcc.getInt("accelCompAll", 0) +
                preferencesAcc.getInt("carGuessAll", 0) +
                preferencesAcc.getInt("nurbCompAll", 0) +
                preferencesAcc.getInt("powerCompAll", 0) +
                preferencesAcc.getInt("interiorGuessAll", 0) +
                preferencesAcc.getInt("prodGuessAll", 0);

        if (allSum != 0) {
            overAllAccuracy = (int) 100 * correctSum / allSum;
        } else {
            overAllAccuracy = 0;
        }
//        -----------------

        binding.overallAccuracy.setProgress(overAllAccuracy);
        binding.overallAccuracyText.setText(String.valueOf(overAllAccuracy) + "%");
        binding.xpProfile.setText(preferences.getString("rating", ""));
        binding.levelProfile.setText(preferences.getString("level", ""));
        binding.regDate.setText(preferences.getString("regDate", ""));

        modeDataList = new ArrayList<>();
        modeDataList.add(new ModeData("accelCompAll", "accelCompCorrect", "Accel. Comp."));
        modeDataList.add(new ModeData("carGuessAll", "carGuessCorrect", "Car Guess"));
        modeDataList.add(new ModeData("interiorGuessAll", "interiorGuessCorrect", "Inter. Guess"));
        modeDataList.add(new ModeData("nurbCompAll", "nurbCompCorrect", "Nurb. Comp."));
        modeDataList.add(new ModeData("powerCompAll", "powerCompCorrect", "Power Comp."));
        modeDataList.add(new ModeData("prodGuessAll", "prodGuessCorrect", "Prod. Guess"));

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.accuracyRecycler.setLayoutManager(layoutManager);
        binding.accuracyRecycler.setAdapter(new ModeDataAdapter(getContext(), modeDataList));

        String username = preferences.getString("Username", "");
        binding.usernameProfile.setText(username);

        binding.logoutProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences = getContext().getSharedPreferences(ProductionGuessActivity.PROFILE_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("Logged", false);
                editor.apply();

                Toast.makeText(getContext(), "Logged out successfully!", Toast.LENGTH_SHORT).show();
            }
        });
        loadUsers();

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.userRecyclerView.setAdapter(new UserAdapter(getContext(), users));
                binding.swipeRefresh.setRefreshing(false);
            }
        });
    }

    public void loadUsers() {
        reference = FirebaseDatabase.getInstance().getReference("users");
        binding.userRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        users = new ArrayList<User>();
        binding.userRecyclerView.setHasFixedSize(true);
//        binding.userRecyclerView.setAdapter(new UserAdapter(getContext(), users));

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    String username = dataSnapshot.child("username").getValue(String.class);
                    String rating = dataSnapshot.child("rating").getValue(String.class);
//                  ----------------------------------
                    User user = new User(rating, username);
//                  --------------
                    users.add(user);
                }
                Collections.sort(users);
                Collections.reverse(users);
                binding.userRecyclerView.setAdapter(new UserAdapter(getContext(), users));
                Log.i("SIZE", String.valueOf(users.size()));
//                userAdapter.notifyDataSetChanged();
                binding.progressbar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}