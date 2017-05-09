package com.baiiu.toucheventstudy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import com.baiiu.library.LogUtil;
import com.baiiu.toucheventstudy.touchEvent.TouchEventFragment;

public class MainActivity extends AppCompatActivity {


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, getFragment(), "mainFragemnt")
                .commit();

        //ScrollView;
        //NestedScrollView;
        //ViewPager;
        //RecyclerView;
        //SwipeRefreshLayout;
    }

    public Fragment getFragment() {
        return

                //new SimpleBehaviorFragment()
                //new ListScrollFragment()
                //new ViewDragHelperFragment()

                new TouchEventFragment()

                ;
    }

    @Override public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtil.d("Activity --> dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override public boolean onTouchEvent(MotionEvent event) {
        LogUtil.d("Activity --> onTouchEvent");
        return super.onTouchEvent(event);
    }

}
