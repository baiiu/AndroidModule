package com.baiiu.toucheventstudy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import com.baiiu.toucheventstudy.viewDragHelper.ViewDragHelperFragment;

public class MainActivity extends AppCompatActivity {


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, getFragment(), "mainFragemnt")
                .commit();

    }

    public Fragment getFragment() {
        return

                //new SimpleBehaviorFragment()
                //new ListScrollFragment()
                new ViewDragHelperFragment()


                ;
    }

}
