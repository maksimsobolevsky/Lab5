package com.example.lab5.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(primaryKeys = {"name", "surname"},tableName = "users")
public class User {
    @NonNull
    private String name="";

    public User(@NonNull String name, @NonNull String surname, String comment) {
        this.name = name;
        this.surname = surname;
        this.comment = comment;
    }

    @NonNull
    private String surname="";
    private String comment;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
