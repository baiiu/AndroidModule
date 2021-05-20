package com.baiiu.nativeleak;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        XHook.get().init();
    }

    public void onClick(View view) {
        System.loadLibrary("test-lib");
        testLeak();
    }

    private native void testLeak();


}