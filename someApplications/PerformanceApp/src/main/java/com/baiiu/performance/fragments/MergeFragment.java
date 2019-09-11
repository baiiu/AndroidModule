package com.baiiu.performance.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.baiiu.performance.R;

/**
 * author: baiiu
 * date: on 16/11/15 14:51
 * description:
 */
public class MergeFragment extends Fragment {

    @Nullable
    @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                       @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_merge, container, false);


        return view;
    }
}
