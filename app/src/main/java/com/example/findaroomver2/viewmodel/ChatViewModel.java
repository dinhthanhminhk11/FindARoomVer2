package com.example.findaroomver2.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.airbnb.lottie.animation.content.Content;
import com.example.findaroomver2.model.ContentChat;
import com.example.findaroomver2.model.MessageChat;
import com.example.findaroomver2.repository.Repository;
import com.example.findaroomver2.request.login.Data;

import java.util.List;

public class ChatViewModel extends AndroidViewModel {
    private Repository chatRepository;

    public ChatViewModel(@NonNull Application application) {
        super(application);
        chatRepository = new Repository();
    }

    public LiveData<List<ContentChat>> getContentChatLiveData(String sendId, String sendToId) {
        return chatRepository.getContentChat(sendId, sendToId);
    }

    public LiveData<List<ContentChat>> getMsgId(String send) {
        return chatRepository.getMsgId(send);
    }

    public LiveData<List<Data>> getHost(String id) {
        return chatRepository.getHost(id);
    }

    public void insertChat(MessageChat message) {
        chatRepository.insertMessage(message);
    }
}
