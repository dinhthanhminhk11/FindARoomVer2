package com.example.findaroomver2.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.findaroomver2.repository.Repository;
import com.example.findaroomver2.request.login.Data;
import com.example.findaroomver2.response.post.PostHome;

import java.util.function.Consumer;

public class ProfileViewModel extends AndroidViewModel {
    private Repository repository;
    private MutableLiveData<PostHome> listMutableLiveDataPost = new MutableLiveData<>();
    private MutableLiveData<Integer> progress = new MutableLiveData<>();
    private MutableLiveData<Data> dataUser = new MutableLiveData<>();

    public ProfileViewModel(@NonNull Application application) {
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

    public void getUserById(String id) {
        progress.setValue(View.VISIBLE);
        repository.getUserById(id, new Consumer<Data>() {
            @Override
            public void accept(Data data) {
                dataUser.postValue(data);
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

    public MutableLiveData<Data> getDataUser() {
        return dataUser;
    }
}
