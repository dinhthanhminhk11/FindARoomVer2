package com.example.findaroomver2.ui.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.findaroomver2.ui.fragment.AllNoticeFragment;
import com.example.findaroomver2.ui.fragment.PostNoticeFragment;
import com.example.findaroomver2.ui.fragment.SystemNoticeFragment;

public class NoticeViewPagerAdapter extends FragmentPagerAdapter {

    AllNoticeFragment allNoticeFragment;
    SystemNoticeFragment systemNoticeFragment;
    PostNoticeFragment postNoticeFragment;

    private Fragment mNoticeCurrentFragment;

    public NoticeViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);

        allNoticeFragment = new AllNoticeFragment();
        systemNoticeFragment = new SystemNoticeFragment();
        postNoticeFragment = new PostNoticeFragment();
    }

    public Fragment getmNoticeCurrentFragment(){
        return mNoticeCurrentFragment;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return allNoticeFragment;
            case 1:
                return systemNoticeFragment;
            case 2:
                return postNoticeFragment;
            default:
                return allNoticeFragment;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Tất Cả";
                break;
            case 1:
                title = "Hệ thống";
                break;
            case 2:
                title = "Tin của bạn";
                break;

        }
        return title;
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        if (getmNoticeCurrentFragment() != object) {
            mNoticeCurrentFragment = ((Fragment) object);
        }
        super.setPrimaryItem(container, position, object);
    }
}
