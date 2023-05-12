package com.example.findaroomver2.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.findaroomver2.R;
import com.example.findaroomver2.databinding.ActivityEditProfileBinding;
import com.example.findaroomver2.model.UserClient;
import com.example.findaroomver2.request.changeInfo.UserEditProfileRequest;
import com.example.findaroomver2.request.login.Data;
import com.example.findaroomver2.response.updateUser.UserUpdateResponse;
import com.example.findaroomver2.ui.customview.toast.CustomToast;
import com.example.findaroomver2.viewmodel.EditProfileViewModel;
import com.example.libraryimagepicker.ImagePicker;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {
    private ActivityEditProfileBinding binding;
    public static final int CAMERA_PERMISSION_REQ = 100;
    private static final String TAG = "Upload ###";
    private String linkImageAvt = "";
    private Uri imagePath;
    private EditProfileViewModel editProfileViewModel;
    private Dialog dialog;
    private Location locationYouSelf;
    private String nameLocationYourSelf;
    private static final int REQUES_PERMISSION_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initToolBar();
        initConfig();
        intView();
        initData();
    }

    private void initToolBar() {
        binding.toolBar.setTitle("Chỉnh sửa thông tin");
        binding.toolBar.setNavigationIcon(R.drawable.ic_back_ios);
        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.toolBar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void intView() {
        binding.phoneEditProfile.setFocusable(false);
        editProfileViewModel = new ViewModelProvider(this).get(EditProfileViewModel.class);
        binding.cameraEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intit();
                ImagePicker.with(EditProfileActivity.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start(20);
            }
        });

        binding.saveEditProfile.setOnClickListener(v -> {
//            progressBarEdiProfile.setVisibility(View.VISIBLE);
            if (imagePath == null) {
                onBackPressed();
                return;
            }

            MediaManager.get().upload(imagePath).callback(new UploadCallback() {
                @Override
                public void onStart(String requestId) {
                    Log.d(TAG, "onStart " + "started");
                }

                @Override
                public void onProgress(String requestId, long bytes, long totalBytes) {
                    Log.d(TAG, "onStart " + imagePath);
                }

                // tra ve link img cloudy
                @Override
                public void onSuccess(String requestId, Map resultData) {
                    linkImageAvt = resultData.get("url").toString();
                    binding.progressBar.setVisibility(View.GONE);
                    editProfileViewModel.updateUserInFo(new UserEditProfileRequest(
                            UserClient.getInstance().getId(),
                            binding.nameEditProfile.getText().toString(),
                            linkImageAvt,
                            binding.cccdEditProfile.getText().toString(),
                            binding.locationEditProfile.getText().toString()
                    ));
                }

                @Override
                public void onError(String requestId, ErrorInfo error) {
                    Log.d(TAG, "onStart " + error);
                }

                @Override
                public void onReschedule(String requestId, ErrorInfo error) {
                    Log.d(TAG, "onStart " + error);
                }
            }).dispatch();
        });

        binding.locationEditProfile.setOnClickListener(view -> {
//            nhỏ hơn android 6 ( ver23) , không cần xin quyền
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                return;
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
                requestPermissions(permissions, REQUES_PERMISSION_CODE);
            }
        });


    }

    private void initData() {
        editProfileViewModel.getUserById(UserClient.getInstance().getId());

        editProfileViewModel.getDataUser().observe(this, new Observer<Data>() {
            @Override
            public void onChanged(Data data) {
                RequestOptions optionsUser = new RequestOptions().centerCrop().placeholder(R.drawable.noavatar).error(R.drawable.noavatar);
                Glide.with(EditProfileActivity.this).load(data.getImage()).apply(optionsUser).into(binding.avtEditProfile);
                linkImageAvt = data.getImage();
                binding.nameEditProfile.setText(UserClient.getInstance().getFullName());
                binding.titleEmailEditProfile.setText(UserClient.getInstance().getEmail());
                binding.locationEditProfile.setText(data.getAddress());
                binding.cccdEditProfile.setText(data.getPersonId());
                binding.phoneEditProfile.setText(data.getPhone());
                binding.titleNameEditProfile.setText(data.getFullName());
            }
        });

        editProfileViewModel.getUserUpdateResponseMutableLiveData().observe(this, new Observer<UserUpdateResponse>() {
            @Override
            public void onChanged(UserUpdateResponse userUpdateResponse) {
                if (userUpdateResponse.getMessage().isStatus()) {
                    UserClient userClient = UserClient.getInstance();
                    userClient.setFullName(userUpdateResponse.getData().getFullName());
                    userClient.setImage(userUpdateResponse.getData().getImage());
                    CustomToast.ct(EditProfileActivity.this, userUpdateResponse.getMessage().getMessage(), CustomToast.LENGTH_SHORT, CustomToast.INFO, false).show();
                    finish();
                } else {
                    CustomToast.ct(EditProfileActivity.this, userUpdateResponse.getMessage().getMessage(), CustomToast.LENGTH_SHORT, CustomToast.INFO, false).show();
                }
            }
        });

        editProfileViewModel.getmProgressMutableData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });
    }

    private void intit() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {

        } else {
            handlePermission();
        }
    }

    private void handlePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

        } else {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQ);
        }
    }

    private static void openAppSetting(final Activity context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }

    private void showSettingsAlert() {
        dialog = new Dialog(EditProfileActivity.this);
        dialog.setContentView(R.layout.permission_camera_editprofile_dialog);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background_dialog_editprofile));
        }
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        Button btnSetting = dialog.findViewById(R.id.btnSettingDialogEditProfile);
        Button btnCancel = dialog.findViewById(R.id.btnCancelDialogEditProfile);
        btnSetting.setOnClickListener(view -> {
            dialog.dismiss();
            openAppSetting(EditProfileActivity.this);
        });

        btnCancel.setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    private void getCurrentLocation() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.getFusedLocationProviderClient(EditProfileActivity.this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {

                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(getApplicationContext())
                                .removeLocationUpdates(this);
                        if (locationResult != null && locationResult.getLocations().size() > 0) {
                            int latestlocIndex = locationResult.getLocations().size() - 1;
                            double lati = locationResult.getLocations().get(latestlocIndex).getLatitude();
                            double longi = locationResult.getLocations().get(latestlocIndex).getLongitude();
//                            progressBarEdiProfile.setVisibility(View.GONE);
                            locationYouSelf = new Location("LocationYouSef");
                            locationYouSelf.setLongitude(longi);
                            locationYouSelf.setLatitude(lati);
                            getAddress(lati, longi);
                        } else {
//                            progressBarEdiProfile.setVisibility(View.GONE);
                        }
                    }
                }, Looper.getMainLooper());
    }

    private void getAddress(double lati, double longi) {
        Geocoder geocoder = new Geocoder(EditProfileActivity.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lati, longi, 1);
            Address obj = addresses.get(0);
            String add = obj.getAddressLine(0);
            nameLocationYourSelf = add;
            add = add + "\n" + obj.getCountryCode();
            binding.locationEditProfile.setText(add);

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void initConfig() {
        try {
            Map config = new HashMap();
            config.put("cloud_name", "dl4lo9r1y");
            config.put("api_key", "477111637363519");
            config.put("api_secret", "q4gJ0kJOSRSDHKujijgYaSMhfJY");
            MediaManager.init(this, config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePath = data.getData();
        Log.d("LE HAI BIEN: ", "Image Path: " + imagePath);
        binding.avtEditProfile.setImageURI(imagePath);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUES_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                CustomToast.ct(EditProfileActivity.this, "Bạn không cho phép truy cập vị trí", CustomToast.LENGTH_SHORT, CustomToast.INFO, false).show();
            }
        }

// bắt sự kiện khi người dùng cấp quyền Camera
        switch (requestCode) {
            case CAMERA_PERMISSION_REQ:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
                        if (showRationale) {
                            CustomToast.ct(EditProfileActivity.this, "Bạn đã từ chối cấp quyền Camera", CustomToast.LENGTH_SHORT, CustomToast.INFO, false).show();
                        } else {
                            showSettingsAlert();
                        }
                    } else {

                    }
                }
        }
    }
}