package com.example.findaroomver2.ui.customview.toast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findaroomver2.R;

public class CustomToast extends Toast {
    public static int INFO = 1;
    public static int WARN = 2;
    public static int ERROR = 3;
    public static int SUCCESS = 4;
    public static int HAPPY = 5;
    public static int SAD = 6;
    public static int CONFUSE = 7;
    public static int DELETE = 8;
    public static int SAVE = 9;
    public static int NORMAL = 10;


    public CustomToast(Context context) {
        super(context);
    }
    @SuppressLint("MissingInflatedId")
    public static Toast ct(Context context, CharSequence message, int duration, int type, boolean icon) {

        Toast toast = new Toast(context);
        toast.setDuration(duration);


        View view = LayoutInflater.from(context).inflate(R.layout.cutetoast_layout, null, false);

        TextView textView = view.findViewById(R.id.toast_text);
        ImageView imageView = view.findViewById(R.id.image_icon);
        LinearLayout linearLayout = view.findViewById(R.id.l_layout);

        textView.setText(message);

        if (icon) {
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }

        if (type == 1) {
            linearLayout.setBackgroundResource(R.drawable.back_normal);
            imageView.setImageResource(R.drawable.ic_info);
        }

        toast.setView(view);
        return toast;
    }
    @SuppressLint("MissingInflatedId")

    public static Toast ct(Context context, CharSequence message, int duration, int type) {

        Toast toast = new Toast(context);
        toast.setDuration(duration);

        View view = LayoutInflater.from(context).inflate(R.layout.cutetoast_layout, null, false);

        TextView textView = view.findViewById(R.id.toast_text);
        ImageView imageView = view.findViewById(R.id.image_icon);
        LinearLayout linearLayout = view.findViewById(R.id.l_layout);

        textView.setText(message);

        imageView.setVisibility(View.GONE);

        if (type == 1) {
            linearLayout.setBackgroundResource(R.drawable.back_normal);
            imageView.setImageResource(R.drawable.ic_info);
        }

        toast.setView(view);
        return toast;
    }

    @SuppressLint("MissingInflatedId")
    public static Toast ct(Context context, CharSequence message, int duration, int type, int img) {

        Toast toast = new Toast(context);
        toast.setDuration(duration);

        View view = LayoutInflater.from(context).inflate(R.layout.cutetoast_layout, null, false);

        TextView textView = view.findViewById(R.id.toast_text);
        ImageView imageView = view.findViewById(R.id.image_icon);
        LinearLayout linearLayout = view.findViewById(R.id.l_layout);

        textView.setText(message);
        imageView.setImageResource(img);

        if (type == 1) {
            linearLayout.setBackgroundResource(R.drawable.back_normal);
            imageView.setImageResource(R.drawable.ic_info);
        }

        toast.setView(view);
        return toast;
    }
}
