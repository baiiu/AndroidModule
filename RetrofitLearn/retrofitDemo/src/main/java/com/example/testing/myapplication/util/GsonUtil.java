package com.example.testing.myapplication.util;


import android.text.TextUtils;
import com.baiiu.library.LogUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import org.json.JSONObject;

/**
 * Created by baiiu on 15/11/10.
 * json解析类
 */
public class GsonUtil {
    public final static Gson gson = new GsonBuilder().serializeNulls()
            .create();


    public static <T> T parseJson(String json, Class<T> clazz) {
        T t = null;

        try {
            t = gson.fromJson(json, clazz);
        } catch (Exception e) {
            LogUtil.e(e.toString());
        }

        return t;
    }

    public static <T> T parseJson(String json, Type typeOfT) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }

        T t = null;

        try {
            t = gson.fromJson(json, typeOfT);
        } catch (Exception e) {
            LogUtil.e(e.toString());
        }

        return t;
    }

    public static boolean parseValue(String json, String key) {
        try {
            JSONObject object = new JSONObject(json);
            return object.optBoolean(key);
        } catch (Exception e) {
            LogUtil.e(e.toString());
        }

        return false;
    }

    public static String parseStrValue(String json, String key) {
        try {
            JSONObject object = new JSONObject(json);
            return object.optString(key);
        } catch (Exception e) {
            LogUtil.e(e.toString());
        }
        return "";
    }

    public static int parseIntValue(String json, String key) {
        try {
            JSONObject object = new JSONObject(json);
            return object.optInt(key);
        } catch (Exception e) {
            LogUtil.e(e.toString());
        }

        return 0;
    }

    public static String toJson(Object obj) {
        return obj == null ? null : gson.toJson(obj);
    }

    public static List<String> parseArray(String s) {
        Type type = new TypeToken<List<String>>() {}.getType();

        return parseJson(s, type);
    }
}
