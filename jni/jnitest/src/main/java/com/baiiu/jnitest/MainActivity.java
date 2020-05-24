package com.baiiu.jnitest;

import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.baiiu.jnitest.basic.BasicFragment;
import com.baiiu.jnitest.dynamicLoad.DynamicFragment;
import com.baiiu.jnitest.dynamicLoad.DynamicLoad;
import com.baiiu.jnitest.referencePass.ReferencePassFragment;
import com.baiiu.jnitest.string.StringTestFragment;
import com.baiiu.jnitest.thread.ThreadFragment;
import com.baiiu.jnitest.typeConvert.TypeConvertFragment;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, getFragment())
                .commitAllowingStateLoss();
    }

    private Fragment getFragment() {

//        return new BasicFragment();

//        return new DynamicFragment();

//        return new TypeConvertFragment();

//        return new StringTestFragment();

//        return new ReferencePassFragment();

        return new ThreadFragment();
    }


}
