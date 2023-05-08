package com.example.findaroomver2.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.findaroomver2.repository.Repository;
import com.example.findaroomver2.request.changepass.ChangePasswordRequest;
import com.example.findaroomver2.response.TextResponse;

import java.util.function.Consumer;

public class ChangePassViewModel extends AndroidViewModel {
    private Repository repository;
    private MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    private MutableLiveData<TextResponse> textResponseMutableLiveData = new MutableLiveData<>();

    public ChangePassViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        mProgressMutableData.postValue(View.VISIBLE);
        repository.changePassword(changePasswordRequest, new Consumer<TextResponse>() {
            @Override
            public void accept(TextResponse textResponse) {
                mProgressMutableData.postValue(View.GONE);
                textResponseMutableLiveData.postValue(textResponse);
            }
        });
    }

    public MutableLiveData<Integer> getmProgressMutableData() {
        return mProgressMutableData;
    }

    public MutableLiveData<TextResponse> getTextResponseMutableLiveData() {
        return textResponseMutableLiveData;
    }
}
