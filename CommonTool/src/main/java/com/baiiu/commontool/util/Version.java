package com.baiiu.commontool.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.StrictMode;

/**
 * author: baiiu
 * date: on 16/3/22 11:14
 * description: copy from liaohuqiu
 */
public class Version {

    private Version() {
    }

    @TargetApi(11) // @TargetApi(VERSION_CODES.HONEYCOMB)
    public static void enableStrictMode() {
        if (Version.hasGingerbread()) {
            StrictMode.ThreadPolicy.Builder threadPolicyBuilder =
                    new StrictMode.ThreadPolicy.Builder().detectAll()
                            .penaltyLog();
            StrictMode.VmPolicy.Builder vmPolicyBuilder = new StrictMode.VmPolicy.Builder().detectAll()
                    .penaltyLog();

            if (Version.hasHoneycomb()) {
                threadPolicyBuilder.penaltyFlashScreen();
                // vmPolicyBuilder.setClassInstanceLimit(ImageGridActivity.class, 1).setClassInstanceLimit(ImageDetailActivity.class, 1);
            }
            StrictMode.setThreadPolicy(threadPolicyBuilder.build());
            StrictMode.setVmPolicy(vmPolicyBuilder.build());
        }
    }

    /**
     * API level is or higher than 8
     */
    public static boolean hasFroyo() {
        // Can use static final constants like FROYO, declared in later versions
        // of the OS since they are inlined at compile time. This is guaranteed behavior.
        return Build.VERSION.SDK_INT >= 8; // VERSION_CODES.FROYO;
    }

    /**
     * API level is or higher than 9
     */
    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= 9; // VERSION_CODES.GINGERBREAD;
    }

    /**
     * API level is or higher than 11
     */
    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= 11; // VERSION_CODES.HONEYCOMB;
    }

    /**
     * API level is or higher than 12
     */
    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= 12; // VERSION_CODES.HONEYCOMB_MR1;
    }

    /**
     * API level is or higher than 16
     */
    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= 16; // VERSION_CODES.JELLY_BEAN;
    }

    /**
     * API level is higher than 19
     */
    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= 19; // VERSION_CODES.KITKAT;
    }

    /**
     * API level is higher than 21
     */
    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= 21;
    }

    /**
     * API level is higher than 21
     */
    public static boolean hasMarshmallow() {
        return Build.VERSION.SDK_INT >= 23;
    }

    /**
     * API level is between 19 and 21
     */
    public static boolean hasKitKatAndUnderLollipop() {
        return Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21;
    }

    /**
     * API level is lower than 19
     */
    public static boolean belowKitKat() {
        return Build.VERSION.SDK_INT < 19;
    }
}
