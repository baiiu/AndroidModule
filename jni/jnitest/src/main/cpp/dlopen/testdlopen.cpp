//
// Created by baiiu on 2020/12/24.
//
#include <dlfcn.h>
#include <string.h>
#include <cstdlib>
#include "jni.h"
#include "log.h"

extern "C"
JNIEXPORT void JNICALL
Java_com_baiiu_jnitest_dlopen_DLOpenFragment_testdlopen(JNIEnv *env, jobject thiz, jstring jstr) {
    const char *soPath = env->GetStringUTFChars(jstr, JNI_FALSE);

    LOGD("native--> soPath: %s", soPath);
    void *handle = dlopen(soPath, RTLD_NOW);

    char *error_msg = nullptr;
    if (handle == nullptr) {
        error_msg = strdup(dlerror());
        LOGD("%s", error_msg);
    }

    LOGD("handle is %p", handle);

//    void *sym = dlsym(handle, "fibonacci"); // readelf find real funcName
    void *sym = dlsym(handle, "_Z9fibonaccii");
    if (sym == nullptr) {
        LOGD("can not find the function");
        return;
    }

    using FIBONACCI = int (*)(int);
    FIBONACCI fibonacci = reinterpret_cast<FIBONACCI>(sym);
    int result = (*fibonacci)(5);

    LOGD("fibonacci result is: %d", result);

    dlclose(handle);

    env->ReleaseStringUTFChars(jstr, soPath);

}