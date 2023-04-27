package com.example.findaroomver2.ui.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.findaroomver2.ui.fragment.BestPriceFragment;
import com.example.findaroomver2.ui.fragment.NearestPosrFragment;
import com.example.findaroomver2.ui.fragment.NewestPostFragment;
import com.example.findaroomver2.ui.fragment.WatchedFragment;

public class PostViewPagerAdapter extends FragmentPagerAdapter {

    private Fragment mPostCurrentFragment;

    public PostViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public Fragment getmPostCurrentFragment(){
        return mPostCurrentFragment;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
                return new NearestPosrFragment();
            case 2:
                return new BestPriceFragment();
            case 3:
                return new WatchedFragment();
            default:
                return new NewestPostFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Bài viết mới nhất";
                break;
            case 1:
                title = "Gần bạn nhất";
                break;
            case 2:
                title = "Giá tốt nhất";
                break;
            case 3:
                title = "Xem gần đây";
                break;
        }
        return title;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getmPostCurrentFragment() != object) {
            mPostCurrentFragment = ((Fragment) object);
        }
        super.setPrimaryItem(container, position, object);
    }
}
