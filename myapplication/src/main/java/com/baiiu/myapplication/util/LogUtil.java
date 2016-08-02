package com.baiiu.myapplication.util;

import android.util.Log;

/**
 * Log工具 <BR>
 * <p/>
 * 输出格式:className.methodName(L:lineNumber) --> content。
 *
 * @author baiiu
 */
public class LogUtil {

    public static String TAG = "mLogUtil";

    private LogUtil() {
    }

    public static boolean allow = true;

    private static String getPrefix() {
        String prefix = "%s.%s(L:%d)";

        StackTraceElement caller = Thread.currentThread()
                .getStackTrace()[4];
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        prefix = String.format(prefix, callerClazzName, caller.getMethodName(), caller.getLineNumber());

        return prefix;
    }

    /**
     * Send a DEBUG log message,蓝色
     */
    public static void d(String content) {
        if (allow) {
            Log.d(TAG, getPrefix() + " --> " + content);
        }
    }

    public static void d(int content) {
        if (allow) {
            Log.d(TAG, getPrefix() + " --> " + content);
        }
    }

    public static void d(float content) {
        if (allow) {
            Log.d(TAG, getPrefix() + " --> " + content);
        }
    }

    /**
     * Send a ERROR log message and log the exception,红色
     */
    public static void e(String content, Throwable e) {
        if (allow) {
            Log.e(TAG, getPrefix() + " --> " + content, e);
        }
    }

    /**
     * Send an ERROR log message,红色
     */
    public static void e(String content) {
        if (allow) {
            Log.e(TAG, getPrefix() + " --> " + content);
        }
    }

    /**
     * Send an INFO log message,绿色
     */
    public static void i(String content) {
        if (allow) {
            Log.i(TAG, getPrefix() + " --> " + content);
        }
    }

    /**
     * Send a VERBOSE log message,黑色
     */
    public static void v(String content) {
        if (allow) {
            Log.v(TAG, getPrefix() + " --> " + content);
        }
    }

    /**
     * Send a WARN log message,黄色
     */
    public static void w(String content) {
        if (allow) {
            Log.w(TAG, getPrefix() + " --> " + content);
        }
    }
}
