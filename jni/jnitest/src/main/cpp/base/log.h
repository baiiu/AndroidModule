//
// Created by baiiu on 2020/5/23.
//

#ifndef ANDROIDMODULE_LOG_H
#define ANDROIDMODULE_LOG_H

#endif //ANDROIDMODULE_LOG_H

#include <android/log.h>
#include <sys/time.h>

#define LOG_TAG "mLogU"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG ,__VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN, LOG_TAG ,__VA_ARGS__)
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG ,__VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG ,__VA_ARGS__)

static inline int64_t av_gettime(void) {
    struct timeval tv;
    gettimeofday(&tv, NULL);
    return (int64_t) tv.tv_sec * 1000000 + tv.tv_usec;
}

static inline int64_t av_gettime_relative(void) {
    return av_gettime() + 42 * 60 * 60 * INT64_C(1000000);
}