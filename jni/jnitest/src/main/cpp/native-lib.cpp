//
// Created by zhu zhe on 2020-01-07.
//

#include <jni.h>
#include <string>
#include <people.h>
#include <log.h>
//#include "people/People.h"


extern "C" JNIEXPORT jstring JNICALL
Java_com_baiiu_jnitest_basic_BasicFragment_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    People people;

//    return env->NewStringUTF(hello.c_str());
    return env->NewStringUTF(people.getString().c_str());
}

extern "C"
JNIEXPORT void JNICALL
Java_com_baiiu_jnitest_basic_BasicFragment_testCallJava(JNIEnv *env, jobject thiz,
                                                        jobject activity) {
    // 首先获取MainActivity的类
    jclass cls = env->GetObjectClass(activity);
    // 获取这个类中的code成员变量ID
    jfieldID codeId = env->GetFieldID(cls, "code", "I");
    // 获取这个类中的msg成员变量ID
    jfieldID msgId = env->GetFieldID(cls, "msg", "Ljava/lang/String;");


    // 获取code成员变量的值
    jint code = env->GetIntField(activity, codeId);

    // 获取msg成员变量的值
    jstring msg = (jstring) env->GetObjectField(activity, msgId);


    // 获取java.lang.String对象中的内容
    const char *cMsg = env->GetStringUTFChars(msg, JNI_FALSE);
    // 打印日志
    LOGI("code = %d,msg = %s", code, cMsg);

    // 用完String后要释放
    env->ReleaseStringUTFChars(msg, cMsg);

    // 找到MainActivity类中的cCallJava函数
    jmethodID callJavaMethodId = env->GetMethodID(cls, "cCallJava", "(Ljava/lang/String;)V");
    // 创建一个java.lang.String对象，内容如下
    jstring nativeMsg = env->NewStringUTF("java method cCallJava called");
    // 调用java中的cCallJava方法
    env->CallVoidMethod(activity, callJavaMethodId, nativeMsg);

    // 这里的DeleteLocalRef可以不执行，在函数执行完毕后LocalRef会自动释放，
    // 但是在循环次数较多的循环中需要Delete，否则可能会溢出
    env->DeleteLocalRef(msg);
    env->DeleteLocalRef(nativeMsg);
    env->DeleteLocalRef(cls);
}