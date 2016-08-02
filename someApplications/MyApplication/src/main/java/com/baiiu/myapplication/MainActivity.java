package com.baiiu.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import com.baiiu.myapplication.module.ultraPtr.PtrPageFragment;

public class MainActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, getFragment(), "mainFragemnt")
                .commit();
    }

    private Fragment getFragment() {
        return

                //new FastScrollFragment()

                new PtrPageFragment()

                ;
    }
}
