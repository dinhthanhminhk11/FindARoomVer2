package com.example.findaroomver2.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.findaroomver2.repository.Repository;
import com.example.findaroomver2.response.bookmark.ListBookmarkResponse;

import java.util.function.Consumer;

public class BookmarkViewModel extends AndroidViewModel {
    private Repository repository;
    private MutableLiveData<ListBookmarkResponse> bookmarkResponseMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();

    public BookmarkViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public void getListBookmarkByIdUser(String id) {
        mProgressMutableData.postValue(View.VISIBLE);
        repository.getListBookmarkByIdUser(id, new Consumer<ListBookmarkResponse>() {
            @Override
            public void accept(ListBookmarkResponse listBookmarkResponse) {
                mProgressMutableData.postValue(View.GONE);
                bookmarkResponseMutableLiveData.postValue(listBookmarkResponse);
            }
        });
    }

    public MutableLiveData<ListBookmarkResponse> getBookmarkResponseMutableLiveData() {
        return bookmarkResponseMutableLiveData;
    }

    public MutableLiveData<Integer> getmProgressMutableData() {
        return mProgressMutableData;
    }
}
