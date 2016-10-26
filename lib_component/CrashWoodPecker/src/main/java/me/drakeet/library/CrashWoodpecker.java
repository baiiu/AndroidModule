/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 drakeet (drakeet.me@gmail.com)
 * http://drakeet.me
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.drakeet.library;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import me.drakeet.library.ui.CatchActivity;

/**
 * Created by drakeet(http://drakeet.me)
 * Date: 8/31/15 22:35
 */
public class CrashWoodpecker implements UncaughtExceptionHandler {

    private final static String TAG = "CrashWoodpecker";

    /* Default log out time, 7days. */
    private final static long LOG_OUT_TIME = TimeUnit.DAYS.toMillis(7);

    /* get DateFormatter for current locale */
    private final static DateFormat FORMATTER = DateFormat.getDateInstance();

    private volatile UncaughtExceptionHandler originHandler;
    private volatile UncaughtExceptionInterceptor interceptor;
    private volatile boolean crashing = false;

    private boolean forceHandleByOrigin = false;
    private final Context context;
    private final String version;
    /* for highlight */
    private ArrayList<String> keys;


    /**
     * Install CrashWoodpecker.
     *
     * @param application to capture exceptions for.
     * @param forceHandleByOrigin whether to force original
     * UncaughtExceptionHandler handle again,
     * by default false.
     * @return CrashWoodpecker instance.
     */
    public static CrashWoodpecker flyTo(Application application, boolean forceHandleByOrigin) {
        return new CrashWoodpecker(application, forceHandleByOrigin);
    }


    /**
     * Install CrashWoodpecker.
     *
     * @param application to capture exceptions for.
     * @return CrashWoodpecker instance.
     */
    public static CrashWoodpecker flyTo(Application application) {
        return new CrashWoodpecker(application, false);
    }


    /**
     * Install CrashWoodpecker.
     *
     * @param application to capture exceptions for.
     * @param forceHandleByOrigin whether to force original
     * UncaughtExceptionHandler handle again,
     * by default false.
     * @return CrashWoodpecker instance.
     * @deprecated use {@link CrashWoodpecker#flyTo(Application, boolean)} instead.
     */
    @Deprecated
    public static CrashWoodpecker init(Application application, boolean forceHandleByOrigin) {
        return flyTo(application, forceHandleByOrigin);
    }


    /**
     * Install CrashWoodpecker.
     *
     * @param application to capture exceptions for.
     * @return CrashWoodpecker instance.
     * @deprecated use {@link CrashWoodpecker#flyTo(Application)} instead.
     */
    @Deprecated
    public static CrashWoodpecker init(Application application) {
        return flyTo(application, false);
    }


    private CrashWoodpecker(Context context, boolean forceHandleByOrigin) {
        this.context = context;
        this.forceHandleByOrigin = forceHandleByOrigin;
        this.keys = new ArrayList<>();
        this.keys.add(this.context.getPackageName());

        try {
            PackageInfo info = context.getPackageManager()
                .getPackageInfo(context.getPackageName(), 0);
            version = info.versionName + "(" + info.versionCode + ")";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        UncaughtExceptionHandler originHandler = Thread.currentThread()
            .getUncaughtExceptionHandler();
        // check to prevent set again
        if (this != originHandler) {
            this.originHandler = originHandler;
            Thread.currentThread().setUncaughtExceptionHandler(this);
            Thread.setDefaultUncaughtExceptionHandler(this);
        }
    }


    private boolean handleException(Throwable throwable) {
        boolean success = saveToFile(throwable);
        try {
            startCatchActivity(throwable);
            byeByeLittleWood();
        } catch (Exception e) {
            success = false;
        }
        return success;
    }


    private void byeByeLittleWood() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }


    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        // Don't re-enter,  avoid infinite loops if crash-handler crashes.
        if (crashing) {
            return;
        }
        crashing = true;

        // pass it to interceptor's before method
        UncaughtExceptionInterceptor interceptor = this.interceptor;
        if (interceptor != null &&
            interceptor.onInterceptExceptionBefore(thread, throwable)) {
            return;
        }

        boolean isHandle = handleException(throwable);

        // pass it to interceptor's after method
        if (interceptor != null &&
            interceptor.onInterceptExceptionAfter(thread, throwable)) {
            return;
        }

