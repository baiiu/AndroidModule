//
// Created by baiiu on 2020/12/16.
//
#include <jni.h>
#include <log.h>
#include <unistd.h>


int function(int n) {
    if (n == 1) return 1;
    if (n == 2) return 1;

    return function(n - 1) + function(n - 2);
}


extern "C"
JNIEXPORT jboolean JNICALL
Java_com_baiiu_jnitest_testSocket_TestSocketFragment_native_1connect(JNIEnv *env, jobject thiz) {
    __android_log_print(ANDROID_LOG_ERROR, "mLogU", "start connect: %p， %p", &env, env);

//    int result = function(1000000000);
    int result = function(5);

    __android_log_print(ANDROID_LOG_ERROR, "mLogU", "end connect: %p， %p, %d", &env, env, result);
    return true;
}