package com.baiiu.jnitest;

import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.baiiu.jnitest.basic.BasicFragment;
import com.baiiu.jnitest.dynamicLoad.DynamicFragment;
import com.baiiu.jnitest.dynamicLoad.DynamicLoad;

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

        return new DynamicFragment();

    }


}
