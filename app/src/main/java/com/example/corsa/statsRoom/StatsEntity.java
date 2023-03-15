package com.example.corsa.statsRoom;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.net.Inet4Address;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

@Entity(tableName = "stats")
public class StatsEntity implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "attempts")
    public Integer attempts;

    @ColumnInfo(name = "succeed")
    public Integer succeed;

    @ColumnInfo(name = "level")
    public Integer level;

    @ColumnInfo(name = "time")
    public TimeUnit time;
}
