package com.baiiu.commontool;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import com.baiiu.commontool.base.BaseActivity;
import com.baiiu.commontool.net.util.HttpNetUtil;
import com.baiiu.commontool.net.util.NetWorkReceiver;
import com.baiiu.commontool.util.AppConfigUtil;
import com.baiiu.commontool.util.store.StorageUtil;
import com.baiiu.library.LogUtil;

public class MainActivity extends BaseActivity {

    private NetWorkReceiver netWorkReceiver;

    @Override public int provideLayoutId() {
        return R.layout.activity_main;
    }

    @Override protected void initOnCreate(Bundle savedInstanceState) {
        initBroadCast();

        // @formatter:off
        LogUtil.d(StorageUtil.getCacheDirectory().getAbsolutePath());
        LogUtil.d(StorageUtil.getCacheDirectory(false).getAbsolutePath());
        LogUtil.d(StorageUtil.getUnderCacheDirectory("aaa").getAbsolutePath());
        LogUtil.d(StorageUtil.getUnderCacheDirectory("aaaa", false).getAbsolutePath());
        LogUtil.d(StorageUtil.getPublicDirectory("aaa").getAbsolutePath());
        // @formatter:on

        LogUtil.d("availableProcessors: " + AppConfigUtil.availableProcessors());
        LogUtil.d("availableHeap: " + AppConfigUtil.availableHeap());
    }

    private void initBroadCast() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        netWorkReceiver = new NetWorkReceiver();
        registerReceiver(netWorkReceiver, filter);
        HttpNetUtil.setConnected();
    }

    @Override protected void onDestroy() {
        unregisterReceiver(netWorkReceiver);

        super.onDestroy();
    }
}
