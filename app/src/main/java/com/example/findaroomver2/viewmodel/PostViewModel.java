package com.example.findaroomver2.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.findaroomver2.repository.Repository;
import com.example.findaroomver2.response.supplement.DataSupplement;

import java.util.function.Consumer;

public class PostViewModel extends AndroidViewModel {
    private Repository repository;

    MutableLiveData<Integer> progress = new MutableLiveData<>();
    MutableLiveData<DataSupplement> dataSupplementMutableLiveData = new MutableLiveData<>();

    public PostViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public void getListSupplement() {
        progress.setValue(View.VISIBLE);
        repository.getListSupplement(new Consumer<DataSupplement>() {
            @Override
            public void accept(DataSupplement dataSupplement) {
                dataSupplementMutableLiveData.postValue(dataSupplement);
                progress.postValue(View.GONE);
            }
        });
    }

    public MutableLiveData<Integer> getProgress() {
        return progress;
    }

    public MutableLiveData<DataSupplement> getDataSupplementMutableLiveData() {
        return dataSupplementMutableLiveData;
    }
}
