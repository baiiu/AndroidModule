//
// Created by baiiu on 2020/5/30.
//

#include <jni.h>
#include <log.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_baiiu_jnitest_reference_ReferenceFragment_localReference(JNIEnv *env, jobject thiz) {
    // 局部引用，方法执行完后结束
    jclass localRef = env->FindClass("java/lang/String");
    jmethodID mid = env->GetMethodID(localRef, "<init>", "(Ljava/lang/String;)V");

    jstring str = env->NewStringUTF("local reference string");

    for (int i = 0; i < 1000; ++i) {
        jclass jclazz = env->FindClass("java/lang/String");
        env->DeleteLocalRef(jclazz); // 可以手动释放
    }

    return static_cast<jstring>(env->NewObject(localRef, mid, str));
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_baiiu_jnitest_reference_ReferenceFragment_globalReference(JNIEnv *env, jobject thiz) {
    static jclass stringClass = nullptr;
    if (stringClass == nullptr) {
        jclass jclazz = env->FindClass("java/lang/String");
        stringClass = static_cast<jclass>(env->NewGlobalRef(jclazz)); // 创建全局引用
        env->DeleteLocalRef(jclazz); // 释放局部引用
    } else {
        LOGD("cached");
    }

    jmethodID mid = env->GetMethodID(stringClass, "<init>", "(Ljava/lang/String;)V");
    jstring str = env->NewStringUTF("global reference string");

    return static_cast<jstring>(env->NewObject(stringClass, mid, str));
}


extern "C"
JNIEXPORT jstring JNICALL
Java_com_baiiu_jnitest_reference_ReferenceFragment_weakReference(JNIEnv *env, jobject thiz) {
    static jclass stringClass = nullptr;
    if (stringClass == nullptr) {
        jclass clazz = env->FindClass("java/lang/String");
        stringClass = static_cast<jclass>(env->NewWeakGlobalRef(clazz));
        env->DeleteLocalRef(clazz);
    } else {
        LOGD("cached");
    }

    if (env->IsSameObject(stringClass, nullptr)) {
        LOGD("weak");
        return nullptr;
    } else {
        jmethodID mid = env->GetMethodID(stringClass, "<init>", "(Ljava/lang/String;)V");
        jstring str = env->NewStringUTF("weak reference string");
        jobject obj = env->NewObject(stringClass, mid, str);
        LOGD("%s", *obj);

        return static_cast<jstring>(obj);
    }


}

