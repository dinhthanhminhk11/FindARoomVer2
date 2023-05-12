package com.example.findaroomver2.ui.fragment;

import android.app.ActivityOptions;
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
import android.widget.TextView;

import com.example.findaroomver2.R;
import com.example.findaroomver2.constant.AppConstant;
import com.example.findaroomver2.constant.NotificationCenter;
import com.example.findaroomver2.databinding.FragmentHomeBinding;
import com.example.findaroomver2.event.KeyEvent;
import com.example.findaroomver2.model.Post;
import com.example.findaroomver2.model.UserClient;
import com.example.findaroomver2.request.login.Data;
import com.example.findaroomver2.response.post.PostHome;
import com.example.findaroomver2.response.post.PostResponse;
import com.example.findaroomver2.sharedpreferences.MySharedPreferences;
import com.example.findaroomver2.ui.activity.DetailActivity;
import com.example.findaroomver2.ui.activity.SearchRoomActivity;
import com.example.findaroomver2.ui.adapter.homefragment.PostTrendAdapter;
import com.example.findaroomver2.ui.adapter.postfragment.PostAdapter;
import com.example.findaroomver2.ui.customview.circleimage.CircleImageView;
import com.example.findaroomver2.viewmodel.MainViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private MainViewModel mainViewModel;
    private String mParam1;
    private String mParam2;
    private PostAdapter postAdapter;
    private TextView nameUser;
    private CircleImageView imageUser;

    public HomeFragment() {
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        intData();
    }

    private void initView() {
        mainViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);

        binding.listItemTrend.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        binding.rcvPost.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SearchRoomActivity.class), ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
            }
        });

        binding.reLoad.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainViewModel.getAllListPostHome();
                binding.reLoad.setRefreshing(false);
            }
        });
    }

    private void intData() {
        mainViewModel.getListMutableLiveDataPost().observe(getActivity(), new Observer<PostHome>() {
            @Override
            public void onChanged(PostHome posts) {
                postAdapter = new PostAdapter(posts.getData());
                postAdapter.setCallback(new PostAdapter.Callback() {
                    @Override
                    public void onClickItem(Post post) {
                        Intent intent = new Intent(getActivity(), DetailActivity.class);
                        intent.putExtra(AppConstant.ID_POST, post.get_id());
                        startActivity(intent);
                    }

                    @Override
                    public void onClickMore(Post post) {

                    }

                    @Override
                    public void onClickAddHeart(Post post) {

                    }

                    @Override
                    public void onClickRemoteHeart(Post post) {

                    }
                });
                binding.rcvPost.setAdapter(postAdapter);
            }
        });

        mainViewModel.getProgress().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });

        mainViewModel.getLiveDataHomeAds().observe(getActivity(), new Observer<PostHome>() {
            @Override
            public void onChanged(PostHome postHome) {
                PostTrendAdapter postTrendAdapter = new PostTrendAdapter(postHome.getData());
                binding.listItemTrend.setAdapter(postTrendAdapter);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mainViewModel.getAllListPostHome();
        mainViewModel.getListHomeAds();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(KeyEvent event) {
        if (event.getIdEven() == NotificationCenter.checkLogin) {
//            mainViewModel.getAllListPostHome();
        }
    }
}