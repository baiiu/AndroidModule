package com.baiiu.multiprocess;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.baiiu.library.LogUtil;

/**
 * author: baiiu
 * date: on 17/12/27 15:19
 * description:
 */
public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        LogUtil.d(UserManager.INSTANCE.age + ", " + UserManager.sId);
    }

}
