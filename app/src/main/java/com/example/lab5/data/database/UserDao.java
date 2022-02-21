package com.example.lab5.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.lab5.data.entity.User;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;


@Dao
public interface UserDao {

    @Query("SELECT COUNT(*) FROM users")
    Flowable<Integer> countAllRows();

    @Query("SELECT COUNT(*) FROM users WHERE  name LIKE :text || '%' OR surname LIKE :text || '%'")
    Flowable<Integer> countFilteredRows(String text);

    @Query("SELECT * FROM users WHERE  name LIKE :text || '%' OR surname LIKE :text || '%'")
    Flowable<List<User>> getFilteredUsers(String text);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);
}
