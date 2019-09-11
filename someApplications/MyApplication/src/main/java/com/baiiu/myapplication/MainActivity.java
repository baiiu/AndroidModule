package com.baiiu.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.baiiu.myapplication.module.ultraPtr.PtrPageFragment;

public class MainActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, getFragment(), "mainFragemnt")
                .commit();

        //getSupportFragmentManager().beginTransaction()
        //        .add(new OneDialogFragment(), OneDialogFragment.class.getName())
        //        .commitAllowingStateLoss();
    }

    private Fragment getFragment() {
        return

                //new FastScrollFragment()

                new PtrPageFragment()

                //new WatermarkFragment()

                //new JavaJsFragment()

                ;
    }

}
