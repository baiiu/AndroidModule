package com.baiiu.performance.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.baiiu.performance.R;

/**
 * author: baiiu
 * date: on 16/11/15 14:51
 * description:
 */
public class MergeFragment extends Fragment {

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_merge, container, false);


        return view;
    }
}
