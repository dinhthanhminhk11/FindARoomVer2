package com.example.findaroomver2.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.findaroomver2.repository.Repository;
import com.example.findaroomver2.response.updateUser.UserUpdateResponse;

import java.util.function.Consumer;

public class UpdateAccountViewModel extends AndroidViewModel {
    private Repository repository;
    private MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    private MutableLiveData<UserUpdateResponse> userUpdateResponseMutableLiveData = new MutableLiveData<>();

    public UpdateAccountViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public void updateAccount(String id) {
        mProgressMutableData.postValue(View.VISIBLE);
        repository.updateAccount(id, new Consumer<UserUpdateResponse>() {
            @Override
            public void accept(UserUpdateResponse userUpdateResponse) {
                userUpdateResponseMutableLiveData.postValue(userUpdateResponse);
                mProgressMutableData.postValue(View.GONE);
            }
        });
    }

    public MutableLiveData<Integer> getmProgressMutableData() {
        return mProgressMutableData;
    }

    public MutableLiveData<UserUpdateResponse> getUserUpdateResponseMutableLiveData() {
        return userUpdateResponseMutableLiveData;
    }
}
