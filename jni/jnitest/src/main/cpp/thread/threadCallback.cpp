#include <jni.h>
#include <pthread.h>
#include <log.h>

//
// Created by baiiu on 2020/5/24.
//

extern "C"
JNIEXPORT void JNICALL
Java_com_baiiu_jnitest_thread_ThreadCallbackFragment_nativeCallBack(JNIEnv *env, jobject thiz,
                                                                    jobject call_back) {

    jclass clazz = env->GetObjectClass(call_back);
    jmethodID methodId = env->GetMethodID(clazz, "onCallBack", "()V");
    env->CallVoidMethod(call_back, methodId);

}

JavaVM *gVm;

JNIEXPORT int JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env;

    if (vm->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_6) != JNI_OK) {
        return JNI_FALSE;
    }

    gVm = vm;

    return JNI_VERSION_1_6;
}

jobject threadObject;
jclass threadClazz;
jmethodID threadMethod;

void *threadCallBack(void *) {
    JNIEnv *env;

    if (gVm->AttachCurrentThread(&env, nullptr) == JNI_OK) {

        env->CallVoidMethod(threadObject, threadMethod);

        gVm->DetachCurrentThread();
    }

    return 0;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_baiiu_jnitest_thread_ThreadCallbackFragment_nativeThreadCallBack(JNIEnv *env, jobject thiz,
                                                                          jobject call_back) {

    threadObject = env->NewGlobalRef(call_back);
    threadClazz = env->GetObjectClass(call_back);
    threadMethod = env->GetMethodID(threadClazz, "onCallBack", "()V");

    // 创建线程
    pthread_t handle;
    pthread_create(&handle, nullptr, threadCallBack, nullptr);
}
