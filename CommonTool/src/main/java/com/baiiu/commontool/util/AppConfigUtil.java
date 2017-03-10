package com.baiiu.commontool.util;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import com.baiiu.commontool.app.UIUtil;
import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

/**
 * author: baiiu
 * date: on 16/3/22 17:08
 * description:
 */
public class AppConfigUtil {
    private static final String PATH_CPU = "/sys/devices/system/cpu/";
    private static final String CPU_FILTER = "cpu[0-9]+";
    private static int CPU_CORES = 0;

    /**
     * 机器可用的cpu内核数
     */
    public static int availableProcessors() {
        if (CPU_CORES > 0) {
            return CPU_CORES;
        }
        //Private Class to display only CPU devices in the directory listing
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                //Check if filename is "cpu", followed by a single digit number
                if (Pattern.matches(CPU_FILTER, pathname.getName())) {
                    return true;
                }
                return false;
            }
        }
        try {
            //Get directory containing CPU info
            File dir = new File(PATH_CPU);
            //Filter to only list the devices we care about
            File[] files = dir.listFiles(new CpuFilter());
            //Return the number of cores (virtual CPU devices)
            CPU_CORES = files.length;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (CPU_CORES < 1) {
            CPU_CORES = Runtime.getRuntime().availableProcessors();
        }
        if (CPU_CORES < 1) {
            CPU_CORES = 1;
        }
        return CPU_CORES;
    }

    /**
     * 获取系统分配的堆内存大小
     */
    public static int availableHeap() {
        ActivityManager am = (ActivityManager) UIUtil.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        int memoryClass = am.getMemoryClass();
        if (Version.hasHoneycomb() && isLargeHeap()) {
            memoryClass = getLargeMemoryClass(am);
        }
        return memoryClass;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static boolean isLargeHeap() {
        return (UIUtil.getContext().getApplicationInfo().flags & ApplicationInfo.FLAG_LARGE_HEAP) != 0;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static int getLargeMemoryClass(ActivityManager am) {
        return am.getLargeMemoryClass();
    }
}
