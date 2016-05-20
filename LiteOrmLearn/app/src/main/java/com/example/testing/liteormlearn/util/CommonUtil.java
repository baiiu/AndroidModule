package com.example.testing.liteormlearn.util;

import android.os.Looper;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by baiiu on 15/11/25.
 * 简单的通用util
 */
public class CommonUtil {

    //============================集合========================================
    public static boolean isEmpty(List list) {
        return list == null || list.isEmpty();
    }

    //全部为空
    public static boolean isAllEmpty(List... lists) {
        for (List list : lists) {
            if (!isEmpty(list)) {
                return false;
            }
        }

        return true;
    }

    //有一个为空
    public static boolean isOneEmpty(List... lists) {
        for (List list : lists) {
            if (isEmpty(list)) {
                return true;
            }
        }

        return false;
    }


    //============================字符串============================================
    public static boolean isEmpty(String s) {
        return s == null || TextUtils.isEmpty(s);
    }

    //全部为空
    public static boolean isAllEmpty(String... strings) {
        for (String s : strings) {

            if (!isEmpty(s)) {
                //如果有一个不为空则返回false.
                return false;
            }

        }

        return true;
    }

    //有一个为空
    public static boolean isOneEmpty(String... strings) {
        for (String s : strings) {
            if (isEmpty(s)) {
                return true;
            }
        }

        return false;
    }


    //============================URL============================================

    /**
     * 只encode中文
     */
    public static String encode(String sourceString) {
        Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]+");
        Matcher matcher = pattern.matcher(sourceString);
        while (matcher.find()) {
            String s1 = matcher.group();
            sourceString = sourceString.replace(s1, urlEncode(s1));
        }

        return sourceString;
    }


    public static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LogUtil.e(e.toString());
            return URLEncoder.encode(s);
        }
    }

    public static String urlDecode(String s) {
        try {
            return URLDecoder.decode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return URLDecoder.decode(s);
        }
    }

    //============================ELSE============================================

    //生存uuid
    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    //判断是否在主线程
    public boolean isUIThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }


}
