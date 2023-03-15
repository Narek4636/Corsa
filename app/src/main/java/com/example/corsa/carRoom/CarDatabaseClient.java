package com.example.corsa.carRoom;


import android.content.Context;

import androidx.room.Room;

public class CarDatabaseClient {
    public static final String DB_NAME = "cars.db";
    public static CarDatabaseClient instance;
    public final CarDatabase carDatabase;

    public CarDatabaseClient(Context context) {
        carDatabase = Room.databaseBuilder(context, CarDatabase.class, DB_NAME).build();
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
