package com.baiiu.dagger2learn.doubleDependenciesSample;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.baiiu.dagger2learn.doubleDependenciesSample.bean.Three;
import com.baiiu.dagger2learn.doubleDependenciesSample.bean.Two;
import com.baiiu.dagger2learn.util.LogUtil;

import javax.inject.Inject;

/**
 * auther: baiiu
 * time: 16/6/14 14 21:24
 * description:
 */
public class DoubleDependenciesFragment extends Fragment {

    @Inject Two two;
    @Inject Three three;

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DaggerDoubleDependenciesComponent.builder()
                .build()
                .inject(this);

        LogUtil.d(two.toString());
        LogUtil.d(three.toString());

    }

}
