package com.example.corsa.carRoom;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class CarDatabaseClient {
    public static final String DB_NAME = "cars.db";
    public static CarDatabaseClient instance;
    public final CarDatabase carDatabase;

    public static final Migration MIGRATION_2_4 = new Migration(2,4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE cars ADD COLUMN added INTEGER NOT NULL");
        }
    };

    public CarDatabaseClient(Context context) {
        carDatabase = Room.databaseBuilder(context, CarDatabase.class, DB_NAME).addMigrations(MIGRATION_2_4).build();
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
