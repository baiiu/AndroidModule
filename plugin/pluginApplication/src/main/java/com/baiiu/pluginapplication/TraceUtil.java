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
                               .getName() + "call onCreate"
                , Toast.LENGTH_LONG)
                .show();
    }

    public static void onActivityDestroy(Activity activity) {
        Toast.makeText(activity
                , activity.getClass()
                               .getName() + "call onDestroy"
                , Toast.LENGTH_LONG)
                .show();
    }
}
