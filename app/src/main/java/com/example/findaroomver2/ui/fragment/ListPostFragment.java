package com.example.findaroomver2.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.findaroomver2.R;
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
        postViewPagerAdapter = new PostViewPagerAdapter(getChildFragmentManager(), PostViewPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        fragmentListPostBinding.postViewPager.setAdapter(postViewPagerAdapter);

        fragmentListPostBinding.postTablayout.setupWithViewPager(fragmentListPostBinding.postViewPager);

        return fragmentListPostBinding.getRoot();
    }
}