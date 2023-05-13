package com.example.findaroomver2.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;

import com.example.findaroomver2.R;
import com.example.findaroomver2.constant.AppConstant;
import com.example.findaroomver2.databinding.ActivityAddMoneyBinding;
import com.example.findaroomver2.model.UserClient;
import com.example.findaroomver2.request.money.CashFlowRequest;
import com.example.findaroomver2.response.TextResponse;
import com.example.findaroomver2.ui.bottomsheet.BottomSheetPayment;
import com.example.findaroomver2.ui.customview.toast.CustomToast;
import com.example.findaroomver2.viewmodel.AddCashViewModel;

import java.text.DecimalFormat;
import java.text.ParseException;

public class AddMoneyActivity extends AppCompatActivity {
    private ActivityAddMoneyBinding binding;
    private String gia;
    private BottomSheetPayment bottomSheetPayment;
    private AddCashViewModel addCashViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddMoneyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initToolbar();
        initView();

        binding.number1.setText("100.000");
        binding.number2.setText("200.000");
        binding.number3.setText("500.000");

        binding.editInputMoney.addTextChangedListener(new NumberTextWatcher(binding.editInputMoney));

        binding.editInputMoney.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                v.performClick();
                if (binding.editInputMoney.getCompoundDrawables()[DRAWABLE_RIGHT] == null)
                    return false;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (binding.editInputMoney.getRight() - binding.editInputMoney.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        resetText(false);
                        return true;
                    }
                }
                return false;
            }
        });

        binding.contentBackgroundAddMoney.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = binding.contentBackgroundAddMoney.getRootView().getHeight() - binding.contentBackgroundAddMoney.getHeight();
                if ((heightDiff > dpToPx(AddMoneyActivity.this, 200)) && (binding.editInputMoney.getText().toString().length() < 7)) { // if more than 200 dp, it's probably a keyboard...
                    binding.contentLayoutMoney.setVisibility(View.VISIBLE);
                } else {
                    binding.contentLayoutMoney.setVisibility(View.GONE);
                }
            }
        });

        addCashViewModel = new ViewModelProvider(this).get(AddCashViewModel.class);
        addCashViewModel.getmProgressMutableData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });

        addCashViewModel.getTestResponseMutableLiveData().observe(this, new Observer<TextResponse>() {
            @Override
            public void onChanged(TextResponse textResponse) {
                if (textResponse.isStatus()) {
                    Intent intent = new Intent(AddMoneyActivity.this, PayCashYourActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(AppConstant.CHECK_SUCCESS, AppConstant.CHECK_SUCCESS_ADD_MONEY);
                    startActivity(intent);
                } else {
                    CustomToast.ct(AddMoneyActivity.this, textResponse.getMessage(), CustomToast.LENGTH_SHORT, CustomToast.INFO, true).show();
                }
            }
        });
        checkBtnClick();
    }

    private void checkBtnClick() {
        if (binding.editInputMoney.getText().toString().isEmpty() || !binding.textPayment.getText().toString().trim().equals("Thẻ VISA (VISA card)")) {
            binding.btnPay.setAlpha(0.4f);
            binding.btnPay.setEnabled(false);
        } else {
            binding.btnPay.setAlpha(1);
            binding.btnPay.setEnabled(true);
        }
    }

    private void initView() {
        binding.btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textString = binding.editInputMoney.getText().toString().replaceAll(AppConstant.DOT, "");
                addCashViewModel.createCashByUser(new CashFlowRequest(UserClient.getInstance().getId(), true, "Thông báo biến động số dư", "Nạp tiền vào tài khoản (VISA)", Integer.parseInt(textString)));
            }
        });
        binding.number1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.editInputMoney.setText(binding.number1.getText().toString());

            }
        });
        binding.number2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.editInputMoney.setText(binding.number2.getText().toString());
            }
        });
        binding.number3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.editInputMoney.setText(binding.number3.getText().toString());
            }
        });
        binding.contentPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    private void initToolbar() {
        binding.toolBar.setNavigationIcon(R.drawable.ic_back_ios);
        binding.toolBar.setTitle("Nạp tiền");
        binding.toolBar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });
    }


    private void resetText(boolean showClearButton) {
        if (showClearButton) {
            binding.editInputMoney.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_round_close_24, 0);
            return;
        } else {
            binding.editInputMoney.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        binding.editInputMoney.setText("");
        binding.editInputMoney.requestFocus();
    }

    class NumberTextWatcher implements TextWatcher {
        private DecimalFormat df;
        private DecimalFormat dfnd;
        private boolean hasFractionalPart;
        private EditText et;

        public NumberTextWatcher(EditText et) {
            df = new DecimalFormat("#,###.##");
            df.setDecimalSeparatorAlwaysShown(true);
            dfnd = new DecimalFormat("#,###");
            this.et = et;
            hasFractionalPart = false;
        }

        @SuppressWarnings("unused")
        private static final String TAG = "NumberTextWatcher";

        public void afterTextChanged(Editable s) {
            et.removeTextChangedListener(this);
            if (binding.editInputMoney.getText().toString().isEmpty() || binding.editInputMoney.getText().toString().length() == 0 || binding.editInputMoney.getText().toString().trim().equals("0") || !binding.textPayment.getText().toString().trim().equals("Thẻ VISA (VISA card)")) {
                binding.sumPrice.setText("0 ₫");
                binding.priceAll.setText("0 ₫");

                binding.number1.setText(dfnd.format(100000));
                binding.number2.setText(dfnd.format(200000));
                binding.number3.setText(dfnd.format(500000));
                binding.btnPay.setAlpha(0.4f);
                binding.btnPay.setEnabled(false);
            } else {
                checkBtnClick();
                binding.btnPay.setAlpha(1);
            }

            if (binding.editInputMoney.getText().toString().length() == 1 && !binding.editInputMoney.getText().toString().trim().equals("0")) {
                int sum1 = Integer.parseInt(binding.editInputMoney.getText().toString().trim()) * 10000;
                int sum2 = Integer.parseInt(binding.editInputMoney.getText().toString().trim()) * 100000;
                int sum3 = Integer.parseInt(binding.editInputMoney.getText().toString().trim()) * 1000000;
                binding.number1.setText(String.valueOf(dfnd.format(sum1)));
                binding.number2.setText(String.valueOf(dfnd.format(sum2)));
                binding.number3.setText(String.valueOf(dfnd.format(sum3)));
            } else if (binding.editInputMoney.getText().toString().length() == 2) {
                int sum1 = Integer.parseInt(binding.editInputMoney.getText().toString().trim()) * 1000;
                int sum2 = Integer.parseInt(binding.editInputMoney.getText().toString().trim()) * 10000;
                int sum3 = Integer.parseInt(binding.editInputMoney.getText().toString().trim()) * 100000;
                binding.number1.setText(String.valueOf(dfnd.format(sum1)));
                binding.number2.setText(String.valueOf(dfnd.format(sum2)));
                binding.number3.setText(String.valueOf(dfnd.format(sum3)));
            } else if (binding.editInputMoney.getText().toString().length() == 3) {
                int sum1 = Integer.parseInt(binding.editInputMoney.getText().toString().trim()) * 100;
                int sum2 = Integer.parseInt(binding.editInputMoney.getText().toString().trim()) * 1000;
                int sum3 = Integer.parseInt(binding.editInputMoney.getText().toString().trim()) * 10000;
                binding.number1.setText(String.valueOf(dfnd.format(sum1)));
                binding.number2.setText(String.valueOf(dfnd.format(sum2)));
                binding.number3.setText(String.valueOf(dfnd.format(sum3)));
            } else if (binding.editInputMoney.getText().toString().length() == 4) {
                int sum1 = Integer.parseInt(binding.editInputMoney.getText().toString().replaceAll(AppConstant.DOT, "").trim()) * 10;
                int sum2 = Integer.parseInt(binding.editInputMoney.getText().toString().replaceAll(AppConstant.DOT, "").trim()) * 100;
                int sum3 = Integer.parseInt(binding.editInputMoney.getText().toString().replaceAll(AppConstant.DOT, "").trim()) * 1000;
                binding.number1.setText(String.valueOf(dfnd.format(sum1)));
                binding.number2.setText(String.valueOf(dfnd.format(sum2)));
                binding.number3.setText(String.valueOf(dfnd.format(sum3)));
            } else if (binding.editInputMoney.getText().toString().length() == 6) {
                int sum1 = Integer.parseInt(binding.editInputMoney.getText().toString().replaceAll(AppConstant.DOT, "").trim()) * 1;
                int sum2 = Integer.parseInt(binding.editInputMoney.getText().toString().replaceAll(AppConstant.DOT, "").trim()) * 10;
                int sum3 = Integer.parseInt(binding.editInputMoney.getText().toString().replaceAll(AppConstant.DOT, "").trim()) * 100;
                binding.number1.setText(String.valueOf(dfnd.format(sum1)));
                binding.number2.setText(String.valueOf(dfnd.format(sum2)));
                binding.number3.setText(String.valueOf(dfnd.format(sum3)));
            }
            try {
                int inilen, endlen;
                inilen = et.getText().length();
                String v = s.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                Number n = df.parse(v);
                int cp = et.getSelectionStart();
                if (hasFractionalPart) {
                    et.setText(df.format(n));

                    binding.sumPrice.setText(df.format(n) + " ₫");
                    binding.priceAll.setText(df.format(n) + " ₫");
                } else {
                    et.setText(dfnd.format(n));

                    binding.sumPrice.setText(dfnd.format(n) + " ₫");
                    binding.priceAll.setText(dfnd.format(n) + " ₫");
                }
                endlen = et.getText().length();
                int sel = (cp + (endlen - inilen));
                if (sel > 0 && sel <= et.getText().length()) {
                    et.setSelection(sel);
                } else {
                    et.setSelection(et.getText().length() - 1);

                }
            } catch (NumberFormatException nfe) {
            } catch (ParseException e) {
            }

            et.addTextChangedListener(this);
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (count > 0) {
                resetText(true);
            }

            if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator()))) {
                hasFractionalPart = true;
            } else {
                hasFractionalPart = false;
            }
        }
    }

    public static float dpToPx(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }

    private void showDialog() {
        bottomSheetPayment = new BottomSheetPayment(AddMoneyActivity.this, new BottomSheetPayment.CallBack() {
            @Override
            public void onCLickCLose() {
                bottomSheetPayment.dismiss();
            }

            @Override
            public void onClickPayment() {
                bottomSheetPayment.dismiss();
                binding.textPayment.setText("Thẻ VISA (VISA card)");
                checkBtnClick();
            }
        });
        bottomSheetPayment.show();
        bottomSheetPayment.setCanceledOnTouchOutside(false);
    }
}