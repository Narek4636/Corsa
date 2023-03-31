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

//    public static final Migration MIGRATION = new Migration(5,6) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("CREATE TABLE cars3 (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name TEXT NOT NULL, power INTEGER NOT NULL, prodYear INTEGER NOT NULL, nurbTime TEXT, accelTime DOUBLE NOT NULL, imagePath TEXT NOT NULL)");
////            database.execSQL("INSERT INTO cars3 (id, name, power, prodYear, nurbTime, accelTime) SELECT id, name, power, prodYear, nurbTime, accelTime FROM cars");
//            database.execSQL("DROP TABLE cars");
//            database.execSQL("ALTER TABLE cars3 RENAME TO cars");
//        }
//    };

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

