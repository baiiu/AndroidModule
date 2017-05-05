package com.example.testing.liteormlearn.orm;

import android.content.Context;
import com.example.testing.liteormlearn.MyApplcation;

/**
 * author: baiiu
 * date: on 16/5/18 18:55
 * description:
 */
public interface ApiConstant {
  String DB_NAME_PATH = MyApplcation.mContext.getDir("db", Context.MODE_PRIVATE).getAbsolutePath();
  String DB_NAME = "uOrm.db";
}
