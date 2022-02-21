package com.example.lab5.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lab5.data.database.UserDao;
import com.example.lab5.data.entity.User;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FormViewModel extends ViewModel {
    public MutableLiveData<Integer> allUsersCount = new MutableLiveData<>();
    private Disposable disposableAllUsersCount;
    private Disposable disposableSaveUser;
    private Disposable disposableFilteredUsers;
    private Disposable disposableFoundRecords;
    public MutableLiveData<List<User>> filteredUsers = new MutableLiveData<>();
    public MutableLiveData<Integer> foundRecords = new MutableLiveData<>();
    public MutableLiveData<String> filter = new MutableLiveData<>("");
    private final UserDao userDao;

    public FormViewModel(UserDao userDao) {
        this.userDao = userDao;
        disposableAllUsersCount = userDao.countAllRows()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    allUsersCount.postValue(integer);
                });
        filter.observeForever(s -> {
                    disposableFilteredUsers = userDao.getFilteredUsers(s)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(users -> {
                                filteredUsers.postValue(users);
                            });
                    disposableFoundRecords = userDao.countFilteredRows(s)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(integer -> {
                                foundRecords.postValue(integer);
                            });
                }
        );

    }

    public void saveUser(User user) {
        disposableSaveUser = Observable.create(emitter -> {
            userDao.insert(user);
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposableAllUsersCount = null;
        disposableSaveUser = null;
        disposableFilteredUsers = null;
    }
}
