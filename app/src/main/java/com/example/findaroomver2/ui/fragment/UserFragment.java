package com.example.findaroomver2.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.findaroomver2.R;
import com.example.findaroomver2.constant.AppConstant;
import com.example.findaroomver2.databinding.FragmentUserBinding;
import com.example.findaroomver2.model.UserClient;
import com.example.findaroomver2.request.login.UserRequestTokenDevice;
import com.example.findaroomver2.sharedpreferences.MySharedPreferences;
import com.example.findaroomver2.ui.activity.BookmarkActivity;
import com.example.findaroomver2.ui.activity.ChangePasswordActivity;
import com.example.findaroomver2.ui.activity.ContactActivity;
import com.example.findaroomver2.ui.activity.EditProfileActivity;
import com.example.findaroomver2.ui.activity.LoginActivity;
import com.example.findaroomver2.ui.activity.ManagerPostActivity;
import com.example.findaroomver2.ui.activity.PayCashYourActivity;
import com.example.findaroomver2.ui.activity.UpdateAccountHostActivity;
import com.example.findaroomver2.viewmodel.MainViewModel;


public class UserFragment extends Fragment {
    private FragmentUserBinding binding;
    private String token = "";
    private ImageView close;
    private TextView text;
    private TextView btnCancel;
    private Button login;

    private MainViewModel viewModel;

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        checkLogin();
    }

    private void initData() {
        binding.nameUser.setText(UserClient.getInstance().getFullName());
        binding.email.setText(UserClient.getInstance().getEmail());
        RequestOptions optionsUser = new RequestOptions().centerCrop().placeholder(R.drawable.noavatar).error(R.drawable.noavatar);
        Glide.with(getActivity()).load(UserClient.getInstance().getImage()).apply(optionsUser).into(binding.imageUser);
        if (UserClient.getInstance().getRole() == 2) {
            binding.contentPost.setVisibility(View.VISIBLE);
            binding.layoutContentPayment.setVisibility(View.VISIBLE);
            binding.contentUpdateAccount.setVisibility(View.GONE);
        } else {
            binding.contentPost.setVisibility(View.GONE);
            binding.layoutContentPayment.setVisibility(View.GONE);
            binding.contentUpdateAccount.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {
        viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
        binding.layoutContentPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PayCashYourActivity.class));
            }
        });

        binding.editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), EditProfileActivity.class));
            }
        });

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        binding.contentLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        binding.contentUpdateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), UpdateAccountHostActivity.class));
            }
        });

        binding.contentChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ChangePasswordActivity.class));
            }
        });

        binding.contentBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), BookmarkActivity.class));
            }
        });

        binding.contentPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ManagerPostActivity.class));
            }
        });

        binding.contentBySeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ContactActivity.class));
            }
        });
    }

    private void showDialog() {
        final Dialog dialogLogOut = new Dialog(getActivity());
        dialogLogOut.setContentView(R.layout.dia_log_comfirm_logout_ver2);
        Window window2 = dialogLogOut.getWindow();
        window2.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialogLogOut != null && dialogLogOut.getWindow() != null) {
            dialogLogOut.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        close = (ImageView) dialogLogOut.findViewById(R.id.close);
        text = (TextView) dialogLogOut.findViewById(R.id.text);
        btnCancel = (TextView) dialogLogOut.findViewById(R.id.btnCancel);
        login = (Button) dialogLogOut.findViewById(R.id.login);

        btnCancel.setPaintFlags(btnCancel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        login.setText("Đăng xuất");
        text.setText("Bạn có chắc muốn đăng xuất");

        close.setOnClickListener(v1 -> {
            dialogLogOut.dismiss();
        });
        btnCancel.setOnClickListener(v1 -> {
            dialogLogOut.dismiss();
        });
        login.setOnClickListener(v2 -> {
            dialogLogOut.dismiss();
            resetDataUser();
            checkLogin();
        });
        dialogLogOut.show();
    }

    private void checkLogin() {
        token = MySharedPreferences.getInstance(getActivity()).getString(AppConstant.USER_TOKEN, "");

        if (!token.equals("")) {
            // đã đang nhập
            binding.contentNullLogin.setVisibility(View.GONE);

            binding.textTop.setVisibility(View.VISIBLE);
            binding.contentProfile.setVisibility(View.VISIBLE);
            binding.contentPost.setVisibility(View.VISIBLE);
            binding.contentChangePass.setVisibility(View.VISIBLE);
            binding.layoutContentPayment.setVisibility(View.VISIBLE);
            binding.contentUpdateAccount.setVisibility(View.VISIBLE);
            binding.contentBySeft.setVisibility(View.VISIBLE);
            binding.contentBookmark.setVisibility(View.VISIBLE);
            binding.contentLogout.setVisibility(View.VISIBLE);
            binding.contentNotNullLogin.setVisibility(View.VISIBLE);
            initData();
        } else {
            // chưa đăng nhập
            binding.contentNullLogin.setVisibility(View.VISIBLE);

            binding.textTop.setVisibility(View.GONE);
            binding.contentProfile.setVisibility(View.GONE);
            binding.contentPost.setVisibility(View.GONE);
            binding.contentChangePass.setVisibility(View.GONE);
            binding.layoutContentPayment.setVisibility(View.GONE);
            binding.contentUpdateAccount.setVisibility(View.GONE);
            binding.contentBySeft.setVisibility(View.GONE);
            binding.contentBookmark.setVisibility(View.GONE);
            binding.contentLogout.setVisibility(View.GONE);
            binding.contentNotNullLogin.setVisibility(View.GONE);
        }
    }

    private void resetDataUser() {
        MySharedPreferences.getInstance(getActivity()).putString(AppConstant.USER_TOKEN, "");
        viewModel.updateTokenDevice(new UserRequestTokenDevice(UserClient.getInstance().getId(), ""));
    }
}