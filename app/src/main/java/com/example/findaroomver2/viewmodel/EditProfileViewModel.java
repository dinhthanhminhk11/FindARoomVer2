package com.example.findaroomver2.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.findaroomver2.repository.Repository;
import com.example.findaroomver2.request.changeInfo.UserEditProfileRequest;
import com.example.findaroomver2.request.login.Data;
import com.example.findaroomver2.response.updateUser.UserUpdateResponse;

import java.util.function.Consumer;

public class EditProfileViewModel extends AndroidViewModel {

    private Repository repository;
    private MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    private MutableLiveData<UserUpdateResponse> userUpdateResponseMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Data> dataUser = new MutableLiveData<>();

    public EditProfileViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public void updateUserInFo(UserEditProfileRequest userEditProfileRequest) {
        mProgressMutableData.postValue(View.VISIBLE);
        repository.updateUserInFo(userEditProfileRequest, new Consumer<UserUpdateResponse>() {
            @Override
            public void accept(UserUpdateResponse userUpdateResponse) {
                userUpdateResponseMutableLiveData.postValue(userUpdateResponse);
                mProgressMutableData.postValue(View.GONE);
            }
        });
    }

    public void getUserById(String id) {
        mProgressMutableData.setValue(View.VISIBLE);
        repository.getUserById(id, new Consumer<Data>() {
            @Override
            public void accept(Data data) {
                dataUser.postValue(data);
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

    public MutableLiveData<Data> getDataUser() {
        return dataUser;
    }
}
