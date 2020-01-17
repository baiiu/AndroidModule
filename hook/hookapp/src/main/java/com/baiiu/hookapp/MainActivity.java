package com.baiiu.hookapp;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.baiiu.hookapp.msHook.AMSHook;
import com.baiiu.hookapp.binderHook.BinderHook2;
import com.baiiu.hookapp.msHook.PMSHook;
import com.baiiu.hookapp.startActivityHook.StartActivityHook;
import com.baiiu.library.LogUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int a;
    private final int b = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_startAct).setOnClickListener(this);
        findViewById(R.id.btn_clipboard).setOnClickListener(this);
        findViewById(R.id.btn_pmshook).setOnClickListener(this);

        AMSHook.hook();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_startAct:
                StartActivityHook.hook(MainActivity.this);
                startActivity(
                        new Intent(MainActivity.this, SecondActivity.class)
                );
                break;
            case R.id.btn_clipboard:
                BinderHook2.hook();

                ClipboardManager clipboardManager =
                        (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                LogUtil.e("btn_clipboard: " +
                        clipboardManager +
                        ", " +
                        clipboardManager.getPrimaryClip()
                        + "， "
                        + clipboardManager.hasPrimaryClip());
                break;
            case R.id.btn_pmshook:
                PMSHook.hook(this);
                LogUtil.e(MyApplication.getContext() + ", " + this);
                LogUtil.e(MyApplication.getContext().getPackageManager() + ", " + this.getPackageManager());
                getPackageManager().getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
                break;
        }
    }
}
