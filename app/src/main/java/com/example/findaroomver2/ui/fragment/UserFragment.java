package com.example.findaroomver2.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.findaroomver2.R;
import com.example.findaroomver2.databinding.FragmentUserBinding;
import com.example.findaroomver2.model.UserClient;
import com.example.findaroomver2.ui.activity.EditProfileActivity;
import com.example.findaroomver2.ui.activity.PayCashYourActivity;


public class UserFragment extends Fragment {
    private FragmentUserBinding binding;

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
        initData();
    }

    private void initData() {
        binding.nameUser.setText(UserClient.getInstance().getFullName());
        binding.email.setText(UserClient.getInstance().getEmail());
    }

    private void initView() {
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
    }
}