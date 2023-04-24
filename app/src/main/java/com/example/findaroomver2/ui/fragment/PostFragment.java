package com.example.findaroomver2.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.findaroomver2.constant.AppConstant;
import com.example.findaroomver2.databinding.FragmentPostBinding;
import com.example.findaroomver2.sharedpreferences.MySharedPreferences;
import com.example.findaroomver2.ui.activity.LoginActivity;
import com.example.findaroomver2.ui.customview.spinner.NiceSpinner;
import com.example.findaroomver2.ui.customview.spinner.OnSpinnerItemSelectedListener;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostFragment extends Fragment {

    private FragmentPostBinding binding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostFragment newInstance(String param1, String param2) {
        PostFragment fragment = new PostFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPostBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {

        String token = MySharedPreferences.getInstance(getActivity()).getString(AppConstant.USER_TOKEN, "");

        if (token != null || !token.equals("")) {
            binding.contentNullLogin.setVisibility(View.GONE);
        } else {
            binding.contentNullLogin.setVisibility(View.VISIBLE);
        }

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        List<String> dataCategoryPost = new LinkedList<>(Arrays.asList("Phòng trọ/Nhà trọ", "Phòng trọ/Nhà trọ", "Nhượng phòng"));
        List<String> dataCty = new LinkedList<>(Arrays.asList("Chọn tỉnh / thành phố"));
        List<String> dataDistrict = new LinkedList<>(Arrays.asList("Chọn quận / huyện"));
        List<String> dataWard = new LinkedList<>(Arrays.asList("Chọn xã / phường"));
        List<String> dataStreet = new LinkedList<>(Arrays.asList("Chọn đường / phố"));


        binding.listCategoryPost.attachDataSource(dataCategoryPost);
        binding.listCategoryCty.attachDataSource(dataCty);
        binding.listCategoryDistrict.attachDataSource(dataDistrict);
        binding.listCategoryWard.attachDataSource(dataWard);
        binding.listStreet.attachDataSource(dataStreet);

        binding.listCategoryPost.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                String item = String.valueOf(parent.getItemAtPosition(position));
            }
        });

    }
}