        if ((forceHandleByOrigin || !isHandle) && originHandler != null) {
            originHandler.uncaughtException(thread, throwable);
        }
    }


    /**
     * For setting more highlight keys except package name
     *
     * @param keys highlight keys except package name
     * @return itself
     */
    public CrashWoodpecker withKeys(final String... keys) {
        this.keys.addAll(Arrays.asList(keys));
        return this;
    }


    /**
     * Set uncaught exception interceptor.
     *
     * @param interceptor uncaught exception interceptor.
     * @return itself
     */
    public CrashWoodpecker setInterceptor(UncaughtExceptionInterceptor interceptor) {
        this.interceptor = interceptor;
        return this;
    }


    /**
     * Delete outmoded logs.
     */
    public void deleteLogs() {
        deleteLogs(LOG_OUT_TIME);
    }


    /**
     * Delete outmoded logs.
     *
     * @param timeout outmoded timeout.
     */
    public void deleteLogs(final long timeout) {
        final File logDir = new File(getCrashDir());
        try {
            final long currTime = System.currentTimeMillis();
            File[] files = logDir.listFiles(new FilenameFilter() {
                @Override public boolean accept(File dir, String filename) {
                    File f = new File(dir, filename);
                    return currTime - f.lastModified() > timeout;
                }
            });
            if (files != null) {
                for (File f : files) {
                    FileUtils.delete(f);
                }
            }
        } catch (Exception e) {
            Log.v(TAG, "exception occurs when deleting outmoded logs", e);
        }
    }


    private String getCrashDir() {
        String rootPath = Environment.getExternalStorageDirectory().getPath();
        return rootPath + "/CrashWoodpecker/";
    }


    private void startCatchActivity(Throwable throwable) {
        String traces = getStackTrace(throwable);
        Intent intent = new Intent();
        intent.setClass(context, CatchActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String[] strings = traces.split("\n");
        String[] logs = new String[strings.length];
        for (int i = 0; i < strings.length; i++) {
            logs[i] = strings[i].trim();
        }
        intent.putStringArrayListExtra(CatchActivity.EXTRA_HIGHLIGHT_KEYS, keys);
        intent.putExtra(CatchActivity.EXTRA_APPLICATION_NAME, getApplicationName(context));
        intent.putExtra(CatchActivity.EXTRA_CRASH_LOGS, logs);
        intent.putExtra(CatchActivity.EXTRA_CRASH_4_LOGCAT, Log.getStackTraceString(throwable));
        context.startActivity(intent);
    }


    private String getStackTrace(Throwable throwable) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        throwable.printStackTrace(printWriter);
        printWriter.close();
        return writer.toString();
    }


    private boolean saveToFile(Throwable throwable) {
        String time = FORMATTER.format(new Date());
        String fileName = "Crash-" + time + ".log";
        String crashDir = getCrashDir();
        String crashPath = crashDir + fileName;

        String androidVersion = Build.VERSION.RELEASE;
        String deviceModel = Build.MODEL;
        String manufacturer = Build.MANUFACTURER;

        File file = new File(crashPath);
        if (file.exists()) {
            file.delete();
        } else {
            try {
                new File(crashDir).mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                return false;
            }
        }

        PrintWriter writer;
        try {
            writer = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            return false;
        }
        writer.write("Device: " + manufacturer + ", " + deviceModel + "\n");
        writer.write("Android Version: " + androidVersion + "\n");
        if (version != null) writer.write("App Version: " + version + "\n");
        writer.write("---------------------\n\n");
        throwable.printStackTrace(writer);
        writer.close();

        return true;
    }


    public String getApplicationName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo = null;
        String name = null;
        try {
            applicationInfo = packageManager.getApplicationInfo(
                context.getApplicationInfo().packageName, 0);
            name = (String) packageManager.getApplicationLabel(applicationInfo);
        } catch (final PackageManager.NameNotFoundException e) {
            String[] packages = context.getPackageName().split(".");
            name = packages[packages.length - 1];
        }
        return name;
    }


    public interface UncaughtExceptionInterceptor {
        /**
         * Called before this uncaught exception be handled by {@link
         * CrashWoodpecker}.
         *
         * @return true if intercepted, which means this event won't be handled
         * by {@link CrashWoodpecker}.
         */
        boolean onInterceptExceptionBefore(Thread t, Throwable ex);

        /**
         * Called after this uncaught exception be handled by
         * {@link CrashWoodpecker} (but before {@link CrashWoodpecker}'s
         * parent).
         *
         * @return true if intercepted, which means this event won't be handled
         * by {@link CrashWoodpecker}'s parent.
         */
        boolean onInterceptExceptionAfter(Thread t, Throwable ex);
    }
}
