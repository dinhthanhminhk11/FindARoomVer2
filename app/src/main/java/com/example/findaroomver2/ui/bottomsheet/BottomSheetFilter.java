package com.example.findaroomver2.ui.bottomsheet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.example.findaroomver2.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.slider.RangeSlider;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class BottomSheetFilter extends BottomSheetDialog {
    private EventClick eventClick;
    private NumberFormat fm = new DecimalFormat("#,###");
    private ImageView imgCancel;
    private TextView tvReset;
    private RangeSlider rangeFilter;
    private TextView starPrice;
    private TextView endPrice;
    private AppCompatButton btnFilter;
    private int giaBd = 1000000;
    private int giaKt = 9000000;

    public BottomSheetFilter(@NonNull Context context, EventClick eventClick) {
        super(context);
        this.eventClick = eventClick;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setGravity(Gravity.CENTER);
        setContentView(R.layout.fragment_filter);
        getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
        initView();
    }

    private void initView() {


        imgCancel = (ImageView) findViewById(R.id.imgCancel);
        tvReset = (TextView) findViewById(R.id.tvReset);
        rangeFilter = (RangeSlider) findViewById(R.id.rangeFilter);
        starPrice = (TextView) findViewById(R.id.starPrice);
        endPrice = (TextView) findViewById(R.id.endPrice);
        btnFilter = (AppCompatButton) findViewById(R.id.btnFilter);

        starPrice.setText(fm.format(giaBd) + " đ");
        endPrice.setText(fm.format(giaKt) + " đ");
        rangeFilter.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {

            }

            @Override
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                giaBd = (int) slider.getValues().get(0).floatValue();
                giaKt = (int) slider.getValues().get(1).floatValue();
            }
        });

        rangeFilter.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                starPrice.setText(fm.format(slider.getValues().get(0).floatValue()) + " đ");
                endPrice.setText(fm.format(slider.getValues().get(1).floatValue()) + " đ");
            }
        });

        tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreate(null);
                giaBd = 1000000;
                giaKt = 9000000;
                resetData();
            }
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventClick.onCLickFilter(giaBd + "", giaKt + "");
                dismiss();
            }
        });

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

    private void resetData() {
        starPrice.setText(fm.format(giaBd) + " đ");
        endPrice.setText(fm.format(giaKt) + " đ");
        rangeFilter.setValues((float) giaBd, (float) giaKt);
    }

    public interface EventClick {
        void onCLickFilter(String giaBd, String giaKt);
    }
}
