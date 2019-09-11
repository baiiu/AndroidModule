// (c)2016 Flipboard Inc, All Rights Reserved.

package com.example.testing.rxjavalearn.bestSample;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testing.rxjavalearn.bestSample.data.Data;
import com.example.testing.rxjavalearn.bestSample.data.Item;
import com.example.testing.rxjavalearn.util.LogUtil;

import java.util.List;

import rx.Observer;

/**
 * 来自朱凯的github:
 *https://github.com/rengwuxian/RxJavaSamples
 *
 */

public class CacheFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        load();
    }

    void clearMemoryCache() {
        Data.getInstance().clearMemoryCache();
    }

    void clearMemoryAndDiskCache() {
        Data.getInstance().clearMemoryAndDiskCache();
    }

    void load() {
        Data.getInstance()
                .subscribeData(new Observer<List<Item>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<Item> items) {
                        LogUtil.d(items.toString());
                    }
                });
    }

}
