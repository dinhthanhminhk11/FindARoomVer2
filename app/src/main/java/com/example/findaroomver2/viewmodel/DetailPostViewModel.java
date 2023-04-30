package com.example.findaroomver2.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.findaroomver2.repository.Repository;
import com.example.findaroomver2.response.UserResponseLogin;
import com.example.findaroomver2.response.post.PostResponse;

import java.util.function.Consumer;

public class DetailPostViewModel extends AndroidViewModel {
    private Repository repository;

    private MutableLiveData<Integer> progress = new MutableLiveData<>();// cho thằng loading
    private MutableLiveData<PostResponse> postResponseMutableLiveData = new MutableLiveData<>();

    public DetailPostViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public void getPostById(String id) {
        progress.setValue(View.VISIBLE);
        repository.getPostById(id, new Consumer<PostResponse>() {
            @Override
            public void accept(PostResponse postResponse) {
                postResponseMutableLiveData.postValue(postResponse);
                progress.postValue(View.GONE);
            }
        });
    }

    public MutableLiveData<Integer> getProgress() {
        return progress;
    }

    public MutableLiveData<PostResponse> getPostResponseMutableLiveData() {
        return postResponseMutableLiveData;
    }
}
