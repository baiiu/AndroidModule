#include <jni.h>
#include <log.h>

//
// Created by baiiu on 2020/5/23.
//

extern "C"
JNIEXPORT jstring JNICALL
Java_com_baiiu_jnitest_string_StringTestFragment_callNativeString(JNIEnv *env, jobject thiz,
                                                                  jstring jStr) {

    // java str 转 native str
    const char *str = env->GetStringUTFChars(jStr, JNI_FALSE);

    LOGD("java str is: %s, %p, java is %p, %p", str, &str, jStr, &jStr);

    // 回收
    env->ReleaseStringUTFChars(jStr, str);

    const char *s = "str from c++";
    LOGD("java str is: %s , %p", s, &s);

    return env->NewStringUTF(s);

}

extern "C"
JNIEXPORT void JNICALL
Java_com_baiiu_jnitest_string_StringTestFragment_stringMethod(JNIEnv *env, jobject thiz,
                                                              jstring jStr) {

    const char *str = env->GetStringUTFChars(jStr, JNI_FALSE);
    int len = env->GetStringLength(jStr);

    LOGD("jStr length: %d", len);

    char buf[1024];
    env->GetStringUTFRegion(jStr, 0, len - 1, buf);
    LOGD("jStr region is %s", buf);

    env->ReleaseStringUTFChars(jStr, str);

}

extern "C"
JNIEXPORT void JNICALL
Java_com_baiiu_jnitest_string_StringTestFragment_callJava(JNIEnv *env, jobject thiz) {

    jclass clazz = env->FindClass("com/baiiu/jnitest/string/StringTestFragment");
    jmethodID methodId = env->GetStaticMethodID(clazz, "decorateUrl",
                                                "(Ljava/lang/String;)Ljava/lang/String;");

    jstring jstr = env->NewStringUTF("test");
    jobject decorateObject = env->CallStaticObjectMethod(clazz, methodId, jstr);

    const char *afterUrl = env->GetStringUTFChars(static_cast<jstring>(decorateObject), JNI_FALSE);
    LOGD("afterUrl %s", afterUrl);

    env->ReleaseStringUTFChars(jstr, afterUrl);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_baiiu_jnitest_string_StringTestFragment_callJavaFree(JNIEnv *env, jobject thiz) {

    jclass clazz = env->FindClass("com/baiiu/jnitest/string/StringTestFragment");
    jmethodID methodId = env->GetMethodID(clazz, "decorateUrlFree",
                                          "(Ljava/lang/String;)Ljava/lang/String;");

    jstring jstr = env->NewStringUTF("test");
    jobject decorateObject = env->CallObjectMethod(thiz, methodId, jstr);

    if (decorateObject) {
        const char *afterUrl = env->GetStringUTFChars(static_cast<jstring>(decorateObject),
                                                      JNI_FALSE);
        LOGD("afterUrl %s", afterUrl);

        env->ReleaseStringUTFChars(jstr, afterUrl);
    }
}