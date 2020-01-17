package com.baiiu.hookapp.startStubActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.baiiu.hookapp.R;
import com.baiiu.library.LogUtil;

/**
 * author: zhuzhe
 * time: 2020-01-17
 * description:
 */
@SuppressLint("Registered")
public class TargetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);

        LogUtil.e("TargetActivity#onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.e("TargetActivity#onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.e("TargetActivity#onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.e("TargetActivity#onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.e("TargetActivity#onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.e("TargetActivity#onDestroy");
    }
}
