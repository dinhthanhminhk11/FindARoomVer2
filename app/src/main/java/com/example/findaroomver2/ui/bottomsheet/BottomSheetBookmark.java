package com.example.findaroomver2.ui.bottomsheet;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.telecom.Call;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.example.findaroomver2.R;
import com.example.findaroomver2.repository.Repository;
import com.example.findaroomver2.request.bookmark.Bookmark;
import com.example.findaroomver2.response.bookmark.BookmarkResponse;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.function.Consumer;

public class BottomSheetBookmark extends BottomSheetDialog {
    private ImageView close;
    private AppCompatButton btnSave;
    private Callback callback;
    private String idUser;
    private String idPost;
    private boolean isClickSpeed = true;
    private Repository repository;

    public BottomSheetBookmark(@NonNull Context context, String idUser, String idPost, Repository repository) {
        super(context);
        this.idPost = idPost;
        this.idUser = idUser;
        this.repository = repository;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setGravity(Gravity.BOTTOM);
        setContentView(R.layout.bottomsheet_bookmark);
        initView();
    }

    private void initView() {
        close = (ImageView) findViewById(R.id.close);
        btnSave = (AppCompatButton) findViewById(R.id.btnSave);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        repository.getBookmarkByIdUserAndIdPost(idUser, idPost, new Consumer<BookmarkResponse>() {
            @Override
            public void accept(BookmarkResponse bookmarkResponse) {
                if (bookmarkResponse.getBookmark() != null) {
                    btnSave.setText("Bỏ lưu bài viết");
                    isClickSpeed = false;
                } else {
                    btnSave.setText("Lưu bài viết");
                    isClickSpeed = true;
                }
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isClickSpeed) {
                    repository.addBookmark(new Bookmark(idUser, idPost), new Consumer<BookmarkResponse>() {
                        @Override
                        public void accept(BookmarkResponse bookmarkResponse) {
                            Toast.makeText(btnSave.getContext(), bookmarkResponse.getMessage().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
//                    btnSave.setText("Bỏ lưu bài viết");
                    isClickSpeed = false;
                    cancel();
                } else {
                    repository.deleteBookmark(idUser, idPost, new Consumer<BookmarkResponse>() {
                        @Override
                        public void accept(BookmarkResponse bookmarkResponse) {
                            Toast.makeText(btnSave.getContext(), bookmarkResponse.getMessage().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
//                    btnSave.setText("Lưu bài viết");
                    isClickSpeed = true;
                    cancel();
                }
            }
        });
    }

    public interface Callback {
        void onClick(AppCompatButton btnSave);
    }
}
