package com.baiiu.hookapp;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.baiiu.hookapp.binderHook.BinderHook2;
import com.baiiu.hookapp.loadedApkHook.CreateClassLoaderHook;
import com.baiiu.hookapp.msHook.AMSHook;
import com.baiiu.hookapp.msHook.PMSHook;
import com.baiiu.hookapp.pathClassLoaderHook.PathClassLoaderHook;
import com.baiiu.hookapp.pathClassLoaderHook2.PathClassLoaderHook2;
import com.baiiu.hookapp.startActivityHook.StartActivityHook;
import com.baiiu.hookapp.startStubActivity.StubHook;
import com.baiiu.hookapp.startStubActivity.TargetActivity;
import com.baiiu.library.LogUtil;

import static com.baiiu.hookapp.loadedApkHook.Util.NAME_CLASS;
import static com.baiiu.hookapp.loadedApkHook.Util.NAME_PACKAGE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LogUtil.d("MainActivity#onCreate");

        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_startAct).setOnClickListener(this);
        findViewById(R.id.btn_clipboard).setOnClickListener(this);
        findViewById(R.id.btn_pmshook).setOnClickListener(this);
        findViewById(R.id.btn_startStubActInDex).setOnClickListener(this);
        findViewById(R.id.btn_startStubActOtherDex_hook_pathClassLoader).setOnClickListener(this);
        findViewById(R.id.btn_startStubActOtherDex_hook_loadedApk).setOnClickListener(this);
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
            case R.id.btn_amshook:
                AMSHook.hook();
                break;
            case R.id.btn_pmshook:
                PMSHook.hook(this);
                LogUtil.e(MyApplication.getContext() + ", " + this);
                LogUtil.e(MyApplication.getContext().getPackageManager() + ", " + this.getPackageManager());
                getPackageManager().getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
                break;
            case R.id.btn_startStubActInDex:
                // 打开未在manifest文件中注册的本dex下的activity
                StubHook.hook();
                startActivity(new Intent(this, TargetActivity.class));
                break;
            case R.id.btn_startStubActOtherDex_hook_pathClassLoader:
                // hookPathClassLoader打开别的dex下的activity
                PathClassLoaderHook.hook();

                startActivity(new Intent().setClassName(NAME_PACKAGE, NAME_CLASS));
                break;
            case R.id.btn_startStubActOtherDex_hook_loadedApk:
                // hookLoadedApk打开别的dex下的activity
                CreateClassLoaderHook.hook();
                startActivity(new Intent().setClassName(NAME_PACKAGE, NAME_CLASS));
                break;
        }
    }
}
