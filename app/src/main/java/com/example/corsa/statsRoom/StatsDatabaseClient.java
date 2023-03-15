package com.example.corsa.statsRoom;

import android.content.Context;

import androidx.room.Room;

import com.example.corsa.carRoom.CarDatabase;
import com.example.corsa.carRoom.CarDatabaseClient;

public class StatsDatabaseClient {
    private static final String DB_NAME = "stats.db";
    private static StatsDatabaseClient instance;
    private final StatsDatabase statsDatabase;

    private StatsDatabaseClient(Context context) {
        statsDatabase = Room.databaseBuilder(context, StatsDatabase.class, DB_NAME).build();
    }

    public static synchronized StatsDatabaseClient getInstance(Context context) {
        if (instance == null) {
            instance = new StatsDatabaseClient(context);
        }
        return instance;
    }

    public StatsDatabase getStatsDatabase() {
        return statsDatabase;
    }
}
