package com.baiiu.jnitest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.baiiu.jnitest.bitmap.BitmapFragment;
import com.baiiu.jnitest.dlopen.DLOpenFragment;
import com.baiiu.jnitest.dns.DNSFragment;
import com.baiiu.jnitest.epoll.EpollFragment;
import com.baiiu.jnitest.exception.ExceptionFragment;
import com.baiiu.jnitest.reference.PassReferenceFragment;
import com.baiiu.jnitest.reference.ReferenceFragment;
import com.baiiu.jnitest.string.StringTestFragment;
import com.baiiu.jnitest.testSocket.TestSocketFragment;
import com.baiiu.jnitest.thread.ThreadCallbackFragment;
import com.baiiu.jnitest.thread.ThreadCreateFragment;
import com.baiiu.jnitest.thread.ThreadMutexFragment;

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

//        return new PassReferenceFragment();

//        return new ReferenceFragment();

//        return new ThreadFragment();

//        return new ExceptionFragment();

//        return new ThreadCallbackFragment();

//        return new ThreadCreateFragment();

//        return new ThreadMutexFragment();

//        return new BitmapFragment();

//        return new TestSocketFragment();

//        return new DLOpenFragment();

//        return new EpollFragment();

        return new DNSFragment();
    }


}
