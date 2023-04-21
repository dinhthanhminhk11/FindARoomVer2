package com.example.findaroomver2;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.findaroomver2.databinding.ActivityMainBinding;
import com.example.findaroomver2.ui.adapter.FragmentViewPagerAdapter;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainActivity extends AppCompatActivity {

    public ActivityMainBinding activityMainBinding;
    private FragmentViewPagerAdapter viewPagerAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        viewPagerAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), FragmentViewPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        activityMainBinding.vpHome.setAdapter(viewPagerAdapter);
        activityMainBinding.vpHome.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(
                    int position,
                    float positionOffset,
                    int positionOffsetPixels
            ) {

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
            public void onTabSelected(
                    int i,
                    AnimatedBottomBar.Tab tab,
                    int i1,
                    @NonNull AnimatedBottomBar.Tab tab1
            ) {
                activityMainBinding.vpHome.setCurrentItem(i1);
            }

            @Override
            public void onTabReselected(int i, @NonNull AnimatedBottomBar.Tab tab) {

            }
        });
    }
}