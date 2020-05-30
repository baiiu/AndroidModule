//
// Created by baiiu on 2020/5/30.
//
#include <jni.h>
#include <string>
#include <people.h>
#include <log.h>
#include <pthread.h>

pthread_mutex_t mutex;
pthread_cond_t cond;
int count;

bool isOdd(int num) {
    return (num & 1) == 1;
}

void *print1(void *) {
    while (true) {
        pthread_mutex_lock(&mutex);

        while (!isOdd(count)) {
            pthread_cond_wait(&cond, &mutex);
        }
        LOGD("print1 count is: %d", count++);
        pthread_cond_signal(&cond);

        pthread_mutex_unlock(&mutex);

        if (count >= 100) {
            break;
        }
    }

    LOGD("print1 end");
    return 0;
}

void *print2(void *) {
    while (true) {
        pthread_mutex_lock(&mutex);

        while (isOdd(count)) {
            pthread_cond_wait(&cond, &mutex);
        }
        LOGD("print2 count is: %d", count++);
        pthread_cond_signal(&cond);

        pthread_mutex_unlock(&mutex);

        if (count >= 100) {
            break;
        }
    }

    LOGD("print2 end");
    return 0;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_baiiu_jnitest_thread_ThreadMutexFragment_print12to100(JNIEnv *env, jobject thiz) {
    LOGD("count init: %d", count);

    pthread_mutex_init(&mutex, nullptr);
    pthread_cond_init(&cond, nullptr);

    pthread_t handle1;
    pthread_t handle2;
    pthread_create(&handle1, nullptr, print1, nullptr);
    pthread_create(&handle2, nullptr, print2, nullptr);

}