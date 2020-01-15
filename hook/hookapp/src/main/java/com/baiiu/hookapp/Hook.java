package com.baiiu.hookapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import com.baiiu.library.LogUtil;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * author: zhuzhe
 * time: 2020-01-15
 * description:
 */
public class Hook {


    static void hook(Activity activity) throws Exception {
        Class<?> aClass = Class.forName("android.app.ActivityThread");

        Method currentActivityThreadMethod = aClass.getDeclaredMethod("currentActivityThread");
        currentActivityThreadMethod.setAccessible(true);
        Object currentActivityThread = currentActivityThreadMethod.invoke(null);


        Field mInstrumentationField = aClass.getDeclaredField("mInstrumentation");
        mInstrumentationField.setAccessible(true);
        Object mInstrumentation = mInstrumentationField.get(currentActivityThread);


        EvilInstrumentation evilInstrumentation = new EvilInstrumentation(mInstrumentation);
        mInstrumentationField.set(currentActivityThread, evilInstrumentation);

        try {
            Field field = Activity.class.getDeclaredField("mInstrumentation");
            field.setAccessible(true);
            field.set(activity, evilInstrumentation);
        } catch (Exception e) {
            LogUtil.e("Hook#hook: " + e.toString());
        }
    }

    private static class EvilInstrumentation extends Instrumentation {
        Object mInstrumentation;

        EvilInstrumentation(Object instrumentation) {
            mInstrumentation = instrumentation;
        }

        public ActivityResult execStartActivity(Context who, IBinder contextThread, IBinder token,
                Activity target, Intent intent, int requestCode, Bundle options) {

            LogUtil.e("before EvilInstrumentation#execStartActivity");

            try {
                Method execStartActivity =
                        Instrumentation.class.getDeclaredMethod("execStartActivity", Context.class,
                                                                IBinder.class, IBinder.class,
                                                                Activity.class, Intent.class,
                                                                int.class,
                                                                Bundle.class);

                return (ActivityResult) execStartActivity.invoke(mInstrumentation, who,
                                                                 contextThread, token,
                                                                 target,
                                                                 intent, requestCode, options);
            } catch (Exception e) {
                LogUtil.e("EvilInstrumentation#execStartActivity: " + e.toString());
                throw new RuntimeException(e);
            } finally {
                LogUtil.e("after EvilInstrumentation#execStartActivity");
            }
        }
    }

}
