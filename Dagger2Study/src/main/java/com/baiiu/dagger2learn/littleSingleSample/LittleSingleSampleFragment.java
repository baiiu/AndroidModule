package com.baiiu.dagger2learn.littleSingleSample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import com.baiiu.dagger2learn.util.LogUtil;
import javax.inject.Inject;

/**
 * author: baiiu
 * date: on 16/6/13 17:26
 * description:
 */
public class LittleSingleSampleFragment extends Fragment {

    /*
        通过构造函数注入
     */
    @Inject Apple apple;

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerLittleSampleComponent.builder()
                .build()
                .inject(this);

        LogUtil.d(apple.toString());
    }
}
