package com.example.findaroomver2.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.findaroomver2.repository.Repository;
import com.example.findaroomver2.request.login.UserLoginRequest;
import com.example.findaroomver2.response.UserResponseLogin;

import java.util.function.Consumer;

public class MainViewModel extends AndroidViewModel {

    private Repository repository;

    MutableLiveData<UserResponseLogin> userResponseLoginMutableLiveData = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public void loginToken(String token){
        repository.loginByToken(token, new Consumer<UserResponseLogin>() {
            @Override
            public void accept(UserResponseLogin userResponseLogin) {
                if (userResponseLogin instanceof UserResponseLogin) {
                    userResponseLoginMutableLiveData.postValue(userResponseLogin);
                }
            }
        });
    }

    public MutableLiveData<UserResponseLogin> getUserResponseLoginMutableLiveData() {
        return userResponseLoginMutableLiveData;
    }
}
