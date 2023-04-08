package com.example.corsa.carRoom;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class CarDatabaseClient {
    public static final String DB_NAME = "carsData.db";
    public static CarDatabaseClient instance;
    public final CarDatabase carDatabase;

    public CarDatabaseClient(Context context) {
        carDatabase = Room.databaseBuilder(context, CarDatabase.class, DB_NAME).fallbackToDestructiveMigration().build();
    }

    public static synchronized CarDatabaseClient getInstance(Context context) {
        if (instance == null) {
            instance = new CarDatabaseClient(context);
        }
        return instance;
    }

    public CarDatabase getCarDatabase() {
        return carDatabase;
    }
}

