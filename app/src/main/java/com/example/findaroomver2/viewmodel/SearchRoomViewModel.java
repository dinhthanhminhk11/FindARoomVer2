package com.example.findaroomver2.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.findaroomver2.model.SearchModel;
import com.example.findaroomver2.repository.Repository;
import com.example.findaroomver2.response.post.PostHome;

import java.util.List;
import java.util.function.Consumer;

public class SearchRoomViewModel extends AndroidViewModel {
    private Repository repository;

    private MutableLiveData<List<SearchModel>> searchModelMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    private MutableLiveData<PostHome> listMutableLiveDataPost = new MutableLiveData<>();

    public SearchRoomViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public void getListSearchLocationHotel(String id) {
        repository.getListSearchLocationPost(id, o -> {
            searchModelMutableLiveData.postValue((List<SearchModel>) o);
        });
    }

    public void getListPostSearchByTextCty(String textSearch) {
        mProgressMutableData.setValue(View.VISIBLE);
        repository.getListPostByLocationCty(textSearch, new Consumer<PostHome>() {
            @Override
            public void accept(PostHome posts) {
                listMutableLiveDataPost.postValue(posts);
                mProgressMutableData.postValue(View.GONE);
            }
        });
    }

    public void getListSearchLocationCtyAndPrice(String textSearch, String startPrice, String endPrice) {
        mProgressMutableData.setValue(View.VISIBLE);
        repository.getListSearchLocationCtyAndPrice(textSearch, startPrice, endPrice, new Consumer<PostHome>() {
            @Override
            public void accept(PostHome posts) {
                listMutableLiveDataPost.postValue(posts);
                mProgressMutableData.postValue(View.GONE);
            }
        });
    }

    public void getListSearchPrice( String startPrice, String endPrice) {
        mProgressMutableData.setValue(View.VISIBLE);
        repository.getListSearchPrice( startPrice, endPrice, new Consumer<PostHome>() {
            @Override
            public void accept(PostHome posts) {
                listMutableLiveDataPost.postValue(posts);
                mProgressMutableData.postValue(View.GONE);
            }
        });
    }

    public MutableLiveData<List<SearchModel>> getSearchModelMutableLiveData() {
        return searchModelMutableLiveData;
    }

    public MutableLiveData<Integer> getmProgressMutableData() {
        return mProgressMutableData;
    }

    public MutableLiveData<PostHome> getListMutableLiveDataPost() {
        return listMutableLiveDataPost;
    }
}
