package com.example.findaroomver2.ui.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.findaroomver2.R;
import com.example.findaroomver2.constant.AppConstant;
import com.example.findaroomver2.constant.CallbackClick;
import com.example.findaroomver2.databinding.FragmentChatBinding;
import com.example.findaroomver2.model.UserClient;
import com.example.findaroomver2.request.login.Data;
import com.example.findaroomver2.ui.activity.ChatMessageActivity;
import com.example.findaroomver2.ui.activity.DetailActivity;
import com.example.findaroomver2.ui.adapter.HostAdapter;
import com.example.findaroomver2.viewmodel.ChatViewModel;
import com.example.findaroomver2.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment implements HostAdapter.EventClick {
    private List<Data> mListHost = new ArrayList<>();
    private MainViewModel chatViewModel;
    private CallbackClick callbackClick;
    private FragmentChatBinding binding;
    private HostAdapter hostAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChatBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        chatViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        hostAdapter = new HostAdapter();

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.rcvListHost.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.btnSearch.setOnClickListener(v -> callbackClick.clickHome());

        int role = UserClient.getInstance().getRole();
        if (role == 0) {
             List<String> mList = new ArrayList<>();

            chatViewModel.getMsgId(UserClient.getInstance().getId()).observe(requireActivity(), it -> {
                if (it.size() == 0) {
                    binding.rcvListHost.setVisibility(View.GONE);
                    binding.contentNullList.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.GONE);
                } else {
                    binding.contentNullList.setVisibility(View.GONE);
                    for (int i = 0; i < it.size(); i++) {
                        mList.add(it.get(i).getSendTo());
                    }
                    Set<String> set = new HashSet<String>(mList);
                    List<String> listIdHost = new ArrayList<String>(set);
                    for (int i = 0; i < listIdHost.size(); i++) {
                        chatViewModel.getHost(listIdHost.get(i)).observe(requireActivity(), item -> {
                            mListHost.add(item.get(0));
                            binding.progressBar.setVisibility(View.GONE);
                            hostAdapter.setListHost(mListHost);
                            hostAdapter.setEventClick(this);
                            binding.rcvListHost.setAdapter(hostAdapter);
                        });
                    }
                }
            });
        } else if (role == 2) {
            List<String> mList = new ArrayList<>();
            chatViewModel.getMsgIdSendTo(UserClient.getInstance().getId()).observe(requireActivity(), it -> {
                if (it.size() == 0) {
                    binding.rcvListHost.setVisibility(View.GONE);
                    binding.contentNullList.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.GONE);
                } else {
                    binding.contentNullList.setVisibility(View.GONE);
                    for (int i = 0; i < it.size(); i++) {
                        mList.add(it.get(i).getSend());
                    }
                    Set<String> set = new HashSet<String>(mList);
                    List<String> listIdHost = new ArrayList<String>(set);
                    for (int i = 0; i < listIdHost.size(); i++) {
                        chatViewModel.getHost(listIdHost.get(i)).observe(requireActivity(), item -> {
                            mListHost.add(item.get(0));
                            binding.progressBar.setVisibility(View.GONE);
                            hostAdapter.setListHost(mListHost);
                            hostAdapter.setEventClick(this);
                            binding.rcvListHost.setAdapter(hostAdapter);
                        });
                    }
                }
            });
        }

    }

    @Override
    public void onClick(Data user) {
        Intent intent = new Intent(getActivity(), ChatMessageActivity.class);
        intent.putExtra(AppConstant.ID_USER, user.getId());
        intent.putExtra(AppConstant.IMAGE_USER, user.getImage());
        intent.putExtra(AppConstant.NAME_USER, user.getFullName());
        startActivity(intent);
    }
}