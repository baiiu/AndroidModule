package com.example.testing.liteormlearn.orm;

import android.database.sqlite.SQLiteDatabase;
import com.example.testing.liteormlearn.BuildConfig;
import com.example.testing.liteormlearn.MyApplcation;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBaseConfig;
import com.litesuits.orm.db.assit.SQLiteHelper;
import java.io.File;

/**
 * author: baiiu
 * date: on 16/5/18 17:43
 * description:
 */
public enum UOrm implements SQLiteHelper.OnUpdateListener, ApiContant {
  INSTANCE;

  private LiteOrm mLiteOrm;

  UOrm() {
    DataBaseConfig config = new DataBaseConfig(MyApplcation.mContext);
    config.dbName = DB_NAME_PATH + File.separator + DB_NAME;
    LogUtil.d(DB_NAME);
    config.dbVersion = 1;
    config.onUpdateListener = this;
    config.debugged = BuildConfig.DEBUG;
    mLiteOrm = LiteOrm.newSingleInstance(config);
  }

  @Override public void onUpdate(SQLiteDatabase sqLiteDatabase, int i, int i1) {

  }

  public LiteOrm getLiteOrm() {
    return mLiteOrm;
  }
}
