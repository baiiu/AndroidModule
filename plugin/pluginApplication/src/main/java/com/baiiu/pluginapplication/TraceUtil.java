package com.baiiu.pluginapplication;

import android.app.Activity;
import android.widget.Toast;

/**
 * author: zhuzhe
 * time: 2019-12-20
 * description:
 */
// @formatter:off
public class TraceUtil {

    public static void onMethodEnter(String className,String methodName,String superName) {
        android.util.Log.e("mLogUUUU","onMethodEnter: " + className +", " + methodName +", " + superName);
    }

    public static void onMethodExit(String className,String methodName,String superName) {
        android.util.Log.e("mLogUUUU","onMethodExit: " + className +", " + methodName +", " + superName);
    }


    public static void onActivityCreate(Activity activity) {
        Toast.makeText(activity
                , activity.getClass().getName() + "call onCreate"
                , Toast.LENGTH_SHORT)
                .show();

        android.util.Log.e("mLogU", activity.getClass().getName() + "onCreate");
    }

    public static void onActivityResume(Activity activity) {
        Toast.makeText(activity
                , activity.getClass().getName() + "call onResume"
                , Toast.LENGTH_SHORT)
                .show();

        android.util.Log.e("mLogU", activity.getClass().getName() +"onResume");
    }

    public static void onActivityPause(Activity activity) {
        Toast.makeText(activity
                , activity.getClass().getName() + "call onPause"
                , Toast.LENGTH_SHORT)
                .show();

        android.util.Log.e("mLogU", activity.getClass().getName() +"onPause");
    }

    public static void onActivityDestroy(Activity activity) {
        Toast.makeText(activity
                , activity.getClass()
                               .getName() + "call onDestroy"
                , Toast.LENGTH_SHORT)
                .show();

        android.util.Log.e("mLogU", activity.getClass().getName() +"onDestroy");
    }
}
