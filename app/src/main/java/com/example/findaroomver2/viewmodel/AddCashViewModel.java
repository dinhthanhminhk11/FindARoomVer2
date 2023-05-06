package com.example.findaroomver2.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.findaroomver2.repository.Repository;
import com.example.findaroomver2.request.money.CashFlowRequest;
import com.example.findaroomver2.response.TextResponse;

import java.util.function.Consumer;

public class AddCashViewModel extends AndroidViewModel {
    private Repository repository;
    private  MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    private MutableLiveData<TextResponse> textResponseMutableLiveData = new MutableLiveData<>();

    public AddCashViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public void createCashByUser(CashFlowRequest cashFlowRequest) {
        mProgressMutableData.postValue(View.VISIBLE);
        repository.createCashByUser(cashFlowRequest, new Consumer<TextResponse>() {
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

    public MutableLiveData<TextResponse> getTestResponseMutableLiveData() {
        return textResponseMutableLiveData;
    }
}
