package com.example.findaroomver2.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.findaroomver2.R;
import com.example.findaroomver2.constant.AppConstant;
import com.example.findaroomver2.databinding.ActivityChatMessageBinding;
import com.example.findaroomver2.model.ContentChat;
import com.example.findaroomver2.model.MessageChat;
import com.example.findaroomver2.model.MessageSocket;
import com.example.findaroomver2.model.Text;
import com.example.findaroomver2.model.UserClient;
import com.example.findaroomver2.ui.adapter.ChatAdapter;
import com.example.findaroomver2.viewmodel.ChatViewModel;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ChatMessageActivity extends AppCompatActivity {

    private ActivityChatMessageBinding binding;
    private Socket mSocket;
    private String nameUser = "";
    private String idUser = "";
    private String imageUser = "";
    private List<ContentChat> listChat = new ArrayList<>();
    private ChatAdapter chatAdapter;
    private ChatViewModel chatViewModel;

    {
        try {
            mSocket = IO.socket(AppConstant.BASE);
        } catch (URISyntaxException e) {
            e.getMessage();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatMessageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initToolbar();
        initView();
        intiData();
    }

    @Override
    protected void onPause() {
        mSocket.disconnect();
        super.onPause();
    }

    private void intiData() {
        idUser = getIntent().getStringExtra(AppConstant.ID_USER);
        nameUser = getIntent().getStringExtra(AppConstant.NAME_USER);
        imageUser = getIntent().getStringExtra(AppConstant.IMAGE_USER);

        RequestOptions optionsUser = new RequestOptions().centerCrop().placeholder(R.drawable.noavatar).error(R.drawable.noavatar);
        Glide.with(this).load(imageUser).apply(optionsUser).into(binding.imgBossChat);

        binding.tvNameBossChat.setText(nameUser);

        chatViewModel.getContentChatLiveData(UserClient.getInstance().getId(), idUser).observe(this, it -> {
            for (int i = 0; i <= it.size() - 1; i++) {
                listChat.add(it.get(i));
            }
            chatAdapter = new ChatAdapter(listChat);
            binding.rcvChatMessage.setAdapter(chatAdapter);
            binding.rcvChatMessage.setLayoutManager(new LinearLayoutManager(ChatMessageActivity.this));
            binding.rcvChatMessage.smoothScrollToPosition(listChat.size());
        });

        mSocket.on("new message", onNewMessage);
        mSocket.emit("join", UserClient.getInstance().getId());
        mSocket.on("join", checkOnline);
        binding.btnSent.setOnClickListener(v -> {
            sendChat();
        });

        binding.btnSent.setAlpha(0.4f);
        binding.btnSent.setEnabled(false);

        binding.edContentChat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().length() > 0) {
                    binding.btnSent.setEnabled(true);
                    binding.btnSent.setAlpha(1);
                } else {
                    binding.btnSent.setAlpha(0.4f);
                    binding.btnSent.setEnabled(false);
                }
            }
        });

    }

    private void initView() {
        chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);
    }

    private void initToolbar() {
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mSocket.connect();
    }

    private final Emitter.Listener checkOnline = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(() -> {
                JSONObject data = (JSONObject) args[0];
                String mess = data.optString("id");
                if (mess.equals(idUser)) {
                    binding.tvCheckOnline.setText("Online");
                    binding.imgOnlineChat.setImageDrawable(getResources().getDrawable(R.drawable.ic_online));
                } else {
                    binding.tvCheckOnline.setText("Offline");
                    binding.imgOnlineChat.setImageDrawable(getResources().getDrawable(R.drawable.ic_offline));
                }
            });
        }
    };

    public void sendChat() {
        if (binding.edContentChat.getText().toString().isEmpty() || binding.edContentChat.getText().toString().trim().equals("")) {
            binding.edContentChat.setText("");
            return;
        }
        DateFormat df = new SimpleDateFormat("HH:mm");
        String date = df.format(Calendar.getInstance().getTime());
        Gson gson = new Gson();
        MessageSocket ms = new MessageSocket(UserClient.getInstance().getId(), idUser, UserClient.getInstance().getId(), binding.edContentChat.getText().toString(), date);
        try {
            JSONObject jObject = new JSONObject(gson.toJson(ms));
            mSocket.emit("message", jObject);
            MessageChat message = new MessageChat(idUser, UserClient.getInstance().getId(), binding.edContentChat.getText().toString());
            chatViewModel.insertChat(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        listChat.add(new ContentChat(new Text(binding.edContentChat.getText().toString()), UserClient.getInstance().getId(), idUser));
        chatAdapter.notifyDataSetChanged();
        binding.rcvChatMessage.smoothScrollToPosition(listChat.size() - 1);
        binding.edContentChat.setText("");
    }


    private final Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(() -> {
                JSONObject data = (JSONObject) args[0];
                String mess = data.optString("message");
                listChat.add(new ContentChat(new Text(mess), idUser, UserClient.getInstance().getId()));
                chatAdapter.notifyDataSetChanged();
                binding.rcvChatMessage.smoothScrollToPosition(listChat.size() - 1);
            });
        }
    };
}