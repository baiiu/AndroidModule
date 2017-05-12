package com.baiiu.rxjava2study;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, getFragment(), "operator")
                .commit();

    }

    public Fragment getFragment() {

        return new CreateFragment();

    }
}
