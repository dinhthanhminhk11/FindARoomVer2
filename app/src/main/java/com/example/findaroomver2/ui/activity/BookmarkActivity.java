package com.example.findaroomver2.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.findaroomver2.R;
import com.example.findaroomver2.databinding.ActivityBookmarkBinding;
import com.example.findaroomver2.model.UserClient;
import com.example.findaroomver2.response.bookmark.ListBookmarkResponse;
import com.example.findaroomver2.ui.adapter.BookmarkAdapter;
import com.example.findaroomver2.viewmodel.BookmarkViewModel;

public class BookmarkActivity extends AppCompatActivity {

    private ActivityBookmarkBinding binding;
    private BookmarkViewModel bookmarkViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookmarkBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        initToolbar();
        initData();
    }

    private void initView() {
        bookmarkViewModel = new ViewModelProvider(this).get(BookmarkViewModel.class);
        binding.listBookmark.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void initData() {
        bookmarkViewModel.getListBookmarkByIdUser(UserClient.getInstance().getId());
        bookmarkViewModel.getBookmarkResponseMutableLiveData().observe(this, new Observer<ListBookmarkResponse>() {
            @Override
            public void onChanged(ListBookmarkResponse listBookmarkResponse) {
                if (listBookmarkResponse.getBookmark().size() > 0) {
                    binding.listNull.setVisibility(View.GONE);
                    binding.listBookmark.setVisibility(View.VISIBLE);
                    BookmarkAdapter bookmarkAdapter = new BookmarkAdapter(listBookmarkResponse.getBookmark());
                    binding.listBookmark.setAdapter(bookmarkAdapter);
                } else {
                    binding.listNull.setVisibility(View.VISIBLE);
                    binding.listBookmark.setVisibility(View.GONE);
                }

            }
        });
        bookmarkViewModel.getmProgressMutableData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });
    }

    private void initToolbar() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_back_ios);
        binding.toolbar.setTitle("Đã lưu");
        binding.toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });
    }
}