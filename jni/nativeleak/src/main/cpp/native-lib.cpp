//
// Created by baiiu on 2021/5/20.
//

#include "jni.h"
#include <android/log.h>
#include "xhook/xhook.h"

#define ARRAY_LENGTH(a)  (sizeof(a) / sizeof(a[0]))

void testLeakHook(JNIEnv *env, jobject thiz) {
    __android_log_print(ANDROID_LOG_DEBUG, "mLogU", "no leak, fix it");
}

static void xhook_init(JNIEnv *env, jobject thiz) {
    xhook_register(".*test-lib.so", "Java_com_baiiu_nativeleak_MainActivity_testLeak",
                   (void *) testLeakHook,
                   NULL);
    xhook_refresh(0);
}

static JNINativeMethod methods[] = {
        {"_init", "()V", (void *) (xhook_init)}
};

JNIEXPORT int JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env;

    if (vm->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_6) != JNI_OK) {
        return JNI_ERR;
    }

    jclass clazz = env->FindClass("com/baiiu/nativeleak/XHook");
    if (clazz == NULL) {
        return JNI_ERR;
    }

    if (env->RegisterNatives(clazz, methods, ARRAY_LENGTH(methods))) {
        return JNI_ERR;
    }

    return JNI_VERSION_1_6;
}