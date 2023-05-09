package com.example.findaroomver2.ui.bottomsheet;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.example.findaroomver2.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class BottomSheetBill extends BottomSheetDialog {
    private ImageView close;
    private LinearLayout contentNullAds;
    private LinearLayout contentBackground3;
    private TextView text9;
    private TextView pricePost;
    private TextView date;
    private TextView priceDate;
    private TextView text11;
    private TextView checkMoneyUserView;
    private TextView titleAds;
    private TextView priceAll;
    private AppCompatButton btnPay;
    private Callback callback;
    private NumberFormat fm = new DecimalFormat("#,###");
    private int dateData = 0;
    private int priceAllData = 0;
    private boolean isAds = false;

    private boolean checkMoneyUser = false;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public BottomSheetBill(@NonNull Context context, int dateData, int pricePostData, boolean isAds, boolean checkMoneyUser) {
        super(context);
        this.dateData = dateData;
        this.priceAllData = pricePostData;
        this.isAds = isAds;
        this.checkMoneyUser = checkMoneyUser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setGravity(Gravity.BOTTOM);
        setContentView(R.layout.bottomsheet_bill);
        initView();
    }

    private void initView() {

        close = (ImageView) findViewById(R.id.close);
        contentBackground3 = (LinearLayout) findViewById(R.id.contentBackground3);
        text9 = (TextView) findViewById(R.id.text9);
        pricePost = (TextView) findViewById(R.id.pricePost);
        checkMoneyUserView = (TextView) findViewById(R.id.checkMoneyUserView);
        date = (TextView) findViewById(R.id.date);
        priceDate = (TextView) findViewById(R.id.priceDate);
        text11 = (TextView) findViewById(R.id.text11);
        titleAds = (TextView) findViewById(R.id.titleAds);
        priceAll = (TextView) findViewById(R.id.priceAll);
        btnPay = (AppCompatButton) findViewById(R.id.btnPay);

        contentNullAds = (LinearLayout) findViewById(R.id.contentNullAds);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onClick();
                cancel();
            }
        });
        int moneyPost = priceAllData - (dateData * 50000);

        if (isAds) {
            contentNullAds.setVisibility(View.VISIBLE);
            titleAds.setVisibility(View.VISIBLE);
        } else {
            contentNullAds.setVisibility(View.GONE);
            titleAds.setVisibility(View.GONE);
        }

        if (!checkMoneyUser) {
            checkMoneyUserView.setVisibility(View.GONE);
            btnPay.setEnabled(true);
        } else {
            checkMoneyUserView.setVisibility(View.VISIBLE);
            btnPay.setEnabled(false);
        }

        String textPricePost = moneyPost <= 0 ? "free" : fm.format(moneyPost) + " VND";
        pricePost.setText(textPricePost);

        date.setText(dateData + " ngày x 50.000 nghìn");
        priceDate.setText(fm.format(dateData * 50000) + " VND");

        priceAll.setText(fm.format(priceAllData) + " VND");

    }

    public interface Callback {
        void onClick();
    }
}
