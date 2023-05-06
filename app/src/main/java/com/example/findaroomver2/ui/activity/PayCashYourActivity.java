package com.example.findaroomver2.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.findaroomver2.R;
import com.example.findaroomver2.constant.AppConstant;
import com.example.findaroomver2.databinding.ActivityPayCashYourBinding;
import com.example.findaroomver2.model.UserClient;
import com.example.findaroomver2.response.money.CashFlowResponse;
import com.example.findaroomver2.ui.adapter.CashFlowAdapter;
import com.example.findaroomver2.ui.customview.toast.CustomToast;
import com.example.findaroomver2.viewmodel.CashPayViewModel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class PayCashYourActivity extends AppCompatActivity {
    private ActivityPayCashYourBinding binding;
    private CashPayViewModel cashPayViewModel;
    private NumberFormat fm = new DecimalFormat("#,###");
    private CashFlowAdapter cashFlowAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPayCashYourBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        initToolbar();
        initData();
    }

    private void initData() {

        String check = getIntent().getStringExtra(AppConstant.CHECK_SUCCESS);
        if (!(check == null)) {
            if (check.equals(AppConstant.CHECK_SUCCESS_ADD_MONEY)) {
                CustomToast.ct(this, "Nạp tiền thành công", CustomToast.LENGTH_SHORT, CustomToast.INFO, false).show();
            }
        }
        cashPayViewModel.getGetPrice().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.sourcePayment.setText(fm.format(integer) + " đ");
            }
        });

        cashPayViewModel.getmProgressMutableData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });

        cashPayViewModel.getListMutableLiveData().observe(this, new Observer<List<CashFlowResponse>>() {
            @Override
            public void onChanged(List<CashFlowResponse> cashFlowResponses) {
                if (cashFlowResponses.size() == 0) {
                    binding.text4.setVisibility(View.VISIBLE);
                    binding.recyclerView.setVisibility(View.GONE);
                } else {
                    binding.text4.setVisibility(View.GONE);
                    cashFlowAdapter.setData(cashFlowResponses);
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(PayCashYourActivity.this, LinearLayoutManager.VERTICAL, false));
                    binding.recyclerView.setAdapter(cashFlowAdapter);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cashPayViewModel.getPricePayCashByUser(UserClient.getInstance().getId());
        cashPayViewModel.getListPayCash(UserClient.getInstance().getId());
    }

    private void initView() {
        cashFlowAdapter = new CashFlowAdapter();
        cashPayViewModel = new ViewModelProvider(this).get(CashPayViewModel.class);
        binding.contentAddMoney.setOnClickListener(v -> {
            startActivity(new Intent(this, AddMoneyActivity.class));
        });
    }

    private void initToolbar() {
        binding.toolBar.setNavigationIcon(R.drawable.ic_back_ios);
        binding.toolBar.setTitle("Ví của bạn");
        binding.toolBar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });
    }
}