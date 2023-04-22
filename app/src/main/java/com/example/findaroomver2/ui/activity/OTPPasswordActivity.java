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

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.findaroomver2.R;
import com.example.findaroomver2.constant.AppConstant;
import com.example.findaroomver2.databinding.ActivityOtppasswordBinding;
import com.example.findaroomver2.request.changepass.Email;
import com.example.findaroomver2.request.changepass.Verify;
import com.example.findaroomver2.response.TextResponse;
import com.example.findaroomver2.ui.customview.toast.CustomToast;
import com.example.findaroomver2.viewmodel.OTPPassViewModel;

public class OTPPasswordActivity extends AppCompatActivity {

    private ActivityOtppasswordBinding binding;

    private OTPPassViewModel viewModel;
    private String mail;
    private ImageView close;
    private TextView btnCancel;
    private Button logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtppasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(OTPPassViewModel.class);

        mail = getIntent().getStringExtra(AppConstant.EMAIL_USER);

        binding.textAlien.setText("Nhập mã mà chúng tôi đã gửi qua tin nhắn email " + mail);
        binding.sendAgain.setPaintFlags(binding.sendAgain.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        binding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialogLogOut = new Dialog(OTPPasswordActivity.this);
                dialogLogOut.setContentView(R.layout.dia_log_comfirm_logout_ver2);
                Window window2 = dialogLogOut.getWindow();
                window2.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                if (dialogLogOut != null && dialogLogOut.getWindow() != null) {
                    dialogLogOut.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
                close = (ImageView) dialogLogOut.findViewById(R.id.close);
                btnCancel = (TextView) dialogLogOut.findViewById(R.id.btnCancel);
                btnCancel.setPaintFlags(btnCancel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                logOut = (Button) dialogLogOut.findViewById(R.id.login);
                logOut.setText("Thoát");

                close.setOnClickListener(v1 -> {
                    dialogLogOut.dismiss();
                });
                btnCancel.setOnClickListener(v1 -> {
                    dialogLogOut.dismiss();
                });
                logOut.setOnClickListener(v2 -> {
                    onBackPressed();
                });
                dialogLogOut.show();
            }
        });

        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.otp.getText().toString().isEmpty()) {
                    CustomToast.ct(OTPPasswordActivity.this, "Bạn chưa nhập OTP", CustomToast.LENGTH_SHORT, CustomToast.INFO, true).show();
                } else if (binding.otp.getText().toString().length() > 6 || binding.otp.getText().toString().length() < 6) {
                    CustomToast.ct(OTPPasswordActivity.this, "Mã OTP gồm 6 kí tự", CustomToast.LENGTH_SHORT, CustomToast.INFO, true).show();
                } else {
                    viewModel.checkOTPPass(new Verify(mail, binding.otp.getText().toString()));
                }
            }
        });

        binding.sendAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.sendMailForgotPass(new Email(mail));
            }
        });

        viewModel.getProgress().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });

        viewModel.getTextResponseMutableLiveData().observe(this, new Observer<TextResponse>() {
            @Override
            public void onChanged(TextResponse textResponse) {
                if (textResponse.isStatus()) {
                    Intent intent = new Intent(OTPPasswordActivity.this, NewPasswordActivity.class);
                    intent.putExtra(AppConstant.EMAIL_USER, mail);
                    startActivity(intent);
                } else {
                    CustomToast.ct(OTPPasswordActivity.this, textResponse.getMessage(), CustomToast.LENGTH_SHORT, CustomToast.INFO, false).show();
                }
            }
        });

        viewModel.getTextResponseSendMailMutableLiveData().observe(this, new Observer<TextResponse>() {
            @Override
            public void onChanged(TextResponse textResponse) {
                if (textResponse.isStatus()) {
                    CustomToast.ct(OTPPasswordActivity.this, textResponse.getMessage(), CustomToast.LENGTH_SHORT, CustomToast.INFO, false).show();
                } else {
                    CustomToast.ct(OTPPasswordActivity.this, textResponse.getMessage(), CustomToast.LENGTH_SHORT, CustomToast.INFO, false).show();
                }
            }
        });
    }
}