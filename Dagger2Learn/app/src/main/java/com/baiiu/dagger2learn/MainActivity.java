package com.baiiu.dagger2learn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.baiiu.dagger2learn.bean.OnePerson;
import com.baiiu.dagger2learn.di.module.ApplicationModule;
import com.baiiu.dagger2learn.littleSample.FruitFragment;
import com.baiiu.dagger2learn.littleSingleSample.LittleSingleSampleFragment;
import com.baiiu.dagger2learn.mvpSample.DaggerMainComponent;
import com.baiiu.dagger2learn.mvpSample.MainComponent;
import com.baiiu.dagger2learn.mvpSample.MainFragment;
import com.baiiu.dagger2learn.mvpSample.MainModule;
import com.baiiu.dagger2learn.util.LogUtil;
import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject OnePerson onePerson;
    /*
        成员变量,生命周期为整个Activity
     */
    public MainComponent mainComponent;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //通过构造函数注入
        getSupportFragmentManager().beginTransaction()
                .add(new LittleSingleSampleFragment(), "littleSingleSample")
                .commit();

        //通过Module注入,同时Module优先级高于构造函数
        getSupportFragmentManager().beginTransaction()
                .add(new FruitFragment(), "littleSample")
                .commit();

        /*
            看源码可以看到如果Module只有有参构造函数,则必须要显示传入Module
         */
        mainComponent = DaggerMainComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .mainModule(new MainModule())
                .build();
        mainComponent.inject(this);
        LogUtil.d(onePerson.toString());


        //MVP
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new MainFragment())
                .commit();

    }

}
