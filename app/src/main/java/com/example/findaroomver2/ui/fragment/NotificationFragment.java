package com.example.findaroomver2.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.findaroomver2.R;
import com.example.findaroomver2.databinding.FragmentNotificationBinding;
import com.example.findaroomver2.ui.adapter.NoticeViewPagerAdapter;


public class NotificationFragment extends Fragment {

    FragmentNotificationBinding fragmentNotificationBinding;
    NoticeViewPagerAdapter noticeViewPagerAdapter = null;

    public NotificationFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentNotificationBinding = FragmentNotificationBinding.inflate(getLayoutInflater());
        return fragmentNotificationBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView(){
        noticeViewPagerAdapter = new NoticeViewPagerAdapter(getChildFragmentManager(), NoticeViewPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        fragmentNotificationBinding.noticeViewPager.setAdapter(noticeViewPagerAdapter);
        fragmentNotificationBinding.noticeViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                fragmentNotificationBinding.noticeTablayout.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        fragmentNotificationBinding.noticeTablayout.setupWithViewPager(fragmentNotificationBinding.noticeViewPager);
    }
}