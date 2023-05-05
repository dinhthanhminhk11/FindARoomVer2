package com.example.findaroomver2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.findaroomver2.constant.AppConstant;
import com.example.findaroomver2.constant.NotificationCenter;
import com.example.findaroomver2.databinding.ActivityMainBinding;
import com.example.findaroomver2.event.KeyEvent;
import com.example.findaroomver2.model.UserClient;
import com.example.findaroomver2.response.UserResponseLogin;
import com.example.findaroomver2.sharedpreferences.MySharedPreferences;
import com.example.findaroomver2.ui.adapter.FragmentViewPagerAdapter;
import com.example.findaroomver2.ui.fragment.ChatFragment;
import com.example.findaroomver2.ui.fragment.HomeFragment;
import com.example.findaroomver2.ui.fragment.NotificationFragment;
import com.example.findaroomver2.ui.fragment.PostFragment;
import com.example.findaroomver2.ui.fragment.UserFragment;
import com.example.findaroomver2.viewmodel.MainViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
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

        activityMainBinding.bottomBar.setOnNavigationItemSelectedListener(this);
        loadFragment(new HomeFragment());

        String token = MySharedPreferences.getInstance(this).getString(AppConstant.USER_TOKEN, "");

        if (token != null || !token.equals("")) {
            mainViewModel.loginToken(token);
        }

        backPressToast = Toast.makeText(getApplicationContext(), "Nhấn lần nữa để thoát", Toast.LENGTH_SHORT);

        mainViewModel.getUserResponseLoginMutableLiveData().observe(this, new Observer<UserResponseLogin>() {
            @Override
            public void onChanged(UserResponseLogin userResponseLogin) {
                UserClient userClient = UserClient.getInstance();
                userClient.setEmail(userResponseLogin.getData().getEmail());
                userClient.setPhone(userResponseLogin.getData().getPhone());
                userClient.setFullName(userResponseLogin.getData().getFullName());
                userClient.setId(userResponseLogin.getData().getId());
                userClient.setRole(userResponseLogin.getData().getRole());
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

        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.tab_home) {
            loadFragment(new HomeFragment());
            return true;
        } else if (id == R.id.tab_notification) {
            loadFragment(new NotificationFragment());
            return true;
        } else if (id == R.id.tab_upload) {
            loadFragment(new PostFragment());
            return true;
        } else if (id == R.id.tab_user) {
            loadFragment(new UserFragment());
            return true;
        } else if (id == R.id.tab_chat) {
            loadFragment(new ChatFragment());
            return true;
        }
        return false;
    }

    void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
    }
}