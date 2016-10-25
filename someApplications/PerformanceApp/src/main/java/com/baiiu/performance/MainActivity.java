package com.baiiu.performance;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.baiiu.performance.fragments.LeakCanaryFragment;
import com.baiiu.performance.fragments.StrictModeFragment;

public class MainActivity extends AppCompatActivity {
    /**
     * 内存泄露
     */
    private static Context context;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        leakCanary();
        testStrictMode();
        crashWoodpecker();

    }

    private void crashWoodpecker() {
        String s = "";
        s = null;
        s.toString();
    }

    private void leakCanary() {
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, new LeakCanaryFragment(), LeakCanaryFragment.class.getName())
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    private void testStrictMode() {
        getSupportFragmentManager().beginTransaction()
                .add(new StrictModeFragment(), StrictModeFragment.class.getName())
                .commitAllowingStateLoss();
    }

}
