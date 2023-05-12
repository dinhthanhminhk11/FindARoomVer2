package com.example.findaroomver2.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.findaroomver2.MainActivity;
import com.example.findaroomver2.R;
import com.example.findaroomver2.constant.AppConstant;
import com.example.findaroomver2.databinding.ActivityRegisterBinding;
import com.example.findaroomver2.model.UserClient;
import com.example.findaroomver2.request.login.UserLoginRequest;
import com.example.findaroomver2.request.register.UserRegisterRequest;
import com.example.findaroomver2.response.UserResponseLogin;
import com.example.findaroomver2.sharedpreferences.MySharedPreferences;
import com.example.findaroomver2.ui.customview.toast.CustomToast;
import com.example.findaroomver2.viewmodel.RegisterViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class RegisterActivity extends AppCompatActivity {
    private ImageView close;
    private Button login;
    private TextView btnCancel;
    private Button loginDialog;
    private RegisterViewModel registerViewModel;
    private ActivityRegisterBinding binding;
    private int role = 0;
    private String tokenDevice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        String token = task.getResult();
                        tokenDevice = token;
                    }
                });

        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        binding.tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        SpannableString content = new SpannableString("Chính sách của chúng tôi");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        binding.policy.setText(content);

        binding.policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, PolicyActivity.class));
            }
        });

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.sodienthoai.getText().toString().length() == 0) {
                    CustomToast.ct(RegisterActivity.this, "Bạn chưa nhập số điện thoại", CustomToast.LENGTH_SHORT, CustomToast.INFO, true).show();
                } else if (!isPhoneNumber(binding.sodienthoai.getText().toString())) {
                    CustomToast.ct(RegisterActivity.this, "Định dạng số điện thoại bị sai", CustomToast.LENGTH_SHORT, CustomToast.INFO, true).show();
                } else if (binding.diachiemai.getText().toString().length() == 0) {
                    CustomToast.ct(RegisterActivity.this, "Bạn chưa nhập email", CustomToast.LENGTH_SHORT, CustomToast.INFO, true).show();
                } else if (!isGmail(binding.diachiemai.getText().toString())) {
                    CustomToast.ct(RegisterActivity.this, "Định dạng email bị sai", CustomToast.LENGTH_SHORT, CustomToast.INFO, true).show();
                } else if (binding.username.getText().toString().length() == 0) {
                    CustomToast.ct(RegisterActivity.this, "Bạn chưa nhập họ tên", CustomToast.LENGTH_SHORT, CustomToast.INFO, true).show();
                } else if (binding.password.getText().toString().length() == 0) {
                    CustomToast.ct(RegisterActivity.this, "Bạn chưa nhập mật khẩu", CustomToast.LENGTH_SHORT, CustomToast.INFO, true).show();
                } else if (binding.password02.getText().toString().length() == 0) {
                    CustomToast.ct(RegisterActivity.this, "Bạn chưa nhập lại nhập mật khẩu", CustomToast.LENGTH_SHORT, CustomToast.INFO, true).show();
                } else if (!binding.password02.getText().toString().equals(binding.password.getText().toString())) {
                    CustomToast.ct(RegisterActivity.this, "Mật khẩu không trùng nhau", CustomToast.LENGTH_SHORT, CustomToast.INFO, true).show();
                } else {
                    registerViewModel.register(new UserRegisterRequest(binding.sodienthoai.getText().toString(), binding.diachiemai.getText().toString(), binding.password02.getText().toString(), binding.username.getText().toString(), role, tokenDevice));
                }
            }
        });

        binding.checkboxFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.checkboxFind.isChecked()) {
                    binding.checkboxRent.setChecked(false);
                    role = 0;
                } else {
                    binding.checkboxFind.setChecked(true);
                }
            }
        });
        binding.checkboxRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.checkboxRent.isChecked()) {
                    binding.checkboxFind.setChecked(false);
                    role = 2;
                } else {
                    binding.checkboxRent.setChecked(true);
                }
            }
        });

        registerViewModel.getProgress().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });

        registerViewModel.getUserResponseLoginMutableLiveData().observe(this, new Observer<UserResponseLogin>() {
            @Override
            public void onChanged(UserResponseLogin userResponseLogin) {
                if (userResponseLogin.getMessage().isStatus()) {
                    confirmLogin(binding.diachiemai.getText().toString(), binding.password02.getText().toString());
                } else {
                    Toast.makeText(RegisterActivity.this, "Đăng kí thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerViewModel.getUserResponseLoginAgainMutableLiveData().observe(this, new Observer<UserResponseLogin>() {
            @Override
            public void onChanged(UserResponseLogin userResponseLogin) {
                if (userResponseLogin.getMessage().isStatus()) {

                    MySharedPreferences.getInstance(RegisterActivity.this).putString(AppConstant.USER_TOKEN, userResponseLogin.getData().getAccessToken());
                    UserClient userClient = UserClient.getInstance();
                    userClient.setEmail(userResponseLogin.getData().getEmail());
                    userClient.setPhone(userResponseLogin.getData().getPhone());
                    userClient.setFullName(userResponseLogin.getData().getFullName());
                    userClient.setId(userResponseLogin.getData().getId());
                    userClient.setRole(userResponseLogin.getData().getRole());
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(RegisterActivity.this, userResponseLogin.getMessage().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void confirmLogin(String username, String password) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_comfirm_login);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        close = (ImageView) dialog.findViewById(R.id.close);
        btnCancel = (TextView) dialog.findViewById(R.id.btnCancel);
        btnCancel.setPaintFlags(btnCancel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        loginDialog = (Button) dialog.findViewById(R.id.login);

        loginDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerViewModel.login(new UserLoginRequest(username, password));
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });

        dialog.show();
    }

    private boolean isPhoneNumber(String input) {
        String phoneRegex = "^(03|05|07|08|09)+([0-9]{8})\\b";
        return input.matches(phoneRegex);
    }

    public static boolean isGmail(String input) {
        String gmailRegex = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$";
        return input.matches(gmailRegex);
    }
}