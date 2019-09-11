package com.baiiu.performance;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.SystemClock;

import androidx.appcompat.app.AppCompatActivity;

import com.baiiu.library.LogUtil;
import com.baiiu.performance.fragments.LeakCanaryFragment;
import com.baiiu.performance.fragments.MergeFragment;
import com.baiiu.performance.fragments.StrictModeFragment;

public class MainActivity extends AppCompatActivity {
    /**
     * 内存泄露
     */
    private static Context context;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //跟布局使用Merge向上减少层级
        setContentView(R.layout.activity_main);

        context = this;

        //leakCanary();
        //testStrictMode();
        //crashWoodpecker();
        //blockCanary();
        merge();

        //Looper.getMainLooper().setMessageLogging(mainLooperPrinter);


        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        //可用堆内存，单个应用可以使用的最大内存，如果应用内存使用超过这个值，就报OOM
        int heapgrowthlimit = manager.getMemoryClass();
        //进程内存空间分配的最大值，表示的是单个虚拟机可用的最大内存
        int heapsize = manager.getLargeMemoryClass();
        LogUtil.d("heapgrowthlimit = " + heapgrowthlimit + "m" + ", heapsize = " + heapsize + "");

    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    private void merge() {
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, new MergeFragment(), MergeFragment.class.getName())
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }


    private void blockCanary() {
        SystemClock.sleep(3000);
    }

    private void crashWoodpecker() {
        String s = "";
        s = null;
        s.toString();
    }

    private void leakCanary() {
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, new LeakCanaryFragment(), LeakCanaryFragment.class.getName())
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    private void testStrictMode() {
        getSupportFragmentManager().beginTransaction()
                .add(new StrictModeFragment(), StrictModeFragment.class.getName())
                .commitAllowingStateLoss();
    }

}
