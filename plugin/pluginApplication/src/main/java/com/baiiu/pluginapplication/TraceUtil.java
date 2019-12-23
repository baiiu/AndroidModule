package com.baiiu.pluginapplication;

import android.app.Activity;
import android.widget.Toast;

/**
 * author: zhuzhe
 * time: 2019-12-20
 * description:
 */
public class TraceUtil {


    public static void onActivityCreate(Activity activity) {
        Toast.makeText(activity
                , activity.getClass()
                               .getName() + "call onResume"
                , Toast.LENGTH_SHORT)
                .show();

        android.util.Log.e("mLogU", "onActivityCreate");
    }

    public static void onActivityResume(Activity activity) {
        Toast.makeText(activity
                , activity.getClass()
                               .getName() + "call onResume"
                , Toast.LENGTH_SHORT)
                .show();

        android.util.Log.e("mLogU", "onActivityResume");
    }

    public static void onActivityPause(Activity activity) {
        Toast.makeText(activity
                , activity.getClass()
                               .getName() + "call onPause"
                , Toast.LENGTH_SHORT)
                .show();

        android.util.Log.e("mLogU", "onActivityPause");
    }

    public static void onActivityDestroy(Activity activity) {
        Toast.makeText(activity
                , activity.getClass()
                               .getName() + "call onResume"
                , Toast.LENGTH_SHORT)
                .show();

        android.util.Log.e("mLogU", "onActivityDestroy");
    }
}
