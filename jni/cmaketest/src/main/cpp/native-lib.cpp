//
// Created by baiiu on 2021/4/19.
//

#include "jni.h"
#include <android/log.h>

#ifdef ENABLE_FIBO

#include "fibo.h"

#endif


void testBoolean(bool rtt) {
    __android_log_print(ANDROID_LOG_ERROR, "mLogU", "testBoolean: %d", rtt);
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_baiiu_cmaketest_MainActivity_hello(JNIEnv *env, jobject thiz) {
#ifdef ENABLE_LOG
    __android_log_print(ANDROID_LOG_ERROR, "mLogU", "log");
    return env->NewStringUTF("hello with log");
#endif

#ifdef ENABLE_FIBO
    // 1 1 2 3 5 8
    __android_log_print(ANDROID_LOG_ERROR, "mLogU", "log: %d", test(5));
#endif

    jclass cls = env->GetObjectClass(thiz);

    jmethodID callJavaMethodId = env->GetMethodID(cls, "cCallJava", "(ZJ)V");
    jboolean b = 199; // 报错
    jlong l = 1;

    testBoolean(199);
    env->CallVoidMethod(thiz, callJavaMethodId, b ? JNI_TRUE : JNI_FALSE, l);


    return env->NewStringUTF("hello");
}