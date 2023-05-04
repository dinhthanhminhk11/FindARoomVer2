package com.example.findaroomver2.ui.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.findaroomver2.R;
import com.example.findaroomver2.constant.AppConstant;
import com.example.findaroomver2.databinding.ActivityCommentBinding;
import com.example.findaroomver2.model.UserClient;
import com.example.findaroomver2.request.comment.Comment;
import com.example.findaroomver2.response.comment.CommentListResponse;
import com.example.findaroomver2.response.comment.CommentResponse;
import com.example.findaroomver2.sharedpreferences.MySharedPreferences;
import com.example.findaroomver2.ui.adapter.comment.CommentAdapter;
import com.example.findaroomver2.viewmodel.CommentViewModel;

public class CommentActivity extends AppCompatActivity implements CommentAdapter.Callback {

    private ActivityCommentBinding binding;

    private CommentViewModel commentViewModel;
    private CommentAdapter commentAdapter;
    private String idPost;
    private String idCommentCallback = "";
    private String nameUserCallback = "";
    private String token;
    private boolean isReply = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCommentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initToolbar();
        initView();
        initData();
    }

    private void initToolbar() {
        binding.toolBar.setNavigationIcon(R.drawable.ic_back_ios);
        binding.toolBar.setTitle("Bình luận");
        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.toolBar.setNavigationOnClickListener(v -> finish());
    }

    private void initData() {

        token = MySharedPreferences.getInstance(this).getString(AppConstant.USER_TOKEN, "");
        if (!token.equals("")) {
            binding.contentNotNullLogin.setVisibility(View.VISIBLE);
        } else {
            binding.contentNotNullLogin.setVisibility(View.GONE);
        }

        idPost = getIntent().getStringExtra(AppConstant.ID_POST);
        commentViewModel.getListCommentByIdPost(idPost);

        commentViewModel.getCommentListResponseMutableLiveData().observe(this, new Observer<CommentListResponse>() {
            @Override
            public void onChanged(CommentListResponse commentListResponse) {
                if (commentListResponse.getData().size() > 0) {
                    commentAdapter = new CommentAdapter(commentListResponse.getData());
                    binding.listComment.setAdapter(commentAdapter);
                    commentAdapter.setCallback(CommentActivity.this);
                    binding.contentNoComment.setVisibility(View.GONE);
                } else {
                    binding.contentNoComment.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.btnSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isReply) {
                    commentViewModel.addComment(new Comment(UserClient.getInstance().getId(), null, binding.edContentChat.getText().toString(), idCommentCallback));
                    idCommentCallback = "";
                    nameUserCallback = "";
                    binding.contentReply.setVisibility(View.GONE);
                    isReply = false;
                } else {
                    commentViewModel.addComment(new Comment(UserClient.getInstance().getId(), idPost, binding.edContentChat.getText().toString()));
                }
                binding.edContentChat.setText("");
            }
        });

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idCommentCallback = "";
                nameUserCallback = "";
                binding.contentReply.setVisibility(View.GONE);
                isReply = false;
            }
        });

        commentViewModel.getCommentResponseMutableLiveData().observe(this, new Observer<CommentResponse>() {
            @Override
            public void onChanged(CommentResponse commentResponse) {
                if (commentResponse.getMessage().isStatus()) {
                    commentViewModel.getListCommentByIdPost(idPost);
                }
            }
        });

        binding.btnSent.setAlpha(0.4f);
        binding.btnSent.setEnabled(false);

        binding.edContentChat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().length() > 0) {
                    binding.btnSent.setEnabled(true);
                    binding.btnSent.setAlpha(1);
                } else {
                    binding.btnSent.setAlpha(0.4f);
                    binding.btnSent.setEnabled(false);
                }
            }
        });
    }

    private void initView() {
        commentViewModel = new ViewModelProvider(this).get(CommentViewModel.class);
        binding.listComment.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onCLickReply(String idComment, String nameUser) {
        if (!token.equals("")) {
            idCommentCallback = idComment;
            nameUserCallback = nameUser;
            binding.contentReply.setVisibility(View.VISIBLE);
            String text = "Đang phản hồi " + nameUser;
            int startIndex = text.indexOf(nameUser);
            int endIndex = startIndex + nameUser.length();
            SpannableStringBuilder builder = new SpannableStringBuilder(text);
            StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
            builder.setSpan(boldSpan, startIndex, endIndex, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            binding.nameUser.setText(builder);
            isReply = true;
        } else {
            Toast.makeText(this, "Đăng nhập để sử dụng chức năng này", Toast.LENGTH_SHORT).show();
        }
    }
}