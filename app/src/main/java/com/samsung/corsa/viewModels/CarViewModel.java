package com.samsung.corsa.viewModels;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.samsung.corsa.carRoom.CarDatabase;
import com.samsung.corsa.carRoom.CarDatabaseClient;
import com.samsung.corsa.carRoom.CarEntity;

import java.util.ArrayList;
import java.util.List;

public class CarViewModel extends AndroidViewModel {
    private MutableLiveData<List<CarEntity>> cars;
    private CarDatabase carDatabase;

    public CarViewModel(@NonNull Application application) {
        super(application);
        cars = new MutableLiveData<>();
        carDatabase = CarDatabaseClient.getInstance(getApplication()).getCarDatabase();
    }

    public LiveData<List<CarEntity>> getCars() {
        return cars;
    }

    public void addCar(CarEntity carEntity) {
        AsyncTask.execute(() -> {
            carDatabase.carDao().insertCar(carEntity);
            List<CarEntity> currentList = carDatabase.carDao().getAll();
            cars.postValue(currentList);
        });
    }

    public void addCars(ArrayList<CarEntity> carEntities) {
        AsyncTask.execute(() -> {
            for(CarEntity items : carEntities){
                carDatabase.carDao().insertCar(items);
            }
            List<CarEntity> currentList = carDatabase.carDao().getAll();
            cars.postValue(currentList);
        });
    }

    public void deleteAllCars(){
        AsyncTask.execute(() -> {
            carDatabase.carDao().deleteAllCars();
            List<CarEntity> currentList = carDatabase.carDao().getAll();
            cars.postValue(currentList);
        });
    }

    public void readCars(){
        AsyncTask.execute(() -> {
            List<CarEntity> currentList = carDatabase.carDao().getAll();
            cars.postValue(currentList);
        });
    }
}
