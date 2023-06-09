package com.samsung.corsa.carRoom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CarDao {
    @Query("SELECT * FROM cars")
    List<CarEntity> getAll();

    @Query("SELECT * FROM cars WHERE id = :carId")
    CarEntity getCarById(int carId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertCar(CarEntity carEntity);

    @Delete
    int deleteCar(CarEntity carEntity);

    @Update
    int updateCar(CarEntity carEntity);

    @Query("DELETE FROM cars")
    void deleteAllCars();
}
