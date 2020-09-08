LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := hello-mk
#LOCAL_C_INCLUDES := hello.h
LOCAL_SRC_FILES := hello.cpp

LOCAL_LDLIBS := -llog

include $(BUILD_SHARED_LIBRARY)