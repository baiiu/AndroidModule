//
// Created by baiiu on 2020/5/30.
//
#include <jni.h>
#include <string>
#include <people.h>
#include <log.h>
#include <pthread.h>

pthread_mutex_t mutexM;
pthread_cond_t condM;

int resource;
bool flag = false; // 生产出来了

void *producer(void *) {
    while (resource < 10000) {
        pthread_mutex_lock(&mutexM);

        while (flag) {
            pthread_cond_wait(&condM, &mutexM);
        }
        flag = true;
        ++resource;
        LOGD("produce: %d", resource);

        pthread_cond_signal(&condM);

        pthread_mutex_unlock(&mutexM);
    }

    return 0;
}

void *consumer(void *) {
    while (resource < 10000) {
        pthread_mutex_lock(&mutexM);

        while (!flag) {
            pthread_cond_wait(&condM, &mutexM);
        }
        flag = false;
        LOGD("consume: %d", resource);

        pthread_cond_signal(&condM);

        pthread_mutex_unlock(&mutexM);
    }

    return 0;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_baiiu_jnitest_thread_ThreadMutexFragment_multi(JNIEnv *env, jobject thiz) {
    pthread_mutex_init(&mutexM, nullptr);
    pthread_cond_init(&condM, nullptr);

    LOGD("init is: %d", resource);

    pthread_t handleProducer;
    pthread_create(&handleProducer, nullptr, producer, nullptr);

    pthread_t handleConsumer;
    pthread_create(&handleConsumer, nullptr, consumer, nullptr);
}