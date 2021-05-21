//
// Created by baiiu on 2021/5/20.
//

#include "jni.h"
#include <android/log.h>
#include <malloc.h>
#include <cstring>
#include "xhook/xhook.h"

#define SELF "leak-monitor-lib"

#define ARRAY_LENGTH(a)  (sizeof(a) / sizeof(a[0]))
#define SOLOAD_SO ".*libnativeloader\\.so$"
#define SOLOAD_SYMBOL "android_dlopen_ext"

void *malloc_hook(size_t _byte_count) {
    void *ptr = malloc(_byte_count);
    __android_log_print(ANDROID_LOG_DEBUG, "mLogU", "malloc_hook, __byte_count: %d, ptr: %p",
                        _byte_count, ptr);
    return ptr;
}

void free_hook(void *_ptr) {
    free(_ptr);
    __android_log_print(ANDROID_LOG_DEBUG, "mLogU", "free_hook, ptr: %p", _ptr);
}

void on_so_loaded(const char *filename) {
    xhook_register(filename, "malloc", (void *) malloc_hook, NULL);
    xhook_register(filename, "free", (void *) free_hook, NULL);
    xhook_refresh(0);
}

void *(*android_dlopen_ext_origin)(const char *filename, int flag, const void *extinfo);

void *android_dlopen_ext(const char *filename, int flag, const void *extinfo) {
    void *result = android_dlopen_ext_origin(filename, flag, extinfo);

    if (strstr(filename, SELF)) {
        return result;
    }

    __android_log_print(ANDROID_LOG_DEBUG, "mLogU", "android_dlopen_ext hook, filename: %s",
                        filename);
    on_so_loaded(filename);
    return result;
}

static void xhook_init(JNIEnv *env, jobject thiz) {
    xhook_register(SOLOAD_SO, SOLOAD_SYMBOL, (void *) android_dlopen_ext,
                   (void **) (&android_dlopen_ext_origin));
    xhook_refresh(0);
}

void leakItHook(JNIEnv *env, jobject thiz) {
    __android_log_print(ANDROID_LOG_DEBUG, "mLogU", "no leak, fix it");
}

static void xhook_fixLeak(JNIEnv *env, jobject thiz) {
    // 注释do not register hook after refresh()，允许重复register
    // 在on_so_loaded方法hook malloc方法后，此处不生效了
    int ret = xhook_register(".*libtest-lib\\.so", "_Z6leakItv", (void *) leakItHook, NULL);
    __android_log_print(ANDROID_LOG_DEBUG, "mLogU", "xhook_fixLeak: %d", ret);
    xhook_refresh(0);
}

static JNINativeMethod methods[] = {
        {"_init",    "()V", (void *) xhook_init},
        {"_fixLeak", "()V", (void *) xhook_fixLeak},
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