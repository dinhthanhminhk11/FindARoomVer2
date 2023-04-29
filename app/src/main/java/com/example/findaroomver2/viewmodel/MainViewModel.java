package com.example.findaroomver2.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.findaroomver2.model.Post;
import com.example.findaroomver2.repository.Repository;
import com.example.findaroomver2.request.login.UserLoginRequest;
import com.example.findaroomver2.response.UserResponseLogin;
import com.example.findaroomver2.response.post.PostHome;
import com.example.findaroomver2.response.post.PostResponse;
import com.example.findaroomver2.response.supplement.DataSupplement;

import java.util.List;
import java.util.function.Consumer;

public class MainViewModel extends AndroidViewModel {

    private Repository repository;
    private MutableLiveData<Integer> progress = new MutableLiveData<>();
    private MutableLiveData<DataSupplement> dataSupplementMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<PostResponse> postResponseMutableLiveData = new MutableLiveData<>();
   private MutableLiveData<UserResponseLogin> userResponseLoginMutableLiveData = new MutableLiveData<>();
   private MutableLiveData<PostHome> listMutableLiveDataPost = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public void loginToken(String token){
        repository.loginByToken(token, new Consumer<UserResponseLogin>() {
            @Override
            public void accept(UserResponseLogin userResponseLogin) {
                if (userResponseLogin instanceof UserResponseLogin) {
                    userResponseLoginMutableLiveData.postValue(userResponseLogin);
                }
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

    public void createPost(Post post) {
        progress.setValue(View.VISIBLE);
        repository.createPost(post, new Consumer<PostResponse>() {
            @Override
            public void accept(PostResponse postResponse) {
                postResponseMutableLiveData.postValue(postResponse);
                progress.postValue(View.GONE);
            }
        });
    }

    public void getAllListPostHome(){
        progress.setValue(View.VISIBLE);
        repository.getListPost(new Consumer<PostHome>() {
            @Override
            public void accept(PostHome posts) {
                listMutableLiveDataPost.postValue(posts);
                progress.postValue(View.GONE);
            }
        });
    }

    public MutableLiveData<PostResponse> getPostResponseMutableLiveData() {
        return postResponseMutableLiveData;
    }

    public MutableLiveData<Integer> getProgress() {
        return progress;
    }

    public MutableLiveData<DataSupplement> getDataSupplementMutableLiveData() {
        return dataSupplementMutableLiveData;
    }

    public MutableLiveData<UserResponseLogin> getUserResponseLoginMutableLiveData() {
        return userResponseLoginMutableLiveData;
    }

    public MutableLiveData<PostHome> getListMutableLiveDataPost() {
        return listMutableLiveDataPost;
    }
}
