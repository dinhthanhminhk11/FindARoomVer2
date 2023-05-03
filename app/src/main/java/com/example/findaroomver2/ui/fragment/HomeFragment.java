package com.example.findaroomver2.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

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
import com.example.findaroomver2.request.login.Data;
import com.example.findaroomver2.response.post.PostHome;
import com.example.findaroomver2.response.post.PostResponse;
import com.example.findaroomver2.sharedpreferences.MySharedPreferences;
import com.example.findaroomver2.ui.activity.DetailActivity;
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
        mainViewModel.getAllListPostHome();

        binding.listItemTrend.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        binding.rcvPost.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

    }

    private void intData() {
        List<String> imageUrl = new ArrayList<>();
        imageUrl.add("http://res.cloudinary.com/dl4lo9r1y/image/upload/v1682587330/vfbz9nruf6ht8orbb0up.jpg");
        imageUrl.add("http://res.cloudinary.com/dl4lo9r1y/image/upload/v1682587331/h8spndixobhxh7xvhfta.jpg");
        imageUrl.add("http://res.cloudinary.com/dl4lo9r1y/image/upload/v1682587332/pgofa40zbddhdwhkvu0g.jpg");
        imageUrl.add("http://res.cloudinary.com/dl4lo9r1y/image/upload/v1682587340/shkdz3uwcuvxzpt6tzw7.jpg");
        imageUrl.add("http://res.cloudinary.com/dl4lo9r1y/image/upload/v1682587349/behj9iqency72px0hcct.jpg");
        imageUrl.add("http://res.cloudinary.com/dl4lo9r1y/image/upload/v1682589923/a6zppoxqi9ssu8kzmke0.jpg");

        PostTrendAdapter postTrendAdapter = new PostTrendAdapter(imageUrl);
        binding.listItemTrend.setAdapter(postTrendAdapter);

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
            mainViewModel.getAllListPostHome();
        }
    }
}