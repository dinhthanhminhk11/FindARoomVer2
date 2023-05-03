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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findaroomver2.R;
import com.example.findaroomver2.repository.Repository;
import com.example.findaroomver2.response.favourite.CountFavourite;
import com.example.findaroomver2.ui.adapter.homefragment.PersonFavouriteAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.function.Consumer;

public class BottomSheetPersonFavourite extends BottomSheetDialog {

    private ImageView close;
    private RecyclerView btnSave;

    private String idPost;
    private Repository repository;

    public BottomSheetPersonFavourite(@NonNull Context context, String idPost, Repository repository) {
        super(context);
        this.idPost = idPost;
        this.repository = repository;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setGravity(Gravity.BOTTOM);
        setContentView(R.layout.bottomsheet_person_favourite);
        initView();
        initData();
    }

    private void initView() {
        close = (ImageView) findViewById(R.id.close);
        btnSave = (RecyclerView) findViewById(R.id.btnSave);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        btnSave.setLayoutManager(new LinearLayoutManager(btnSave.getContext(), LinearLayoutManager.VERTICAL, false));
    }

    private void initData() {
        repository.getCountFavouriteByIdPost(idPost, new Consumer<CountFavourite>() {
            @Override
            public void accept(CountFavourite countFavourite) {
                if (countFavourite.getMessage().isStatus()) {
                    PersonFavouriteAdapter personFavouriteAdapter = new PersonFavouriteAdapter(countFavourite.getData(), repository);
                    btnSave.setAdapter(personFavouriteAdapter);
                }
            }
        });
    }
}
