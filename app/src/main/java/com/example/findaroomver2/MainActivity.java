package com.example.findaroomver2;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.findaroomver2.constant.AppConstant;
import com.example.findaroomver2.constant.NotificationCenter;
import com.example.findaroomver2.databinding.ActivityMainBinding;
import com.example.findaroomver2.event.KeyEvent;
import com.example.findaroomver2.model.UserClient;
import com.example.findaroomver2.response.UserResponseLogin;
import com.example.findaroomver2.sharedpreferences.MySharedPreferences;
import com.example.findaroomver2.ui.adapter.FragmentViewPagerAdapter;
import com.example.findaroomver2.viewmodel.MainViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import nl.joery.animatedbottombar.AnimatedBottomBar;


public class MainActivity extends AppCompatActivity {
    private int backPressedCount = 0;
    private final int MAX_BACK_PRESS_COUNT = 2;
    private final int BACK_PRESS_TIME_INTERVAL = 2000;
    private Toast backPressToast;
    ActivityMainBinding activityMainBinding;
    FragmentViewPagerAdapter viewPagerAdapter = null;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        String token = MySharedPreferences.getInstance(this).getString(AppConstant.USER_TOKEN, "");

        if (token != null || !token.equals("")) {
            mainViewModel.loginToken(token);
        }

        viewPagerAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), FragmentViewPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        activityMainBinding.vpHome.setAdapter(viewPagerAdapter);
        activityMainBinding.vpHome.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                activityMainBinding.bottomBar.selectTabAt(position, true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        activityMainBinding.bottomBar.setupWithViewPager(activityMainBinding.vpHome);
        activityMainBinding.bottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int i, AnimatedBottomBar.Tab tab, int i1, AnimatedBottomBar.Tab tab1) {
                activityMainBinding.vpHome.setCurrentItem(i1);
            }

            @Override
            public void onTabReselected(int i, @NonNull AnimatedBottomBar.Tab tab) {

            }
        });

        backPressToast = Toast.makeText(getApplicationContext(), "Nhấn lần nữa để thoát", Toast.LENGTH_SHORT);

        mainViewModel.getUserResponseLoginMutableLiveData().observe(this, new Observer<UserResponseLogin>() {
            @Override
            public void onChanged(UserResponseLogin userResponseLogin) {
                UserClient userClient = UserClient.getInstance();
                userClient.setEmail(userResponseLogin.getData().getEmail());
                userClient.setPhone(userResponseLogin.getData().getPhone());
                userClient.setFullName(userResponseLogin.getData().getFullName());
                userClient.setId(userResponseLogin.getData().getId());
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (backPressedCount < MAX_BACK_PRESS_COUNT - 1) {
            backPressedCount++;
            backPressToast.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    backPressedCount = 0;
                    backPressToast.cancel();
                }
            }, BACK_PRESS_TIME_INTERVAL);
        } else {
            System.exit(1);
        }
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
        if (event.getIdEven() == NotificationCenter.create_post_success) {
            activityMainBinding.vpHome.setCurrentItem(3);
        }
    }
}