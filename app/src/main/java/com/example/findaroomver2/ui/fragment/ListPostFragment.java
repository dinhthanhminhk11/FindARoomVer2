package com.example.findaroomver2.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.findaroomver2.databinding.FragmentListPostBinding;
import com.example.findaroomver2.ui.adapter.PostViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;


public class ListPostFragment extends Fragment {

    FragmentListPostBinding fragmentListPostBinding;
    PostViewPagerAdapter postViewPagerAdapter = null;

    public ListPostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentListPostBinding = FragmentListPostBinding.inflate(getLayoutInflater());
        return fragmentListPostBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       initView();
    }

    private void initView(){
        postViewPagerAdapter = new PostViewPagerAdapter(getChildFragmentManager(), PostViewPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        fragmentListPostBinding.postViewPager.setAdapter(postViewPagerAdapter);
        fragmentListPostBinding.postViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                fragmentListPostBinding.postTablayout.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        fragmentListPostBinding.postTablayout.setupWithViewPager(fragmentListPostBinding.postViewPager);


    }
}