package com.example.findaroomver2.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.findaroomver2.repository.Repository;
import com.example.findaroomver2.response.money.CashFlowResponse;

import java.util.List;
import java.util.function.Consumer;

public class CashPayViewModel extends AndroidViewModel {
    private Repository repository;
    private MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    private MutableLiveData<Integer> getPrice = new MutableLiveData<>();
    private MutableLiveData<List<CashFlowResponse>> listMutableLiveData = new MutableLiveData<>();

    public CashPayViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public void getPricePayCashByUser(String id) {
        mProgressMutableData.postValue(View.VISIBLE);
        repository.getPriceCashPay(id, new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                mProgressMutableData.postValue(View.GONE);
                getPrice.postValue(integer);
            }
        });
    }

    public void getListPayCash(String id) {
        mProgressMutableData.postValue(View.VISIBLE);
        repository.getListPayCashFlow(id, new Consumer<List<CashFlowResponse>>() {
            @Override
            public void accept(List<CashFlowResponse> cashFlowResponses) {
                mProgressMutableData.postValue(View.GONE);
                listMutableLiveData.postValue(cashFlowResponses);
            }
        });
    }

    public MutableLiveData<Integer> getmProgressMutableData() {
        return mProgressMutableData;
    }

    public MutableLiveData<Integer> getGetPrice() {
        return getPrice;
    }

    public MutableLiveData<List<CashFlowResponse>> getListMutableLiveData() {
        return listMutableLiveData;
    }
}
