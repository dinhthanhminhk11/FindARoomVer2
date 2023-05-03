package com.example.findaroomver2.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.findaroomver2.repository.Repository;
import com.example.findaroomver2.request.comment.Comment;
import com.example.findaroomver2.response.comment.CommentListResponse;
import com.example.findaroomver2.response.comment.CommentResponse;

import java.util.function.Consumer;

public class CommentViewModel extends AndroidViewModel {
    private Repository repository;

    private MutableLiveData<Integer> progress = new MutableLiveData<>();
    private MutableLiveData<CommentListResponse> commentListResponseMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<CommentResponse> commentResponseMutableLiveData = new MutableLiveData<>();

    public CommentViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
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


    public void addComment(Comment comment) {
        progress.setValue(View.VISIBLE);
        repository.addComment(comment, new Consumer<CommentResponse>() {
            @Override
            public void accept(CommentResponse commentResponse) {
                commentResponseMutableLiveData.postValue(commentResponse);
                progress.postValue(View.GONE);
            }
        });
    }


    public MutableLiveData<Integer> getProgress() {
        return progress;
    }

    public MutableLiveData<CommentListResponse> getCommentListResponseMutableLiveData() {
        return commentListResponseMutableLiveData;
    }

    public MutableLiveData<CommentResponse> getCommentResponseMutableLiveData() {
        return commentResponseMutableLiveData;
    }
}
