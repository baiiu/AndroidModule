package com.baiiu.performance;

import android.app.Application;
import android.os.StrictMode;
import com.github.moduth.blockcanary.BlockCanary;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import me.drakeet.library.CrashWoodpecker;

/**
 * author: baiiu
 * date: on 16/10/25 11:15
 * description:
 */

public class PerformanceApplication extends Application {

    private RefWatcher mRefWatcher;

    @Override public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            strictMode();
            leakCanary();
            BlockCanary.install(this, new AppBlockCanaryContext()).start();
            CrashWoodpecker.flyTo(this);
        }

    }

    private void leakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }

        mRefWatcher = LeakCanary.install(this);
    }

    private void strictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll() //全都检测
                                           .penaltyFlashScreen()
                                           .penaltyDialog()
                                           .penaltyLog()
                                           .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll()
                                       .penaltyLog()
                                       .build());
    }

    public RefWatcher getRefWatcher() {
        return mRefWatcher;
    }


}
