package com.example.corsa.carRoom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CarDao {
    @Query("SELECT * FROM cars")
    List<CarEntity> getAll();

    @Query("SELECT * FROM cars WHERE id = :carId")
    CarEntity getCarById(int carId);

    @Insert
    long insertCar(CarEntity carEntity);

    @Delete
    int deleteCar(CarEntity carEntity);

    @Update
    int updateCar(CarEntity carEntity);
}
