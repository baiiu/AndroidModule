//
// Created by baiiu on 2020/5/23.
//

#ifndef ANDROIDMODULE_LOG_H
#define ANDROIDMODULE_LOG_H

#endif //ANDROIDMODULE_LOG_H

#include <android/log.h>

#define LOG_TAG "mLogU"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG ,__VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN, LOG_TAG ,__VA_ARGS__)
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG ,__VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG ,__VA_ARGS__)
