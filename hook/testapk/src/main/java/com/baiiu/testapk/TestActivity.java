package com.baiiu.testapk;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        android.util.Log.e("mLogU",
                "TestActivity#onCreate: " + getString(R.string.hello_from_apk20));

        final View viewById = findViewById(R.id.textView);
        if (viewById != null) {
            viewById.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ((TextView) viewById).setText(R.string.hello_from_apk19);
                }
            }, 3000);
        }

    }
}
