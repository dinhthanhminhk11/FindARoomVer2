package com.example.findaroomver2.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findaroomver2.MainActivity;
import com.example.findaroomver2.R;
import com.example.findaroomver2.constant.AppConstant;
import com.example.findaroomver2.databinding.ActivitySearchRoomBinding;
import com.example.findaroomver2.model.Post;
import com.example.findaroomver2.model.SearchModel;
import com.example.findaroomver2.response.post.PostHome;
import com.example.findaroomver2.ui.adapter.SearchAdapter;
import com.example.findaroomver2.ui.adapter.postfragment.PostAdapter;
import com.example.findaroomver2.ui.bottomsheet.BottomSheetFilter;
import com.example.findaroomver2.viewmodel.SearchRoomViewModel;

import java.util.List;

public class SearchRoomActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivitySearchRoomBinding binding;
    private SearchRoomViewModel searchRoomViewModel;
    public String nameLocation = "";
    private SearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initToolbar();
        initView();
        initData();
    }

    private void initData() {
        binding.etSearchHomeFragment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (binding.etSearchHomeFragment.getText().toString().length() > 0) {
                    binding.listSearch.setVisibility(View.VISIBLE);
                    binding.contentAddress.setVisibility(View.GONE);
                    binding.listPost.setVisibility(View.GONE);
                    searchRoomViewModel.getListSearchLocationHotel(binding.etSearchHomeFragment.getText().toString().trim());
                } else {
                    binding.listSearch.setVisibility(View.GONE);
                    binding.contentAddress.setVisibility(View.VISIBLE);
                    binding.listPost.setVisibility(View.VISIBLE);
                }
            }
        });

        searchRoomViewModel.getSearchModelMutableLiveData().observe(this, new Observer<List<SearchModel>>() {
            @Override
            public void onChanged(List<SearchModel> searchModel) {
                binding.contentAddress.setVisibility(View.GONE);
                searchAdapter.setData(searchModel);
                searchAdapter.setConsumer(o -> {
                    if (o instanceof SearchModel) {
                        if (((SearchModel) o).getType() == 2) {
                            Intent intent = new Intent(SearchRoomActivity.this, DetailActivity.class);
                            intent.putExtra(AppConstant.ID_POST, ((SearchModel) o).getIdPost());
                            startActivity(intent);
                        } else {
                            onClickTrendLocation(((SearchModel) o).getTitlePost());
                        }
                    }
                });
                searchAdapter.setTextHighLight(binding.etSearchHomeFragment.getText().toString().trim());
                binding.listSearch.setAdapter(searchAdapter);
            }
        });

        searchRoomViewModel.getListMutableLiveDataPost().observe(this, new Observer<PostHome>() {
            @Override
            public void onChanged(PostHome posts) {
                PostAdapter postAdapter = new PostAdapter(posts.getData());
                postAdapter.setCallback(new PostAdapter.Callback() {
                    @Override
                    public void onClickItem(Post post) {
                        Intent intent = new Intent(SearchRoomActivity.this, DetailActivity.class);
                        intent.putExtra(AppConstant.ID_POST, post.get_id());
                        startActivity(intent);
                    }

                    @Override
                    public void onClickMore(Post post) {

                    }

                    @Override
                    public void onClickAddHeart(Post post) {

                    }

                    @Override
                    public void onClickRemoteHeart(Post post) {

                    }
                });
                binding.listPost.setAdapter(postAdapter);
            }
        });

    }

    private void initView() {
        searchAdapter = new SearchAdapter();
        searchRoomViewModel = new ViewModelProvider(this).get(SearchRoomViewModel.class);
        binding.listSearch.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.listPost.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.btnHaiPhong.setOnClickListener(this);
        binding.btnHaNoi.setOnClickListener(this);
        binding.btnQuangNinh.setOnClickListener(this);
        binding.btnBinhDuong.setOnClickListener(this);
        binding.btnDaNang.setOnClickListener(this);
        binding.btnHCM.setOnClickListener(this);
        binding.btnBacNinh.setOnClickListener(this);
        binding.btnFilter.setOnClickListener(this);

        binding.etSearchHomeFragment.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    onClickTrendLocation(binding.etSearchHomeFragment.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    private void initToolbar() {
        binding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @SuppressLint("NewApi")
    private void onClickTrendLocation(String textLocation) {
        nameLocation = textLocation;
        binding.etSearchHomeFragment.setText(nameLocation);
        binding.listSearch.setVisibility(View.GONE);
        binding.listPost.setVisibility(View.VISIBLE);
        searchRoomViewModel.getListPostSearchByTextCty(textLocation);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnHaNoi) {
            onClickTrendLocation("Ha Noi");
        } else if (id == R.id.btnHCM) {
            onClickTrendLocation("Hồ Chí Minh");
        } else if (id == R.id.btnQuangNinh) {
            onClickTrendLocation("Quảng Ninh");
        } else if (id == R.id.btnBinhDuong) {
            onClickTrendLocation("Bình Dương");
        } else if (id == R.id.btnDaNang) {
            onClickTrendLocation("Đà Nẵng");
        } else if (id == R.id.btnHaiPhong) {
            onClickTrendLocation("Hải Phòng");
        } else if (id == R.id.btnBacNinh) {
            onClickTrendLocation("Bắc Ninh");
        } else if (id == R.id.btnFilter) {
            BottomSheetFilter bottomSheetFilter = new BottomSheetFilter(this, new BottomSheetFilter.EventClick() {
                @Override
                public void onCLickFilter(String giaBd, String giaKt) {
                    binding.contentAddress.setVisibility(View.GONE);
                    if (binding.etSearchHomeFragment.getText().toString().isEmpty()) {
                        searchRoomViewModel.getListSearchPrice(giaBd, giaKt);
                    } else {
                        searchRoomViewModel.getListSearchLocationCtyAndPrice(binding.etSearchHomeFragment.getText().toString(), giaBd, giaKt);
                    }
                }
            });
            bottomSheetFilter.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}