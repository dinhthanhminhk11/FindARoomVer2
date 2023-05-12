package com.example.findaroomver2.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.findaroomver2.constant.AppConstant;
import com.example.findaroomver2.databinding.FragmentNotificationBinding;
import com.example.findaroomver2.model.NotificationModel;
import com.example.findaroomver2.model.UserClient;
import com.example.findaroomver2.response.ListNotificationResponse;
import com.example.findaroomver2.response.TextResponse;
import com.example.findaroomver2.sharedpreferences.MySharedPreferences;
import com.example.findaroomver2.ui.activity.CommentActivity;
import com.example.findaroomver2.ui.activity.DetailActivity;
import com.example.findaroomver2.ui.activity.LoginActivity;
import com.example.findaroomver2.ui.adapter.NotificationAdapter;
import com.example.findaroomver2.viewmodel.MainViewModel;

import org.greenrobot.eventbus.EventBus;


public class NotificationFragment extends Fragment {

    private FragmentNotificationBinding binding;
    private MainViewModel viewModel;

    private NotificationAdapter notificationAdapter;

    private String token = "";

    public NotificationFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNotificationBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    private void initData() {
        binding.reLoad.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.getListNotificationByIdUser(UserClient.getInstance().getId());
                binding.reLoad.setRefreshing(false);
            }
        });

        viewModel.getListNotificationByIdUser(UserClient.getInstance().getId());
        viewModel.getListNotificationResponseMutableLiveData().observe(getActivity(), new Observer<ListNotificationResponse>() {
            @Override
            public void onChanged(ListNotificationResponse listNotificationResponse) {
                if (listNotificationResponse.getData().size() > 0) {
                    binding.contentNullList.setVisibility(View.GONE);
                    binding.reLoad.setVisibility(View.VISIBLE);
                } else {
                    binding.contentNullList.setVisibility(View.VISIBLE);
                    binding.reLoad.setVisibility(View.GONE);
                }
                notificationAdapter.setData(listNotificationResponse.getData());
                notificationAdapter.setCallback(new NotificationAdapter.Callback() {
                    @Override
                    public void onClick(NotificationModel notification) {
                        if (notification.isSeem()) {
                            viewModel.updateNotiSeen(notification.get_id());
                        }
                        if (notification.getType() == 1 || notification.getType() == 4) {
                            Intent intent = new Intent(getActivity(), DetailActivity.class);
                            intent.putExtra(AppConstant.ID_POST, notification.getIdPost());
                            startActivity(intent);
                        } else if (notification.getType() == 2) {
                            Intent intent = new Intent(getActivity(), CommentActivity.class);
                            intent.putExtra(AppConstant.ID_POST, notification.getIdPost());
                            startActivity(intent);
                        }
                    }
                });
                binding.listNotification.setAdapter(notificationAdapter);
            }
        });
        viewModel.getProgress().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });
    }

    private void initView() {
        notificationAdapter = new NotificationAdapter();
        viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
        binding.listNotification.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        token = MySharedPreferences.getInstance(getActivity()).getString(AppConstant.USER_TOKEN, "");

        if (!token.equals("")) {
            binding.contentNullLogin.setVisibility(View.GONE);
            binding.listNotification.setVisibility(View.VISIBLE);
            initData();
        } else {
            binding.contentNullLogin.setVisibility(View.VISIBLE);
            binding.listNotification.setVisibility(View.GONE);
        }
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
    }
}