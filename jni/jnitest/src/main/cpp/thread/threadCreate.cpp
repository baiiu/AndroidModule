//
// Created by baiiu on 2020/5/30.
//

#include <jni.h>
#include <string>
#include <people.h>
#include <log.h>
#include <pthread.h>
#include <unistd.h>

void *threadPrintHello(void *) {
    LOGD("hello thread");
    return 0;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_baiiu_jnitest_thread_ThreadCreateFragment_createNativeThread(JNIEnv *env, jobject thiz) {
    pthread_t handles;
    int result = pthread_create(&handles, nullptr, threadPrintHello, nullptr);

    LOGD("createNativeThread: %d", result);
}

struct ThreadArgs {
    int id;
    const char *s;
};

void *threadWithArgs(void *arg) {
    ThreadArgs *args = static_cast<ThreadArgs *>(arg);
    LOGD("thread args id is: %d", args->id);
    LOGD("thread args str is: %s", args->s);

    return 0;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_baiiu_jnitest_thread_ThreadCreateFragment_createNativeThreadWithArgs(JNIEnv *env,
                                                                              jobject thiz) {
    pthread_t handles;
    ThreadArgs *args = new ThreadArgs;
    args->id = 1;
    args->s = "hello thread";

    int result = pthread_create(&handles, nullptr, threadWithArgs, args);
    LOGD("createNativeThreadWithArgs: %d", result);
}

void *threadCostTime(void *) {
    LOGD("threadCostTime start");
    struct timeval begin;
    gettimeofday(&begin, nullptr);

    sleep(3);

    struct timeval end;
    gettimeofday(&end, nullptr);

    LOGD("threadCostTime end: %d", (end.tv_sec - begin.tv_sec));
    return (void *) "s";
}

extern "C"
JNIEXPORT void JNICALL
Java_com_baiiu_jnitest_thread_ThreadCreateFragment_joinNativeThread(JNIEnv *env, jobject thiz) {
    pthread_t handles;
    int result = pthread_create(&handles, nullptr, threadCostTime, nullptr);
    LOGD("joinNativeThread: %d", result);

    void *ret = nullptr;
    pthread_join(handles, &ret);
    LOGD("join result is: %s", ret);
}