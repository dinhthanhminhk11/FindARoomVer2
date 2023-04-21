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

public class LoginViewModel extends AndroidViewModel {
    private Repository repository;

    MutableLiveData<Integer> progress = new MutableLiveData<>();// cho thằng loading
    MutableLiveData<UserResponseLogin> userResponseLoginMutableLiveData = new MutableLiveData<>();


    public LoginViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public void login(UserLoginRequest userLoginRequest) {
        progress.postValue(View.VISIBLE);
        repository.login(userLoginRequest, new Consumer<UserResponseLogin>() {
            @Override
            public void accept(UserResponseLogin userResponseLogin) {
                if (userLoginRequest instanceof UserLoginRequest) {
                    userResponseLoginMutableLiveData.postValue(userResponseLogin);
                    progress.postValue(View.GONE);
                }
            }
        });
    }

    public MutableLiveData<Integer> getProgress() {
        return progress;
    }

    public MutableLiveData<UserResponseLogin> getUserResponseLoginMutableLiveData() {
        return userResponseLoginMutableLiveData;
    }
}
