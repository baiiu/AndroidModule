package com.baiiu.commontool.util.store;

import android.content.Context;
import android.content.SharedPreferences;
import com.baiiu.commontool.app.UIUtil;
import java.util.WeakHashMap;

/**
 * 配置管理,如果是写入，必须在写完后手动commit
 * 多个写入，建议串联操作
 * <p>
 * <a href="http://blog.csdn.net/u014099894/article/details/50947248">文章链接</a>
 */
public class PreferenceUtil {
    private static WeakHashMap<String, PreferenceUtil> preferenceManagerWeakHashMap;
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;

    private static final String defaultFile = "default";

    private PreferenceUtil(String fileName) {
        settings = UIUtil.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        editor = settings.edit();
    }

    /**
     * 获取默认的sp
     */
    public synchronized static PreferenceUtil instance() {
        return PreferenceUtil.instance(defaultFile);
    }

    /**
     * 获取指定的sp
     */
    public synchronized static PreferenceUtil instance(String fileName) {
        String packageName = UIUtil.getPackageName();
        if (!fileName.startsWith(packageName)) {
            fileName = packageName + "." + fileName;
        }

        if (preferenceManagerWeakHashMap == null) {
            preferenceManagerWeakHashMap = new WeakHashMap<>();
        }

        if (preferenceManagerWeakHashMap.size() > 10) {
            preferenceManagerWeakHashMap.clear();
        }

        if ((preferenceManagerWeakHashMap.get(fileName) == null || !preferenceManagerWeakHashMap.containsKey(fileName))) {
            PreferenceUtil manager = new PreferenceUtil(fileName);
            preferenceManagerWeakHashMap.put(fileName, manager);
        }
        return preferenceManagerWeakHashMap.get(fileName);
    }

    /**
     * 写入字符串，所有写入操作执行完后，必须commit
     */
    public PreferenceUtil put(String key, String value) {
        editor.putString(key, value);
        return this;
    }

    public PreferenceUtil put(String key, long value) {
        editor.putLong(key, value);
        return this;
    }

    /**
     * 写入boolean，所有写入操作执行完后，必须commit
     */
    public PreferenceUtil put(String key, boolean value) {
        editor.putBoolean(key, value);
        return this;
    }

    /**
     * 写入int，所有写入操作执行完后，必须commit
     */
    public PreferenceUtil put(String key, int value) {
        editor.putInt(key, value);
        return this;
    }

    /**
     * 删除key
     */
    public PreferenceUtil remove(String key) {
        editor.remove(key);
        return this;
    }

    public String get(String key, String defVal) {
        return settings.getString(key, defVal);
    }

    public boolean get(String key, boolean defVal) {
        return settings.getBoolean(key, defVal);
    }

    public int get(String key, int defVal) {
        return settings.getInt(key, defVal);
    }

    public long get(String key, long defVal) {
        return settings.getLong(key, defVal);
    }

    /**
     * 保存写入
     */
    public void commit() {
        if (editor != null) {
            try {
                editor.apply();
            } catch (AbstractMethodError unused) {
                editor.commit();
            }
        }
    }

    /**
     * 清除所有配置
     */
    public void clear() {
        editor.clear();
        commit();
    }

    public SharedPreferences getSettings() {
        return settings;
    }

    //    public PreferenceManager putObject(String key, Object value) {
//        if (value != null) {
//            String str = GsonUtil.toJson(value);
//            editor.putString(key, str);
//        } else {
//            editor.remove(key);
//        }
//        return this;
//    }
//
//    public <T> T getObject(String key, Class<T> classOfT) {
//        String str = settings.getString(key, null);
//        if (TextUtils.isEmpty(str))
//            return null;
//        try {
//            T t = GsonUtil.parseJson(str, classOfT);
//            return t;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
}