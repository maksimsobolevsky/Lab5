package com.example.lab5.ui.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.lab5.data.database.UserDao;

public class FormViewModelFactory implements ViewModelProvider.Factory {

    private UserDao userDao;

    public FormViewModelFactory(UserDao userDao) {
        this.userDao = userDao;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new FormViewModel(userDao);
    }
}
