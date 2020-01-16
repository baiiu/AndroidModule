package com.baiiu.hookapp;

import android.app.NotificationManager;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.baiiu.hookapp.binderHook.BinderHook;
import com.baiiu.hookapp.startActivityHook.StartActivityHook;
import com.baiiu.library.LogUtil;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_startAct).setOnClickListener(this);
        findViewById(R.id.btn_clipboard).setOnClickListener(this);

    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_startAct:
                StartActivityHook.hook(MainActivity.this);
                startActivity(
                        new Intent(MainActivity.this, SecondActivity.class)
                );
                break;
            case R.id.btn_clipboard:
                BinderHook.hook();

                ClipboardManager clipboardManager =
                        (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                LogUtil.e("btn_clipboard: " +
                          clipboardManager +
                          ", " +
                          clipboardManager.getPrimaryClip()
                          + "， "
                          + clipboardManager.hasPrimaryClip());


                break;
        }
    }
}
