//
// Created by baiiu on 2020/5/30.
//

#include <jni.h>
#include <string>
#include <people.h>
#include <log.h>

extern "C"
JNIEXPORT void JNICALL
Java_com_baiiu_jnitest_exception_ExceptionFragment_nativeInvokeJavaException(JNIEnv *env,
                                                                             jobject thiz) {

    jclass jclazz = env->GetObjectClass(thiz);
    jmethodID mid = env->GetMethodID(jclazz, "operation", "()I");
    jint result = env->CallIntMethod(thiz, mid);

    // 有异常发生时catch住
    jthrowable thr = env->ExceptionOccurred();
    if (thr) {
        env->ExceptionDescribe();
        env->ExceptionClear();
    }

    LOGD("result: %d", result);
}


extern "C"
JNIEXPORT void JNICALL
Java_com_baiiu_jnitest_exception_ExceptionFragment_nativeThrowException(JNIEnv *env, jobject thiz) {
    jclass clazz = env->FindClass("java/lang/Exception");

    env->ThrowNew(clazz, "native throw exception");
}