package com.example.findaroomver2.ui.fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.choseimage.FishBun;
import com.example.choseimage.adapter.image.impl.GlideAdapter;
import com.example.findaroomver2.constant.AppConstant;
import com.example.findaroomver2.databinding.FragmentPostBinding;
import com.example.findaroomver2.sharedpreferences.MySharedPreferences;
import com.example.findaroomver2.ui.activity.LoginActivity;
import com.example.findaroomver2.ui.adapter.autoimage.ImageAutoSliderAdapter;
import com.example.findaroomver2.ui.customview.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.example.findaroomver2.ui.customview.autoimageslider.SliderAnimations;
import com.example.findaroomver2.ui.customview.autoimageslider.SliderView;
import com.example.findaroomver2.ui.customview.spinner.NiceSpinner;
import com.example.findaroomver2.ui.customview.spinner.OnSpinnerItemSelectedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostFragment extends Fragment {
    private ImageAutoSliderAdapter imageAutoSliderAdapter;
    private ActivityResultLauncher<Intent> startForResultCallback = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    path = data.getParcelableArrayListExtra(FishBun.INTENT_PATH);
                    if (path == null) {
                        path = new ArrayList<>();
                    }
                    imageAutoSliderAdapter.changePath(path);
                    if (path.size() > 0) {
                        binding.choseImage.setVisibility(View.GONE);
                        binding.contentImage.setVisibility(View.VISIBLE);
                    } else {
                        binding.choseImage.setVisibility(View.VISIBLE);
                        binding.contentImage.setVisibility(View.GONE);
                    }
//                    for (Uri uri : path) {
//                        MediaManager.get().upload(uri).callback(new UploadCallback() {
//                            @Override
//                            public void onStart(String requestId) {
//                                Log.d(TAG, "onStart " + "started");
//                            }
//
//                            @Override
//                            public void onProgress(String requestId, long bytes, long totalBytes) {
//                                Log.d(TAG, "onStart " + uri);
//                            }
//
//                            // tra ve link img cloudy
//                            @Override
//                            public void onSuccess(String requestId, Map resultData) {
//                                String linkImageAvt = resultData.get("url").toString();
//                                Log.d("MInhLink", "linkImageAvt " + linkImageAvt);
//                            }
//
//                            @Override
//                            public void onError(String requestId, ErrorInfo error) {
//                                Log.d(TAG, "onStart " + error);
//                            }
//
//                            @Override
//                            public void onReschedule(String requestId, ErrorInfo error) {
//                                Log.d(TAG, "onStart " + error);
//                            }
//                        }).dispatch();
//                    }
                }
            }
        }
    });
    private ArrayList<Uri> path = new ArrayList<>();

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

        binding.choseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FishBun.with(getActivity()).setImageAdapter(new GlideAdapter()).setSelectedImages(path).startAlbumWithActivityResultCallback(startForResultCallback);
            }
        });

        imageAutoSliderAdapter = new ImageAutoSliderAdapter(getActivity(), path);
        binding.imageItem.setSliderAdapter(imageAutoSliderAdapter);
        binding.imageItem.setIndicatorAnimation(IndicatorAnimationType.THIN_WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        binding.imageItem.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        binding.imageItem.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        binding.imageItem.setIndicatorSelectedColor(Color.WHITE);
        binding.imageItem.setIndicatorUnselectedColor(Color.GRAY);
        binding.imageItem.setScrollTimeInSec(4); //set scroll delay in seconds :
        binding.imageItem.startAutoCycle();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FishBun.FISHBUN_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                path = data.getParcelableArrayListExtra(FishBun.INTENT_PATH);
                if (path == null) {
                    path = new ArrayList<>();
                }
                imageAutoSliderAdapter.changePath(path);
                if (path.size() > 0) {
                    binding.choseImage.setVisibility(View.GONE);
                    binding.contentImage.setVisibility(View.VISIBLE);
                } else {
                    binding.choseImage.setVisibility(View.VISIBLE);
                    binding.contentImage.setVisibility(View.GONE);
                }
            }
        }
    }
}