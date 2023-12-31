package com.example.findaroomver2.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.findaroomver2.repository.Repository;
import com.example.findaroomver2.request.login.UserLoginRequest;
import com.example.findaroomver2.response.TextResponse;
import com.example.findaroomver2.response.UserResponseLogin;

import java.util.function.Consumer;

public class NewPassViewModel extends AndroidViewModel {
    private Repository repository;

    MutableLiveData<Integer> progress = new MutableLiveData<>();
    MutableLiveData<TextResponse> textResponseMutableLiveData = new MutableLiveData<>();
    MutableLiveData<UserResponseLogin> userResponseLoginMutableLiveData = new MutableLiveData<>();
    public NewPassViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public void newPassWord(UserLoginRequest userLogin) {
        progress.setValue(View.VISIBLE);
        repository.newPassWord(userLogin, new Consumer<TextResponse>() {
            @Override
            public void accept(TextResponse textResponse) {
                if (textResponse instanceof TextResponse) {
                    textResponseMutableLiveData.postValue(textResponse);
                    progress.postValue(View.GONE);
                }
            }
        });
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

    public MutableLiveData<TextResponse> getTextResponseMutableLiveData() {
        return textResponseMutableLiveData;
    }

    public MutableLiveData<UserResponseLogin> getUserResponseLoginMutableLiveData() {
        return userResponseLoginMutableLiveData;
    }
}
