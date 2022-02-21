package com.example.lab5;

import android.app.Application;

import androidx.room.Room;

import com.example.lab5.data.database.UserDatabase;

public class App extends Application {

    public UserDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, "user-database").build();
    }
}
