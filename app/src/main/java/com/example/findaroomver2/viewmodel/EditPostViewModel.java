package com.example.findaroomver2.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.findaroomver2.model.Post;
import com.example.findaroomver2.repository.Repository;
import com.example.findaroomver2.response.post.PostResponse;
import com.example.findaroomver2.response.supplement.DataSupplement;

import java.util.function.Consumer;

public class EditPostViewModel extends AndroidViewModel {

    private Repository repository;

    private MutableLiveData<Integer> progress = new MutableLiveData<>();// cho thằng loading
    private MutableLiveData<PostResponse> postResponseMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<PostResponse> postEdit = new MutableLiveData<>();
    private MutableLiveData<DataSupplement> dataSupplementMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<DataSupplement> getDataSupplementMutableLiveData() {
        return dataSupplementMutableLiveData;
    }

    public EditPostViewModel(@NonNull Application application) {
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

    public void editPost(Post post , String id){
        progress.setValue(View.VISIBLE);
        repository.editPost(post ,id, new Consumer<PostResponse>() {
            @Override
            public void accept(PostResponse postResponse) {
                postEdit.postValue(postResponse);
                progress.postValue(View.GONE);
            }
        });
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

    public MutableLiveData<PostResponse> getPostResponseMutableLiveData() {
        return postResponseMutableLiveData;
    }

    public MutableLiveData<PostResponse> getPostEdit() {
        return postEdit;
    }
}
