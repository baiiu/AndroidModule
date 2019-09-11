package com.baiiu.dagger2learn.littleSample;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.baiiu.dagger2learn.di.qualifier.FruitType;
import com.baiiu.dagger2learn.util.LogUtil;

import javax.inject.Inject;

/**
 * auther: baiiu
 * time: 16/6/12 12 22:48
 * description:
 */
public class FruitFragment extends Fragment {

    //@Named("A")
    @FruitType(1) @Inject Fruit fruitA;

    //@Named("B")
    @FruitType(2) @Inject Fruit fruitB;


    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*
            注入对象
         */
        DaggerFruitComponent.builder()
                .build()
                .inject(this);

        LogUtil.d(fruitA.toString());
        LogUtil.d(fruitB.toString());
    }
}
