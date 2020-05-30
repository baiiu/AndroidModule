package com.baiiu.jnitest.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * author: baiiu
 * date: on 17/4/18 17:19
 * description:
 */
public abstract class BaseFragment extends Fragment {

    protected View mView;
    protected Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        int layoutId = provideLayoutId();
        if (mView == null && layoutId != 0) {
            mView = inflater.inflate(layoutId, container, false);
        }

        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initOnCreateView();
    }

    protected int provideLayoutId() {
        return 0;
    }

    protected abstract void initOnCreateView();
}
