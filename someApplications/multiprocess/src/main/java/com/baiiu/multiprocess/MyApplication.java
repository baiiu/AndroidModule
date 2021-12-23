package com.baiiu.multiprocess;

import android.app.Application;
import com.baiiu.library.LogUtil;

/**
 * author: baiiu
 * date: on 17/12/27 15:39
 * description:
 */
public class MyApplication extends Application {

    @Override public void onCreate() {
        super.onCreate();
        LogUtil.d("application onCreate");

        //int pid = android.os.Process.myPid();//获取进程pid
        //ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);//获取系统的ActivityManager服务
        //for (ActivityManager.RunningAppProcessInfo appProcess : am.getRunningAppProcesses()) {
        //    if (appProcess.pid == pid) {
        //        LogUtil.d("Process.myUid(): "
        //                          + Process.myUid()
        //                          + ","
        //                          + "Process.myPid(): "
        //                          + Process.myPid()
        //                          + ", "
        //                          + appProcess.processName);
        //        return;
        //    }
        //}
    }

}
