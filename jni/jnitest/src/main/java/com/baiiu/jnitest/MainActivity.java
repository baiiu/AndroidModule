package com.baiiu.jnitest;

import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((TextView) findViewById(R.id.textView)).setText(stringFromJNI());

        testCallJava(this);
    }

    public native String stringFromJNI();

    private int code = 10;
    private String msg = "hello world";

    public void cCallJava(String str) {
        android.util.Log.e("mLogU", "cCallJava: " + str);

        Toast.makeText(this, str, Toast.LENGTH_SHORT)
                .show();
    }


    public native void testCallJava(MainActivity activity);

}
