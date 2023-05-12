package com.example.findaroomver2.ui.activity;

import static org.greenrobot.eventbus.EventBus.TAG;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.choseimage.FishBun;
import com.example.choseimage.adapter.image.impl.GlideAdapter;
import com.example.findaroomver2.R;
import com.example.findaroomver2.constant.AppConstant;
import com.example.findaroomver2.databinding.ActivityEditPostBinding;
import com.example.findaroomver2.model.Post;
import com.example.findaroomver2.model.UserClient;
import com.example.findaroomver2.response.post.PostResponse;
import com.example.findaroomver2.response.supplement.DataSupplement;
import com.example.findaroomver2.response.supplement.Supplement;
import com.example.findaroomver2.ui.adapter.SupplementAdapter;
import com.example.findaroomver2.ui.customview.spinner.NiceSpinner;
import com.example.findaroomver2.ui.customview.spinner.OnSpinnerItemSelectedListener;
import com.example.findaroomver2.ui.customview.toast.CustomToast;
import com.example.findaroomver2.viewmodel.EditPostViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EditPostActivity extends AppCompatActivity implements SupplementAdapter.OnItemClickListener {

    private ActivityEditPostBinding binding;
    private EditPostViewModel editPostViewModel;
    private String idPost = "";
    private String nameCategory = "Phòng trọ/Nhà trọ";
    private RequestOptions options;
    private SupplementAdapter supplementAdapter;
    private boolean isCheckAds = false;
    private List<String> listImages;
    private ArrayList<Uri> path;
    private List<Supplement> supplementList;

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
                                binding.checkImage4.setBackgroundResource(R.drawable.gradient_bg);
                            } else {
                                binding.countImage.setVisibility(View.GONE);
                                binding.checkImage4.setBackground(null);
                            }
                        }
                        listImages.clear();
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
                                    listImages.add(linkImage);
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
                        binding.contentImage1.setVisibility(View.GONE);
                        binding.contentImage2.setVisibility(View.GONE);
                        binding.contentImage3.setVisibility(View.GONE);
                        binding.contentImage4.setVisibility(View.GONE);
                    }

                }
            }
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initToolBar();
        initView();
        initData();
    }

    private void initData() {
        idPost = getIntent().getStringExtra(AppConstant.ID_POST);
        editPostViewModel.getPostById(idPost);
        editPostViewModel.getProgress().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });
        editPostViewModel.getPostResponseMutableLiveData().observe(this, new Observer<PostResponse>() {
            @Override
            public void onChanged(PostResponse postResponse) {
                editPostViewModel.getListSupplement();
                listImages = postResponse.getData().getImages();
                supplementList = postResponse.getData().getSupplements();
                if (postResponse.getData().getImages().size() == 1) {
                    Glide.with(binding.contentImage1.getContext()).load(postResponse.getData().getImages().get(0)).apply(options).into(binding.contentImage1);
                    binding.contentImage1.setVisibility(View.VISIBLE);
                    binding.contentImage2.setVisibility(View.GONE);
                    binding.contentImage3.setVisibility(View.GONE);
                    binding.contentImage4.setVisibility(View.GONE);
                } else if (postResponse.getData().getImages().size() == 2) {
                    Glide.with(binding.contentImage2Image1.getContext()).load(postResponse.getData().getImages().get(0)).apply(options).into(binding.contentImage2Image1);
                    Glide.with(binding.contentImage2Image2.getContext()).load(postResponse.getData().getImages().get(1)).apply(options).into(binding.contentImage2Image2);

                    binding.contentImage1.setVisibility(View.GONE);
                    binding.contentImage2.setVisibility(View.VISIBLE);
                    binding.contentImage3.setVisibility(View.GONE);
                    binding.contentImage4.setVisibility(View.GONE);
                } else if (postResponse.getData().getImages().size() == 3) {
                    Glide.with(binding.contentImage3Image1.getContext()).load(postResponse.getData().getImages().get(0)).apply(options).into(binding.contentImage3Image1);
                    Glide.with(binding.contentImage3Image2.getContext()).load(postResponse.getData().getImages().get(1)).apply(options).into(binding.contentImage3Image2);
                    Glide.with(binding.contentImage3Image3.getContext()).load(postResponse.getData().getImages().get(2)).apply(options).into(binding.contentImage3Image3);

                    binding.contentImage1.setVisibility(View.GONE);
                    binding.contentImage2.setVisibility(View.GONE);
                    binding.contentImage3.setVisibility(View.VISIBLE);
                    binding.contentImage4.setVisibility(View.GONE);
                } else {

                    Glide.with(binding.contentImage4Image1.getContext()).load(postResponse.getData().getImages().get(0)).apply(options).into(binding.contentImage4Image1);
                    Glide.with(binding.contentImage4Image2.getContext()).load(postResponse.getData().getImages().get(1)).apply(options).into(binding.contentImage4Image2);
                    Glide.with(binding.contentImage4Image3.getContext()).load(postResponse.getData().getImages().get(2)).apply(options).into(binding.contentImage4Image3);
                    Glide.with(binding.contentImage4Image4.getContext()).load(postResponse.getData().getImages().get(3)).apply(options).into(binding.contentImage4Image4);

                    if (postResponse.getData().getImages().size() > 4) {
                        binding.countImage.setVisibility(View.VISIBLE);
                        binding.countImage.setText("+" + (postResponse.getData().getImages().size() - 4));
                        binding.checkImage4.setBackgroundResource(R.drawable.gradient_bg);
                    } else {
                        binding.countImage.setVisibility(View.GONE);
                        binding.checkImage4.setBackground(null);
                    }

                    binding.contentImage1.setVisibility(View.GONE);
                    binding.contentImage2.setVisibility(View.GONE);
                    binding.contentImage3.setVisibility(View.GONE);
                    binding.contentImage4.setVisibility(View.VISIBLE);
                }
                binding.title.setText(postResponse.getData().getTitle());
                binding.cty.setText(postResponse.getData().getCty());
                binding.district.setText(postResponse.getData().getDistrict());
                binding.street.setText(postResponse.getData().getStreet());
                binding.wards.setText(postResponse.getData().getWards());
                binding.address.setText(postResponse.getData().getAddress());
                binding.acreage.setText(postResponse.getData().getAcreage() + "");
                binding.depositMoney.setText(postResponse.getData().getDeposit() + "");
                binding.bedroom.setText(postResponse.getData().getBedroom() + "");
                binding.bathroom.setText(postResponse.getData().getBathroom() + "");
                binding.countPerson.setText(postResponse.getData().getCountPerson() + "");
                binding.startDay.setText(postResponse.getData().getStartDate() + "");
                binding.price.setText(postResponse.getData().getPrice() + "");
                binding.electricityPrice.setText(postResponse.getData().getElectricityPrice() + "");
                binding.waterPrice.setText(postResponse.getData().getWaterPrice() + "");
                binding.wifiPrice.setText(postResponse.getData().getWifi() + "");
                binding.textMore.setText(postResponse.getData().getDescribe());
                binding.phone.setText(postResponse.getData().getPhone());

            }
        });

        editPostViewModel.getDataSupplementMutableLiveData().observe(this, new Observer<DataSupplement>() {
            @Override
            public void onChanged(DataSupplement dataSupplement) {
                supplementAdapter.setData(dataSupplement.getData());
                supplementAdapter.setDataChecker(supplementList);
                binding.listUtilities.setAdapter(supplementAdapter);
            }
        });

        editPostViewModel.getPostEdit().observe(this, new Observer<PostResponse>() {
            @Override
            public void onChanged(PostResponse postResponse) {
                if (postResponse.getMessage().isStatus()) {
                    Toast.makeText(EditPostActivity.this, postResponse.getMessage().getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditPostActivity.this, postResponse.getMessage().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initToolBar() {
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        initConfig();
        path = new ArrayList<>();
        listImages = new ArrayList<>();
        supplementList = new ArrayList<>();
        editPostViewModel = new ViewModelProvider(this).get(EditPostViewModel.class);
        supplementAdapter = new SupplementAdapter();
        supplementAdapter.setOnItemClickListener(this);
        options = new RequestOptions().centerCrop().placeholder(R.drawable.noimage).error(R.drawable.noimage);
        List<String> dataCategoryPost = new LinkedList<>(Arrays.asList("Phòng trọ/Nhà trọ", "Phòng trọ/Nhà trọ", "Nhượng phòng"));
        binding.listCategoryPost.attachDataSource(dataCategoryPost);
        binding.listUtilities.setLayoutManager(new GridLayoutManager(this, 2));
        binding.listCategoryPost.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                nameCategory = String.valueOf(parent.getItemAtPosition(position));
            }
        });

        binding.contentImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FishBun.with(EditPostActivity.this).setImageAdapter(new GlideAdapter()).setSelectedImages(path).startAlbumWithActivityResultCallback(startForResultCallback);
            }
        });
        binding.contentImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FishBun.with(EditPostActivity.this).setImageAdapter(new GlideAdapter()).setSelectedImages(path).startAlbumWithActivityResultCallback(startForResultCallback);
            }
        });
        binding.contentImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FishBun.with(EditPostActivity.this).setImageAdapter(new GlideAdapter()).setSelectedImages(path).startAlbumWithActivityResultCallback(startForResultCallback);
            }
        });
        binding.contentImage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FishBun.with(EditPostActivity.this).setImageAdapter(new GlideAdapter()).setSelectedImages(path).startAlbumWithActivityResultCallback(startForResultCallback);
            }
        });

        binding.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.title.getText().toString().length() > 0 &&
                        binding.cty.getText().toString().length() > 0 &&
                        binding.district.getText().toString().length() > 0 &&
                        binding.street.getText().toString().length() > 0 &&
                        binding.wards.getText().toString().length() > 0 &&
                        binding.address.getText().toString().length() > 0 &&
                        binding.acreage.getText().toString().length() > 0 &&
                        binding.depositMoney.getText().toString().length() > 0 &&
                        binding.bedroom.getText().toString().length() > 0 &&
                        binding.bathroom.getText().toString().length() > 0 &&
                        listImages.size() > 0 &&
                        binding.startDay.getText().toString().length() > 0 &&
                        binding.price.getText().toString().length() > 0 &&
                        binding.electricityPrice.getText().toString().length() > 0 &&
                        binding.waterPrice.getText().toString().length() > 0 &&
                        binding.wifiPrice.getText().toString().length() > 0 &&
                        binding.textMore.getText().toString().length() > 0 &&
                        binding.phone.getText().toString().length() > 0 &&
                        binding.phone.getText().toString().length() > 0 &&
                        supplementList.size() > 0) {
                    editPostViewModel.editPost(new Post(UserClient.getInstance().getId(), nameCategory, binding.title.getText().toString(), listImages, binding.cty.getText().toString(), binding.district.getText().toString(), binding.wards.getText().toString(), binding.street.getText().toString(), binding.address.getText().toString(), Integer.parseInt(binding.acreage.getText().toString()), Integer.parseInt(binding.depositMoney.getText().toString().replace(AppConstant.DOT, "")), Integer.parseInt(binding.bedroom.getText().toString()), Integer.parseInt(binding.bathroom.getText().toString()), Integer.parseInt(binding.countPerson.getText().toString()), binding.startDay.getText().toString(), Integer.parseInt(binding.price.getText().toString().replace(AppConstant.DOT, "")), Integer.parseInt(binding.electricityPrice.getText().toString().replace(AppConstant.DOT, "")), Integer.parseInt(binding.waterPrice.getText().toString().replace(AppConstant.DOT, "")), Integer.parseInt(binding.wifiPrice.getText().toString().replace(AppConstant.DOT, "")), binding.textMore.getText().toString(), binding.phone.getText().toString(), supplementList), idPost);
                } else {
                    CustomToast.ct(EditPostActivity.this, "Các trường không được để trống", CustomToast.LENGTH_SHORT, CustomToast.INFO, true).show();
                }
            }
        });

        binding.startDay.setFocusable(false);
        binding.startDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int d = calendar.get(Calendar.DAY_OF_MONTH);
                int m = calendar.get(Calendar.MONTH);
                int y = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditPostActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        final String day = dayOfMonth + "/" + (month + 1) + "/" + year;
                        binding.startDay.setText(day);
                    }
                }, y, m, d);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });
    }

    @Override
    public void onItemSelected(List<Supplement> selectedItems) {
        supplementList = selectedItems;
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

                    listImages.clear();
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
                                listImages.add(linkImage);
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
                    binding.contentImage1.setVisibility(View.GONE);
                    binding.contentImage2.setVisibility(View.GONE);
                    binding.contentImage3.setVisibility(View.GONE);
                    binding.contentImage4.setVisibility(View.GONE);
                }
            }
        }
    }

    private void initConfig() {
        try {
            Map config = new HashMap();
            config.put("cloud_name", "dl4lo9r1y");
            config.put("api_key", "477111637363519");
            config.put("api_secret", "q4gJ0kJOSRSDHKujijgYaSMhfJY");
            MediaManager.init(EditPostActivity.this, config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}