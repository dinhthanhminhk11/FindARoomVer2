package com.example.findaroomver2.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.findaroomver2.repository.Repository;
import com.example.findaroomver2.request.changepass.Email;
import com.example.findaroomver2.request.changepass.Verify;
import com.example.findaroomver2.response.TextResponse;

import java.util.function.Consumer;

public class OTPPassViewModel extends AndroidViewModel {
    private Repository repository;

    MutableLiveData<Integer> progress = new MutableLiveData<>();
    MutableLiveData<TextResponse> textResponseMutableLiveData = new MutableLiveData<>();
    MutableLiveData<TextResponse> textResponseSendMailMutableLiveData = new MutableLiveData<>();

    public OTPPassViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public void checkOTPPass(Verify verify) {
        progress.setValue(View.VISIBLE);
        repository.checkOTPPass(verify, new Consumer<TextResponse>() {
            @Override
            public void accept(TextResponse textResponse) {
                if (textResponse instanceof TextResponse) {
                    textResponseMutableLiveData.postValue(textResponse);
                    progress.postValue(View.GONE);
                }
            }
        });
    }

    public void sendMailForgotPass(Email email) {
        progress.setValue(View.VISIBLE);
        repository.sendMailForgotPass(email, new Consumer<TextResponse>() {
            @Override
            public void accept(TextResponse textResponse) {
                if (textResponse instanceof TextResponse) {
                    textResponseSendMailMutableLiveData.postValue(textResponse);
                    progress.postValue(View.GONE);
                }
            }
        });
    }

    public MutableLiveData<Integer> getProgress() {
        return progress;
    }

    public MutableLiveData<TextResponse> getTextResponseMutableLiveData() {
        return textResponseMutableLiveData;
    }

    public MutableLiveData<TextResponse> getTextResponseSendMailMutableLiveData() {
        return textResponseSendMailMutableLiveData;
    }
}
