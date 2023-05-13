package com.example.findaroomver2.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.airbnb.lottie.animation.content.Content;
import com.example.findaroomver2.model.ContentChat;
import com.example.findaroomver2.model.Post;
import com.example.findaroomver2.repository.Repository;
import com.example.findaroomver2.request.login.Data;
import com.example.findaroomver2.request.login.UserRequestTokenDevice;
import com.example.findaroomver2.response.ListNotificationResponse;
import com.example.findaroomver2.response.TextResponse;
import com.example.findaroomver2.response.UserResponseLogin;
import com.example.findaroomver2.response.post.PostHome;
import com.example.findaroomver2.response.post.PostResponse;
import com.example.findaroomver2.response.supplement.DataSupplement;

import java.util.List;
import java.util.function.Consumer;

public class MainViewModel extends AndroidViewModel {

    private Repository repository;
    private MutableLiveData<Integer> progress = new MutableLiveData<>();
    private MutableLiveData<Integer> countPostUser = new MutableLiveData<>();
    private MutableLiveData<Integer> priceCash = new MutableLiveData<>();
    private MutableLiveData<DataSupplement> dataSupplementMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<PostResponse> postResponseMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<UserResponseLogin> userResponseLoginMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<PostHome> listMutableLiveDataPost = new MutableLiveData<>();
    private MutableLiveData<PostHome> liveDataHomeAds = new MutableLiveData<>();
    private MutableLiveData<ListNotificationResponse> listNotificationResponseMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<TextResponse> textResponseMutableLiveData = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public void loginToken(String token) {
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

    public void getAllListPostHome() {
        progress.setValue(View.VISIBLE);
        repository.getListPost(new Consumer<PostHome>() {
            @Override
            public void accept(PostHome posts) {
                listMutableLiveDataPost.postValue(posts);
                progress.postValue(View.GONE);
            }
        });
    }

    public void getListHomeAds() {
        progress.setValue(View.VISIBLE);
        repository.getListPostHomeAds(new Consumer<PostHome>() {
            @Override
            public void accept(PostHome postHome) {
                liveDataHomeAds.postValue(postHome);
                progress.postValue(View.GONE);
            }
        });
    }

    public void getCountPost(String id) {
        progress.setValue(View.VISIBLE);
        repository.getCountPost(id, new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                countPostUser.postValue(integer);
                progress.postValue(View.GONE);
            }
        });
    }

    public void getPriceCashFlow(String id) {
        progress.setValue(View.VISIBLE);
        repository.getPriceCashPay(id, new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                priceCash.postValue(integer);
                progress.postValue(View.GONE);
            }
        });
    }

    public void getListNotificationByIdUser(String id) {
        progress.postValue(View.VISIBLE);
        repository.getListNotificationByIdUser(id, new Consumer<ListNotificationResponse>() {
            @Override
            public void accept(ListNotificationResponse listNotificationResponse) {
                listNotificationResponseMutableLiveData.postValue(listNotificationResponse);
                progress.postValue(View.GONE);
            }
        });
    }

    public void updateNotiSeen(String id) {
        repository.updateNotiSeen(id, new Consumer<TextResponse>() {
            @Override
            public void accept(TextResponse textResponse) {
                textResponseMutableLiveData.postValue(textResponse);
            }
        });
    }

    public void updateTokenDevice(UserRequestTokenDevice userRequestTokenDevice) {
        repository.updateTokenDevice(userRequestTokenDevice, new Consumer<TextResponse>() {
            @Override
            public void accept(TextResponse textResponse) {
            }
        });
    }

    public LiveData<List<ContentChat>> getMsgId(String send) {
        return repository.getMsgId(send);
    }

    public LiveData<List<ContentChat>> getMsgIdSendTo(String sendTo) {
        return repository.getMsgIdSendTo(sendTo);
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

    public LiveData<List<Data>> getHost(String id) {
        return repository.getHost(id);
    }

    public MutableLiveData<Integer> getCountPostUser() {
        return countPostUser;
    }

    public MutableLiveData<Integer> getPriceCash() {
        return priceCash;
    }

    public MutableLiveData<PostHome> getLiveDataHomeAds() {
        return liveDataHomeAds;
    }

    public MutableLiveData<ListNotificationResponse> getListNotificationResponseMutableLiveData() {
        return listNotificationResponseMutableLiveData;
    }

    public MutableLiveData<TextResponse> getTextResponseMutableLiveData() {
        return textResponseMutableLiveData;
    }
}
