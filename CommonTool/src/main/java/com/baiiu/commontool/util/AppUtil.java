package com.baiiu.commontool.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.text.TextUtils;
import com.baiiu.commontool.app.UIUtil;
import com.baiiu.commontool.util.store.PreferenceUtil;
import java.util.List;

/**
 * Created by baiiu on 16/1/8.
 * 判断本app是否运行在前台
 */
public class AppUtil {

    public static boolean isAppForeground() {

        String packageName = PreferenceUtil.instance(Constant.PREFERENCE_CONFIG).get("packagename", "");
        if (TextUtils.isEmpty(packageName)) {
            packageName = UIUtil.getContext().getPackageName();
            PreferenceUtil.instance(Constant.PREFERENCE_CONFIG).put("packagename", packageName);
        }

        ActivityManager am = (ActivityManager) UIUtil.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = am.getRunningTasks(1);
        if (runningTasks == null) {
            return false;
        }

        ActivityManager.RunningTaskInfo runningTaskInfo = runningTasks.get(0);
        ComponentName topActivity = runningTaskInfo.topActivity;
        String topActivityPackageName = topActivity.getPackageName();


        return packageName.equals(topActivityPackageName);
    }
}
