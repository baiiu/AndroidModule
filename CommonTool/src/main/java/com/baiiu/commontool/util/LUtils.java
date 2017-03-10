/*
 * Copyright 2014 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.baiiu.commontool.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class LUtils {

    protected Activity mActivity;
    private volatile static LUtils singleton;

    private LUtils(Activity activity) {
        mActivity = activity;
    }

    public static LUtils instance(Activity activity) {
        if (singleton == null) {
            synchronized (LUtils.class) {
                if (singleton == null) {
                    singleton = new LUtils(activity);
                }
            }
        }
        singleton.setActivity(activity);
        return singleton;
    }

    public void setActivity(Activity activity) {
        this.mActivity = activity;
    }

    public void clear() {
        this.mActivity = null;
        singleton = null;
    }

    public int getStatusBarColor() {
        if (Version.belowKitKat()) {
            // On pre-kitKat devices, you can have any status bar color so long as it's black.
            return Color.BLACK;
        }

        if (Version.hasLollipop()) {
            return mActivity.getWindow().getStatusBarColor();
        }

        if (Version.hasKitKatAndUnderLollipop()) {
            ViewGroup contentView = (ViewGroup) mActivity.findViewById(android.R.id.content);
            View statusBarView = contentView.getChildAt(0);
            if (statusBarView != null && statusBarView.getMeasuredHeight() == ScreenUtil.getStatusHeight()) {
                Drawable drawable = statusBarView.getBackground();
                if (drawable != null) {
                    return ((ColorDrawable) drawable).getColor();
                }
            }
        }

        return -1;
    }

    public void setStatusBarColor(int color) {
        if (Version.belowKitKat()) {
            return;
        }

        if (Version.hasLollipop()) {
            mActivity.getWindow().setStatusBarColor(color);
            return;
        }

        if (Version.hasKitKatAndUnderLollipop()) {
            ViewGroup contentView = (ViewGroup) mActivity.findViewById(android.R.id.content);

            View statusBarView = contentView.getChildAt(0);
            //改变颜色时避免重复添加statusBarView
            if (statusBarView != null && statusBarView.getMeasuredHeight() == ScreenUtil.getStatusHeight()) {
                statusBarView.setBackgroundColor(color);
                return;
            }
            statusBarView = new View(mActivity);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtil.getStatusHeight());
            statusBarView.setBackgroundColor(color);
            contentView.addView(statusBarView, 0, lp);
        }
    }

    private static final int[] THEME_ATTRS = {
            android.R.attr.colorPrimaryDark,
            android.R.attr.windowTranslucentStatus
    };


    public static int getDefaultStatusBarBackground(Context context) {
        final TypedArray a = context.obtainStyledAttributes(THEME_ATTRS);
        try {
            return a.getColor(0, Color.TRANSPARENT);
        } finally {
            a.recycle();
        }
    }

    public static boolean getWindowTranslucentStatus(Context context) {
        final TypedArray a = context.obtainStyledAttributes(THEME_ATTRS);

        try {
            return a.getBoolean(1, false);
        } finally {
            a.recycle();
        }
    }
}
