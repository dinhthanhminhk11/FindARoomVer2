package com.example.findaroomver2.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.findaroomver2.MainActivity;
import com.example.findaroomver2.R;
import com.example.findaroomver2.constant.AppConstant;
import com.example.findaroomver2.databinding.ActivityNewPasswordBinding;
import com.example.findaroomver2.model.UserClient;
import com.example.findaroomver2.request.login.UserLoginRequest;
import com.example.findaroomver2.response.TextResponse;
import com.example.findaroomver2.response.UserResponseLogin;
import com.example.findaroomver2.sharedpreferences.MySharedPreferences;
import com.example.findaroomver2.ui.customview.toast.CustomToast;
import com.example.findaroomver2.viewmodel.NewPassViewModel;

public class NewPasswordActivity extends AppCompatActivity {
    private String mail;
    private ActivityNewPasswordBinding binding;
    private NewPassViewModel newPassViewModel;
    private ImageView close;
    private TextView btnCancel;
    private Button logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        newPassViewModel = new ViewModelProvider(this).get(NewPassViewModel.class);

        mail = getIntent().getStringExtra(AppConstant.EMAIL_USER);

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.password.getText().toString().length() == 0) {
                    CustomToast.ct(NewPasswordActivity.this, "Bạn chưa nhập mật khẩu", CustomToast.LENGTH_SHORT, CustomToast.INFO, true).show();
                } else if (binding.passwordAgain.getText().toString().length() == 0) {
                    CustomToast.ct(NewPasswordActivity.this, "Bạn chưa nhập lại nhập mật khẩu", CustomToast.LENGTH_SHORT, CustomToast.INFO, true).show();
                } else if (!binding.passwordAgain.getText().toString().equals(binding.password.getText().toString())) {
                    CustomToast.ct(NewPasswordActivity.this, "Mật khẩu không trùng nhau", CustomToast.LENGTH_SHORT, CustomToast.INFO, true).show();
                } else {
                    newPassViewModel.newPassWord(new UserLoginRequest(mail, binding.passwordAgain.getText().toString()));
                }
            }
        });

        binding.tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NewPasswordActivity.this, RegisterActivity.class));
            }
        });

        newPassViewModel.getProgress().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });

        newPassViewModel.getTextResponseMutableLiveData().observe(this, new Observer<TextResponse>() {
            @Override
            public void onChanged(TextResponse textResponse) {
                if (textResponse.isStatus()) {
                    final Dialog dialogLogOut = new Dialog(NewPasswordActivity.this);
                    dialogLogOut.setContentView(R.layout.dia_log_comfirm_logout_ver2);
                    Window window2 = dialogLogOut.getWindow();
                    dialogLogOut.setCancelable(false);
                    window2.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    if (dialogLogOut != null && dialogLogOut.getWindow() != null) {
                        dialogLogOut.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    }
                    close = (ImageView) dialogLogOut.findViewById(R.id.close);
                    btnCancel = (TextView) dialogLogOut.findViewById(R.id.btnCancel);
                    btnCancel.setPaintFlags(btnCancel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                    logOut = (Button) dialogLogOut.findViewById(R.id.login);
                    logOut.setText("Đăng nhập");

                    TextView text = dialogLogOut.findViewById(R.id.text);
                    text.setText("Đổi mật khẩu thành công bạn có muốn đăng nhập");

                    close.setOnClickListener(v1 -> {
                        dialogLogOut.dismiss();
                        Intent intent = new Intent(NewPasswordActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    });
                    btnCancel.setOnClickListener(v1 -> {
                        dialogLogOut.dismiss();
                        Intent intent = new Intent(NewPasswordActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    });
                    logOut.setOnClickListener(v2 -> {
                        newPassViewModel.login(new UserLoginRequest(mail, binding.passwordAgain.getText().toString()));
                        dialogLogOut.dismiss();
                    });
                    dialogLogOut.show();
                } else {
                    Toast.makeText(NewPasswordActivity.this, textResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        newPassViewModel.getUserResponseLoginMutableLiveData().observe(this, new Observer<UserResponseLogin>() {
            @Override
            public void onChanged(UserResponseLogin userResponseLogin) {
                if (userResponseLogin.getMessage().isStatus()) {

                    MySharedPreferences.getInstance(NewPasswordActivity.this).putString(AppConstant.USER_TOKEN, userResponseLogin.getData().getAccessToken());
                    UserClient userClient = UserClient.getInstance();
                    userClient.setEmail(userResponseLogin.getData().getEmail());
                    userClient.setPhone(userResponseLogin.getData().getPhone());
                    userClient.setFullName(userResponseLogin.getData().getFullName());
                    userClient.setId(userResponseLogin.getData().getId());

                    startActivity(new Intent(NewPasswordActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                } else {
                    Toast.makeText(NewPasswordActivity.this, userResponseLogin.getMessage().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(false);
    }
}