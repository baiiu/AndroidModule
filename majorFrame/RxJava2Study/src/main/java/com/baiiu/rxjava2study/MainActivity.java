package com.baiiu.rxjava2study;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


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
