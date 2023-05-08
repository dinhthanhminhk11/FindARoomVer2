package com.example.findaroomver2.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.findaroomver2.repository.Repository;
import com.example.findaroomver2.response.post.PostHome;

import java.util.function.Consumer;

public class ManagerPostViewModel extends AndroidViewModel {
    private Repository repository;
    private MutableLiveData<PostHome> listMutableLiveDataPost = new MutableLiveData<>();
    private MutableLiveData<Integer> progress = new MutableLiveData<>();

    public ManagerPostViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public void getListPostManager(String id) {
        progress.setValue(View.VISIBLE);
        repository.getListPostByMyself(id, new Consumer<PostHome>() {
            @Override
            public void accept(PostHome postHome) {
                listMutableLiveDataPost.postValue(postHome);
                progress.postValue(View.GONE);
            }
        });
    }

    public MutableLiveData<PostHome> getListMutableLiveDataPost() {
        return listMutableLiveDataPost;
    }

    public MutableLiveData<Integer> getProgress() {
        return progress;
    }
}
