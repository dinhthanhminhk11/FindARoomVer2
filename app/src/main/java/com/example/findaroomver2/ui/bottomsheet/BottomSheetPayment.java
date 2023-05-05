package com.example.findaroomver2.ui.bottomsheet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.findaroomver2.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class BottomSheetPayment extends BottomSheetDialog {
    private CallBack callBack;
    private ImageView close;
    private TextView reset;
    private LinearLayout payVisa;

    public BottomSheetPayment(@NonNull Context context, CallBack callBack) {
        super(context);
        this.callBack = callBack;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.BOTTOM);
        setContentView(R.layout.bottomsheet_payment);
        close = (ImageView) findViewById(R.id.close);
        reset = (TextView) findViewById(R.id.reset);
        payVisa = (LinearLayout) findViewById(R.id.payVisa);

        close.setOnClickListener(v -> {
            callBack.onCLickCLose();
        });

        payVisa.setOnClickListener(v -> {
            callBack.onClickPayment();
        });
    }

    public interface CallBack {
        void onCLickCLose();

        void onClickPayment();
    }
}
