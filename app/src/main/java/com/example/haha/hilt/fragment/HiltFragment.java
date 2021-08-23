package com.example.haha.hilt.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.haha.customview.R;
import com.example.haha.hilt.HiltSimple;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

public class HiltFragment extends Fragment {

    @Inject
    public HiltSimple hiltSimple;
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hilt,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hiltSimple.doSomething();
    }
}
