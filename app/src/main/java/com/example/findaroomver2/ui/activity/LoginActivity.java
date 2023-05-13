package com.example.findaroomver2.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.findaroomver2.R;
import com.example.findaroomver2.constant.AppConstant;
import com.example.findaroomver2.constant.NotificationCenter;
import com.example.findaroomver2.databinding.ActivityLoginBinding;
import com.example.findaroomver2.event.KeyEvent;
import com.example.findaroomver2.model.UserClient;
import com.example.findaroomver2.request.login.UserLoginRequest;
import com.example.findaroomver2.request.login.UserRequestTokenDevice;
import com.example.findaroomver2.response.UserResponseLogin;
import com.example.findaroomver2.sharedpreferences.MySharedPreferences;
import com.example.findaroomver2.ui.customview.toast.CustomToast;
import com.example.findaroomver2.viewmodel.LoginViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.greenrobot.eventbus.EventBus;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;
    private String tokenDevice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // lấy token máy để bắn thông báo
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    return;
                }
                String token = task.getResult();
                tokenDevice = token;
                Log.d("tokenFirebase", token);
            }
        });

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding.btnLogin.setOnClickListener(v -> {
            if (binding.username.getText().toString().isEmpty()) {
                CustomToast.ct(this, "Bạn chưa nhập email hoặc số điện thoại", CustomToast.LENGTH_SHORT, CustomToast.INFO, true).show();
            } else if (binding.password.getText().toString().isEmpty()) {
                CustomToast.ct(this, "Bạn chưa nhập mật khẩu", CustomToast.LENGTH_SHORT, CustomToast.INFO, true).show();

            } else if (!isPhoneNumberOrGmail(binding.username.getText().toString())) {
                CustomToast.ct(this, "Nhập sai định dạng gmail hoặc số điện thoại", CustomToast.LENGTH_SHORT, CustomToast.INFO, true).show();
            } else {
                loginViewModel.login(new UserLoginRequest(binding.username.getText().toString(), binding.password.getText().toString()));
            }
        });

        loginViewModel.getProgress().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });

        loginViewModel.getUserResponseLoginMutableLiveData().observe(this, new Observer<UserResponseLogin>() {
            @Override
            public void onChanged(UserResponseLogin userResponseLogin) {
                if (userResponseLogin.getMessage().isStatus()) {
                    if (userResponseLogin.getData().isVerified()) {
                        if (userResponseLogin.getData().getRole() == 2 || userResponseLogin.getData().getRole() == 0) {
                            if (!userResponseLogin.getData().getTokenDevice().equals(tokenDevice)) {
                                loginViewModel.updateTokenDevice(new UserRequestTokenDevice(userResponseLogin.getData().getId(), tokenDevice));
                            }
                            MySharedPreferences.getInstance(LoginActivity.this).putString(AppConstant.USER_TOKEN, userResponseLogin.getData().getAccessToken());
                            UserClient userClient = UserClient.getInstance();
                            userClient.setEmail(userResponseLogin.getData().getEmail());
                            userClient.setPhone(userResponseLogin.getData().getPhone());
                            userClient.setFullName(userResponseLogin.getData().getFullName());
                            userClient.setId(userResponseLogin.getData().getId());
                            userClient.setRole(userResponseLogin.getData().getRole());
                            userClient.setImage(userResponseLogin.getData().getImage());
                            EventBus.getDefault().postSticky(new KeyEvent(NotificationCenter.checkLogin));
                            finish();
                        } else {
                            initdialogFailed("Hành động không được phép chỉ cho tài khoản chủ nhà hoặc người đi thuê, hãy đăng nhập tài khoản khác");
                        }
                    } else {
                        initdialogFailed(userResponseLogin.getData().getTextReport());
                    }
                } else {
                    CustomToast.ct(LoginActivity.this, userResponseLogin.getMessage().getMessage(), CustomToast.LENGTH_SHORT, CustomToast.INFO, false).show();
                }
            }
        });

        binding.tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    public static boolean isPhoneNumberOrGmail(String input) {
        String phoneRegex = "^(03|05|07|08|09)+([0-9]{8})\\b";
        String gmailRegex = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$";

        return input.matches(phoneRegex) || input.matches(gmailRegex);
    }

    private void initdialogFailed(String contentData) {
        final Dialog dialogFailed = new Dialog(this);
        dialogFailed.setCancelable(false);
        dialogFailed.setContentView(R.layout.dialog_fail);
        Window window2 = dialogFailed.getWindow();
        window2.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialogFailed != null && dialogFailed.getWindow() != null) {
            dialogFailed.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        ImageButton closeImgBtnFailed = (ImageButton) dialogFailed.findViewById(R.id.close_img_btn);
        Button failed = (Button) dialogFailed.findViewById(R.id.failed);

        TextView title = (TextView) dialogFailed.findViewById(R.id.title);
        TextView content = (TextView) dialogFailed.findViewById(R.id.content);
        title.setText("Thông báo");
        content.setText(contentData);
        failed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFailed.dismiss();
                binding.password.setText("");
                binding.username.setText("");
            }
        });

        closeImgBtnFailed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFailed.dismiss();
            }
        });

        dialogFailed.show();
    }
}