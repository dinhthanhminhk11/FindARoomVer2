package com.example.findaroomver2.ui.bottomsheet;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.example.findaroomver2.R;
import com.example.findaroomver2.repository.Repository;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class BottomSheetManagerPost extends BottomSheetDialog {
    private Callback callback;
    private Repository repository;
    private ImageView close;
    private AppCompatButton btnEdit;
    private AppCompatButton btnDelete;

    public BottomSheetManagerPost(@NonNull Context context) {
        super(context);
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
    }

    public interface Callback {
        void onClickDelete();

        void onClickEdit();
    }
}
