package com.example.findaroomver2.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.findaroomver2.repository.Repository;
import com.example.findaroomver2.request.login.Data;
import com.example.findaroomver2.response.comment.CommentListResponse;
import com.example.findaroomver2.response.comment.CommentResponse;
import com.example.findaroomver2.response.post.PostResponse;

import java.util.function.Consumer;

public class DetailPostViewModel extends AndroidViewModel {
    private Repository repository;

    private MutableLiveData<Integer> progress = new MutableLiveData<>();// cho thằng loading
    private MutableLiveData<PostResponse> postResponseMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<Data> dataUser = new MutableLiveData<>();

    private MutableLiveData<CommentListResponse> commentListResponseMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<CommentResponse> commentResponseMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<Data> getDataUser() {
        return dataUser;
    }

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

    public void getListCommentByIdPost(String idPost) {
        progress.setValue(View.VISIBLE);
        repository.getListCommentParent(idPost, new Consumer<CommentListResponse>() {
            @Override
            public void accept(CommentListResponse commentListResponse) {
                commentListResponseMutableLiveData.postValue(commentListResponse);
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

    public MutableLiveData<Integer> getProgress() {
        return progress;
    }

    public MutableLiveData<PostResponse> getPostResponseMutableLiveData() {
        return postResponseMutableLiveData;
    }

    public MutableLiveData<CommentListResponse> getCommentListResponseMutableLiveData() {
        return commentListResponseMutableLiveData;
    }

    public MutableLiveData<CommentResponse> getCommentResponseMutableLiveData() {
        return commentResponseMutableLiveData;
    }
}
