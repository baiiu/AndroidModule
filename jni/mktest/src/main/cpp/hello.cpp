//
// Created by baiiu on 2020/9/8.
//

#include <jni.h>

extern "C" JNIEXPORT jstring JNICALL
Java_com_baiiu_mktest_MainActivity_stringFromJNI(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF("test");
}
