package com.example.findaroomver2.ui.bottomsheet;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.example.findaroomver2.R;
import com.example.findaroomver2.model.Post;
import com.example.findaroomver2.repository.Repository;
import com.example.findaroomver2.response.post.PostResponse;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.function.Consumer;

public class BottomSheetManagerPost extends BottomSheetDialog {
    private Callback callback;
    private Repository repository;
    private ImageView close;
    private AppCompatButton btnEdit;
    private AppCompatButton btnDelete;
    private AppCompatButton btnAds;
    private AppCompatButton btnStatus;
    private boolean statusRoom = false;
    private boolean idAds = false;
    private String idPost;

    public BottomSheetManagerPost(@NonNull Context context, Repository repository, boolean statusRoom, String idPost) {
        super(context);
        this.repository = repository;
        this.idPost = idPost;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.BOTTOM);
        setContentView(R.layout.bottomsheet_managerpost);
        initView();
    }

    private void initView() {
        close = (ImageView) findViewById(R.id.close);
        btnEdit = (AppCompatButton) findViewById(R.id.btnEdit);
        btnDelete = (AppCompatButton) findViewById(R.id.btnDelete);
        btnStatus = (AppCompatButton) findViewById(R.id.btnStatus);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onClickDelete();
                dismiss();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onClickEdit();
                dismiss();
            }
        });

        repository.getStatusPost(idPost, new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) {
                statusRoom = aBoolean;
                if (statusRoom) {
                    btnStatus.setText("Cập nhật trạng thái: Hết phòng");
                } else {
                    btnStatus.setText("Cập nhật trạng thái: Còn phòng");
                }
            }
        });
        btnStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (statusRoom) {
                    repository.updateStatusRoom(new Post(false, "Hết phòng", idPost), new Consumer<PostResponse>() {
                        @Override
                        public void accept(PostResponse postResponse) {
                            dismiss();
                            Toast.makeText(btnAds.getContext(), postResponse.getMessage().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    repository.updateStatusRoom(new Post(true, "Còn phòng", idPost), new Consumer<PostResponse>() {
                        @Override
                        public void accept(PostResponse postResponse) {
                            dismiss();
                            Toast.makeText(btnAds.getContext(), postResponse.getMessage().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    public interface Callback {
        void onClickDelete();

        void onClickEdit();
    }
}
