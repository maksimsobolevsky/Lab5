package com.example.lab5.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.lab5.data.entity.User;

@Database(entities = User.class,version=1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}