package com.example.testing.liteormlearn;

import android.app.Application;
import android.content.Context;

/**
 * author: baiiu
 * date: on 16/5/18 17:44
 * description:
 */
public class MyApplcation extends Application {

  public static Context mContext;

  @Override public void onCreate() {
    super.onCreate();
    mContext = this;
  }
}
