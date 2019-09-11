package com.example.testing.rxjavalearn.rxEncapsulated;

import android.text.TextUtils;
import com.example.testing.rxjavalearn.util.LogUtil;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by baiiu on 15/11/25.
 * 简单的通用util
 */
public class CommonUtil {

    //必须在外面传入引用,不能传null
    public static <T> void copyList(List<? super T> destination, List<? extends T> source) {
        destination.clear();
        destination.addAll(source);
    }


    public static void clear(List list) {
        if (list == null) return;

        list.clear();
        list = null;
    }


    public static boolean isEmpty(List list) {
        return list == null || list.isEmpty();
    }

    public static boolean notEmpty(List list) {
        return !isEmpty(list);
    }

    //全部为空
    public static boolean isAllEmpty(List... lists) {
        boolean allEmpty;

        for (List list : lists) {
            allEmpty = isEmpty(list);

            if (!allEmpty) {
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


    //====================================================================
    //====================================================================

    public static boolean isEmpty(String s) {
        return s == null || TextUtils.isEmpty(s.trim());
    }

    public static boolean isEmpty1(String s) {
        return s == null || TextUtils.isEmpty(s.trim());
    }

    public static boolean notEmpty(String s) {
        return s != null && !TextUtils.isEmpty(s.trim());
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

    // s是list里面的一个
    public static boolean isOneEquals(String content, List<String> list) {
        if (CommonUtil.isEmpty(content) || CommonUtil.isEmpty(list)) {
            return false;
        }
        for (String s : list) {
            if (content.equals(s)) {
                return true;
            }
        }

        return false;
    }

    public static String pidReplace(String pid) {
        if (TextUtils.isEmpty(pid)) {
            return "";
        }

        if (pid.contains("/")) {
            pid = pid.replaceFirst("/", "");
        }

        return pid;
    }

    public static String toEmptyString(String text) {
        return TextUtils.isEmpty(text) ? "" : text;
    }


    //===========================================================

    public static boolean isNotResponse(ApiResponse apiResponse) {
        return apiResponse == null || apiResponse.code != 0 || apiResponse.data == null;
    }


    public static long mLastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - mLastClickTime;
        if (0 < timeD && timeD < 500) {
            return true;
        }
        mLastClickTime = time;
        return false;
    }

    /**
     * 只encode中文
     */
    public static String encode(String sourceString) {
        if (TextUtils.isEmpty(sourceString)) return null;
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

}
