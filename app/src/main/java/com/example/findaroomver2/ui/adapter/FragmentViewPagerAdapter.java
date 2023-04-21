package com.example.findaroomver2.ui.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.findaroomver2.ui.fragment.HomeFragment;
import com.example.findaroomver2.ui.fragment.ListPostFragment;
import com.example.findaroomver2.ui.fragment.NotificationFragment;
import com.example.findaroomver2.ui.fragment.UpPostFragment;
import com.example.findaroomver2.ui.fragment.UserFragment;

public class FragmentViewPagerAdapter extends FragmentStatePagerAdapter {

    HomeFragment homeFragment;
    NotificationFragment notificationFragment;
    UpPostFragment upPostFragment;
    ListPostFragment listPostFragment;
    UserFragment userFragment;
    private Fragment mCurrentFragment;

    public FragmentViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        homeFragment = new HomeFragment();
        notificationFragment = new NotificationFragment();
        upPostFragment = new UpPostFragment();
        listPostFragment = new ListPostFragment();
        userFragment = new UserFragment();
    }

    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return notificationFragment;
            case 2:
                return upPostFragment;
            case 3:
                return listPostFragment;
            case 4:
                return userFragment;
            default:
                return homeFragment;
        }
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            mCurrentFragment = ((Fragment) object);
        }
        super.setPrimaryItem(container, position, object);
    }


}

