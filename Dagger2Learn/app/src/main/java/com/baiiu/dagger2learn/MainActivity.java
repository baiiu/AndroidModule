package com.baiiu.dagger2learn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.baiiu.dagger2learn.bean.OnePerson;
import com.baiiu.dagger2learn.di.component.DaggerMainComponent;
import com.baiiu.dagger2learn.di.component.MainComponent;
import com.baiiu.dagger2learn.di.module.ApplicationModule;
import com.baiiu.dagger2learn.util.LogUtil;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    OnePerson onePerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MainComponent mainComponent = DaggerMainComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        mainComponent.inject(this);

        LogUtil.d(onePerson.toString());
    }


}
