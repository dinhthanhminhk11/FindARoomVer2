package com.example.findaroomver2.ui.fragment;

import static android.app.Activity.RESULT_OK;
import static org.greenrobot.eventbus.EventBus.TAG;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.choseimage.FishBun;
import com.example.choseimage.adapter.image.impl.GlideAdapter;
import com.example.findaroomver2.R;
import com.example.findaroomver2.constant.AppConstant;
import com.example.findaroomver2.constant.NotificationCenter;
import com.example.findaroomver2.databinding.FragmentPostBinding;
import com.example.findaroomver2.event.KeyEvent;
import com.example.findaroomver2.model.Post;
import com.example.findaroomver2.model.UserClient;
import com.example.findaroomver2.response.post.PostResponse;
import com.example.findaroomver2.response.supplement.DataSupplement;
import com.example.findaroomver2.response.supplement.Supplement;
import com.example.findaroomver2.sharedpreferences.MySharedPreferences;
import com.example.findaroomver2.ui.activity.LoginActivity;
import com.example.findaroomver2.ui.adapter.SupplementAdapter;
import com.example.findaroomver2.ui.adapter.autoimage.ImageAutoSliderAdapter;
import com.example.findaroomver2.ui.customview.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.example.findaroomver2.ui.customview.autoimageslider.SliderAnimations;
import com.example.findaroomver2.ui.customview.autoimageslider.SliderView;
import com.example.findaroomver2.ui.customview.spinner.NiceSpinner;
import com.example.findaroomver2.ui.customview.spinner.OnSpinnerItemSelectedListener;
import com.example.findaroomver2.ui.customview.toast.CustomToast;
import com.example.findaroomver2.viewmodel.PostViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class PostFragment extends Fragment implements SupplementAdapter.OnItemClickListener {
    private PostViewModel postViewModel;
    private SupplementAdapter supplementAdapter;
    private ImageAutoSliderAdapter imageAutoSliderAdapter;
    private String token = "";
    private List<Supplement> supplements;
    private List<String> images;
    private String nameCategory = "Phòng trọ/Nhà trọ";

    private ImageButton closeImgBtn;
    private Button confirm;
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
                    if (path.size() > 0) {
                        binding.choseImage.setVisibility(View.GONE);
                        if (path.size() == 1) {
                            binding.contentImage1.setImageURI(path.get(0));
                            binding.contentImage1.setVisibility(View.VISIBLE);
                            binding.contentImage2.setVisibility(View.GONE);
                            binding.contentImage3.setVisibility(View.GONE);
                            binding.contentImage4.setVisibility(View.GONE);
                        } else if (path.size() == 2) {
                            binding.contentImage2Image1.setImageURI(path.get(0));
                            binding.contentImage2Image2.setImageURI(path.get(1));

                            binding.contentImage1.setVisibility(View.GONE);
                            binding.contentImage2.setVisibility(View.VISIBLE);
                            binding.contentImage3.setVisibility(View.GONE);
                            binding.contentImage4.setVisibility(View.GONE);
                        } else if (path.size() == 3) {
                            binding.contentImage3Image1.setImageURI(path.get(0));
                            binding.contentImage3Image2.setImageURI(path.get(1));
                            binding.contentImage3Image3.setImageURI(path.get(2));


                            binding.contentImage1.setVisibility(View.GONE);
                            binding.contentImage2.setVisibility(View.GONE);
                            binding.contentImage3.setVisibility(View.VISIBLE);
                            binding.contentImage4.setVisibility(View.GONE);
                        } else {
                            binding.contentImage4Image1.setImageURI(path.get(0));
                            binding.contentImage4Image2.setImageURI(path.get(1));
                            binding.contentImage4Image3.setImageURI(path.get(2));
                            binding.contentImage4Image4.setImageURI(path.get(3));

                            binding.contentImage1.setVisibility(View.GONE);
                            binding.contentImage2.setVisibility(View.GONE);
                            binding.contentImage3.setVisibility(View.GONE);
                            binding.contentImage4.setVisibility(View.VISIBLE);
                            if (path.size() > 4) {
                                binding.countImage.setVisibility(View.VISIBLE);
                                binding.countImage.setText("+" + (path.size() - 4));
                            } else {
                                binding.countImage.setVisibility(View.GONE);
                            }
                        }
                        images = new ArrayList<>();
                        for (Uri uri : path) {
                            MediaManager.get().upload(uri).callback(new UploadCallback() {
                                @Override
                                public void onStart(String requestId) {
                                    Log.d(TAG, "onStart " + "started");
                                }

                                @Override
                                public void onProgress(String requestId, long bytes, long totalBytes) {
                                    Log.d(TAG, "onProgress " + uri);
                                }

                                @Override
                                public void onSuccess(String requestId, Map resultData) {
                                    String linkImage = resultData.get("url").toString();
                                    Log.e(TAG, linkImage);
                                    images.add(linkImage);
                                }

                                @Override
                                public void onError(String requestId, ErrorInfo error) {
                                    Log.d(TAG, "onError " + error);
                                }

                                @Override
                                public void onReschedule(String requestId, ErrorInfo error) {
                                    Log.d(TAG, "onReschedule " + error);
                                }
                            }).dispatch();
                        }

                    } else {
                        binding.choseImage.setVisibility(View.VISIBLE);
                        binding.contentImage1.setVisibility(View.GONE);
                        binding.contentImage2.setVisibility(View.GONE);
                        binding.contentImage3.setVisibility(View.GONE);
                        binding.contentImage4.setVisibility(View.GONE);
                    }

                }
            }
        }
    });
    private ArrayList<Uri> path;
    private FragmentPostBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public PostFragment() {
    }

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
        initConfig();
        supplements = new ArrayList<>();
        path = new ArrayList<>();
        postViewModel = new ViewModelProvider(getActivity()).get(PostViewModel.class);

        token = MySharedPreferences.getInstance(getActivity()).getString(AppConstant.USER_TOKEN, "");

        if (!token.equals("")) {
            binding.contentNullLogin.setVisibility(View.GONE);
            binding.conentNotNull.setVisibility(View.VISIBLE);
        } else {
            binding.contentNullLogin.setVisibility(View.VISIBLE);
            binding.conentNotNull.setVisibility(View.GONE);
        }

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        List<String> dataCategoryPost = new LinkedList<>(Arrays.asList("Phòng trọ/Nhà trọ", "Phòng trọ/Nhà trọ", "Nhượng phòng"));

        binding.listCategoryPost.attachDataSource(dataCategoryPost);

        binding.listCategoryPost.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                nameCategory = String.valueOf(parent.getItemAtPosition(position));
            }
        });

        binding.choseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FishBun.with(getActivity()).setImageAdapter(new GlideAdapter()).setMaxCount(10).setSelectedImages(path).hasCameraInPickerPage(true).startAlbumWithActivityResultCallback(startForResultCallback);
            }
        });

        binding.contentImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FishBun.with(getActivity()).setImageAdapter(new GlideAdapter()).setSelectedImages(path).startAlbumWithActivityResultCallback(startForResultCallback);
            }
        });
        binding.contentImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FishBun.with(getActivity()).setImageAdapter(new GlideAdapter()).setSelectedImages(path).startAlbumWithActivityResultCallback(startForResultCallback);
            }
        });
        binding.contentImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FishBun.with(getActivity()).setImageAdapter(new GlideAdapter()).setSelectedImages(path).startAlbumWithActivityResultCallback(startForResultCallback);
            }
        });
        binding.contentImage4.setOnClickListener(new View.OnClickListener() {
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

        postViewModel.getListSupplement();
        postViewModel.getDataSupplementMutableLiveData().observe(getActivity(), new Observer<DataSupplement>() {
            @Override
            public void onChanged(DataSupplement dataSupplement) {
                supplementAdapter = new SupplementAdapter(dataSupplement.getData());
                binding.listUtilities.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                binding.listUtilities.setAdapter(supplementAdapter);
                supplementAdapter.setOnItemClickListener(PostFragment.this);
            }
        });

        binding.depositMoney.addTextChangedListener(new NumberTextWatcher(binding.depositMoney));
        binding.price.addTextChangedListener(new NumberTextWatcher(binding.price));
        binding.electricityPrice.addTextChangedListener(new NumberTextWatcher(binding.electricityPrice));
        binding.waterPrice.addTextChangedListener(new NumberTextWatcher(binding.waterPrice));
        binding.wifiPrice.addTextChangedListener(new NumberTextWatcher(binding.wifiPrice));
        binding.depositMoney.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                v.performClick();
                if (binding.depositMoney.getCompoundDrawables()[DRAWABLE_RIGHT] == null)
                    return false;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (binding.depositMoney.getRight() - binding.depositMoney.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        return true;
                    }
                }
                return false;
            }
        });
        binding.price.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                v.performClick();
                if (binding.depositMoney.getCompoundDrawables()[DRAWABLE_RIGHT] == null)
                    return false;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (binding.depositMoney.getRight() - binding.depositMoney.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        return true;
                    }
                }
                return false;
            }
        });
        binding.electricityPrice.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                v.performClick();
                if (binding.depositMoney.getCompoundDrawables()[DRAWABLE_RIGHT] == null)
                    return false;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (binding.depositMoney.getRight() - binding.depositMoney.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        return true;
                    }
                }
                return false;
            }
        });
        binding.waterPrice.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                v.performClick();
                if (binding.depositMoney.getCompoundDrawables()[DRAWABLE_RIGHT] == null)
                    return false;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (binding.depositMoney.getRight() - binding.depositMoney.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        return true;
                    }
                }
                return false;
            }
        });
        binding.wifiPrice.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                v.performClick();
                if (binding.depositMoney.getCompoundDrawables()[DRAWABLE_RIGHT] == null)
                    return false;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (binding.depositMoney.getRight() - binding.depositMoney.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        return true;
                    }
                }
                return false;
            }
        });

        binding.btnCreate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View view) {
                if (binding.title.getText().toString().length() > 0 && binding.cty.getText().toString().length() > 0 && binding.district.getText().toString().length() > 0 && binding.street.getText().toString().length() > 0 && binding.wards.getText().toString().length() > 0 && binding.address.getText().toString().length() > 0 && binding.acreage.getText().toString().length() > 0 && binding.depositMoney.getText().toString().length() > 0 && binding.bedroom.getText().toString().length() > 0 && binding.bathroom.getText().toString().length() > 0 && binding.countImage.getText().toString().length() > 0 && binding.startDay.getText().toString().length() > 0 && binding.price.getText().toString().length() > 0 && binding.electricityPrice.getText().toString().length() > 0 && binding.waterPrice.getText().toString().length() > 0 && binding.wifiPrice.getText().toString().length() > 0 && binding.textMore.getText().toString().length() > 0 && binding.phone.getText().toString().length() > 0 && binding.phone.getText().toString().length() > 0 && path.size() > 0 && supplements.size() > 0) {
                    postViewModel.createPost(new Post(UserClient.getInstance().getId(), nameCategory, binding.title.getText().toString(), images, binding.cty.getText().toString(), binding.district.getText().toString(), binding.wards.getText().toString(), binding.street.getText().toString(), binding.address.getText().toString(), Integer.parseInt(binding.acreage.getText().toString()), Integer.parseInt(binding.depositMoney.getText().toString().replace(".", "")), Integer.parseInt(binding.bedroom.getText().toString()), Integer.parseInt(binding.bathroom.getText().toString()), Integer.parseInt(binding.countPerson.getText().toString()), binding.startDay.getText().toString(), Integer.parseInt(binding.price.getText().toString().replace(".", "")), Integer.parseInt(binding.electricityPrice.getText().toString().replace(".", "")), Integer.parseInt(binding.waterPrice.getText().toString().replace(".", "")), Integer.parseInt(binding.wifiPrice.getText().toString().replace(".", "")), binding.textMore.getText().toString(), binding.phone.getText().toString(), supplements));
                } else {
                    CustomToast.ct(getActivity(), "Các trường không được để trống", CustomToast.LENGTH_SHORT, CustomToast.INFO, true).show();
                }
            }
        });

        postViewModel.getProgress().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });

        postViewModel.getPostResponseMutableLiveData().observe(getActivity(), new Observer<PostResponse>() {
            @Override
            public void onChanged(PostResponse postResponse) {
                if (postResponse.getMessage().isStatus()) {
                    initdialogSuccess();
                } else {
                    initdialogFailed();
                }
            }
        });
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
                if (path.size() > 0) {
                    binding.choseImage.setVisibility(View.GONE);
                    if (path.size() == 1) {
                        binding.contentImage1.setImageURI(path.get(0));
                        binding.contentImage1.setVisibility(View.VISIBLE);
                        binding.contentImage2.setVisibility(View.GONE);
                        binding.contentImage3.setVisibility(View.GONE);
                        binding.contentImage4.setVisibility(View.GONE);
                    } else if (path.size() == 2) {
                        binding.contentImage2Image1.setImageURI(path.get(0));
                        binding.contentImage2Image2.setImageURI(path.get(1));

                        binding.contentImage1.setVisibility(View.GONE);
                        binding.contentImage2.setVisibility(View.VISIBLE);
                        binding.contentImage3.setVisibility(View.GONE);
                        binding.contentImage4.setVisibility(View.GONE);
                    } else if (path.size() == 3) {
                        binding.contentImage3Image1.setImageURI(path.get(0));
                        binding.contentImage3Image2.setImageURI(path.get(1));
                        binding.contentImage3Image3.setImageURI(path.get(2));


                        binding.contentImage1.setVisibility(View.GONE);
                        binding.contentImage2.setVisibility(View.GONE);
                        binding.contentImage3.setVisibility(View.VISIBLE);
                        binding.contentImage4.setVisibility(View.GONE);
                    } else {
                        binding.contentImage4Image1.setImageURI(path.get(0));
                        binding.contentImage4Image2.setImageURI(path.get(1));
                        binding.contentImage4Image3.setImageURI(path.get(2));
                        binding.contentImage4Image4.setImageURI(path.get(3));

                        binding.contentImage1.setVisibility(View.GONE);
                        binding.contentImage2.setVisibility(View.GONE);
                        binding.contentImage3.setVisibility(View.GONE);
                        binding.contentImage4.setVisibility(View.VISIBLE);
                    }

                    images = new ArrayList<>();
                    for (Uri uri : path) {
                        MediaManager.get().upload(uri).callback(new UploadCallback() {
                            @Override
                            public void onStart(String requestId) {
                                Log.d(TAG, "onStart " + "started");
                            }

                            @Override
                            public void onProgress(String requestId, long bytes, long totalBytes) {
                                Log.d(TAG, "onProgress " + uri);
                            }

                            @Override
                            public void onSuccess(String requestId, Map resultData) {
                                String linkImage = resultData.get("url").toString();
                                Log.e(TAG, linkImage);
                                images.add(linkImage);
                            }

                            @Override
                            public void onError(String requestId, ErrorInfo error) {
                                Log.d(TAG, "onError " + error);
                            }

                            @Override
                            public void onReschedule(String requestId, ErrorInfo error) {
                                Log.d(TAG, "onReschedule " + error);
                            }
                        }).dispatch();
                    }

                } else {
                    binding.choseImage.setVisibility(View.VISIBLE);
                    binding.contentImage1.setVisibility(View.GONE);
                    binding.contentImage2.setVisibility(View.GONE);
                    binding.contentImage3.setVisibility(View.GONE);
                    binding.contentImage4.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void onItemSelected(List<Supplement> selectedItems) {
        supplements = selectedItems;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(KeyEvent event) {
        if (event.getIdEven() == NotificationCenter.checkLogin) {
            token = MySharedPreferences.getInstance(getActivity()).getString(AppConstant.USER_TOKEN, "");
            if (!token.equals("")) {
                binding.contentNullLogin.setVisibility(View.GONE);
                binding.conentNotNull.setVisibility(View.VISIBLE);
            } else {
                binding.contentNullLogin.setVisibility(View.VISIBLE);
                binding.conentNotNull.setVisibility(View.GONE);
            }
        }
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
            try {
                int inilen, endlen;
                inilen = et.getText().length();
                String v = s.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                Number n = df.parse(v);
                int cp = et.getSelectionStart();
                if (hasFractionalPart) {
                    et.setText(df.format(n));
                } else {
                    et.setText(dfnd.format(n));
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
            if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator()))) {
                hasFractionalPart = true;
            } else {
                hasFractionalPart = false;
            }
        }
    }

    private void initConfig() {
        try {
            Map config = new HashMap();
            config.put("cloud_name", "dl4lo9r1y");
            config.put("api_key", "477111637363519");
            config.put("api_secret", "q4gJ0kJOSRSDHKujijgYaSMhfJY");
            MediaManager.init(getActivity(), config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initdialogSuccess() {
        final Dialog dialogSuccess = new Dialog(getActivity());
        dialogSuccess.setCancelable(false);
        dialogSuccess.setContentView(R.layout.dialog_success);
        Window window2 = dialogSuccess.getWindow();
        window2.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialogSuccess != null && dialogSuccess.getWindow() != null) {
            dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        closeImgBtn = (ImageButton) dialogSuccess.findViewById(R.id.close_img_btn);
        confirm = (Button) dialogSuccess.findViewById(R.id.confirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().postSticky(new KeyEvent(NotificationCenter.create_post_success));
                dialogSuccess.dismiss();
            }
        });

        closeImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().postSticky(new KeyEvent(NotificationCenter.create_post_success));
                dialogSuccess.dismiss();
            }
        });

        dialogSuccess.show();
    }

    private ImageButton closeImgBtnFailed;
    private Button failed;

    private void initdialogFailed() {
        final Dialog dialogFailed = new Dialog(getActivity());
        dialogFailed.setCancelable(false);
        dialogFailed.setContentView(R.layout.dialog_fail);
        Window window2 = dialogFailed.getWindow();
        window2.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialogFailed != null && dialogFailed.getWindow() != null) {
            dialogFailed.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        closeImgBtnFailed = (ImageButton) dialogFailed.findViewById(R.id.close_img_btn);
        failed = (Button) dialogFailed.findViewById(R.id.failed);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFailed.dismiss();
            }
        });

        closeImgBtnFailed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFailed.dismiss();
            }
        });

        dialogFailed.show();
    }

}