package com.baiiu.performance;

import android.content.Context;
import com.squareup.leakcanary.RefWatcher;

/**
 * author: baiiu
 * date: on 16/10/25 11:56
 * description:
 */

public class UIUtil {

    public static RefWatcher getRefWatcher(Context context) {
        PerformanceApplication application = (PerformanceApplication) context.getApplicationContext();
        return application.getRefWatcher();
    }
}
