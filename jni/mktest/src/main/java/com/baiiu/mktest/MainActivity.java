package com.baiiu.mktest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("hello-mk");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((TextView) findViewById(R.id.textView)).setText(String.format("%s %s", stringFromJNI(), stringFromJNI2()));
    }

    public native String stringFromJNI();

    public native String stringFromJNI2();

}
