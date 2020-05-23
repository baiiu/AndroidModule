//
// Created by baiiu on 2020/5/23.
//

#include <log.h>
#include <jni.h>

jstring nativeGetString(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF("message from dynamic loader");
}

jint nativeSum(JNIEnv *env, jobject thiz, jint x, jint y) {
    return x + y;
}

int registerNativeMethods(JNIEnv
                          *env,
                          const char *className,
                          const JNINativeMethod *methods,
                          jint nMethods) {

    jclass clazz = env->FindClass(className);
    if (clazz == nullptr) {
        return JNI_FALSE;
    }

    if ((env->RegisterNatives(clazz, methods, nMethods) != JNI_OK)) {
        return JNI_FALSE;
    }

    return JNI_TRUE;
}

JNIEXPORT int JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env;

    if (vm->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_6) != JNI_OK) {
        return JNI_FALSE;
    }

    const char *className = "com/baiiu/jnitest/dynamicLoad/DynamicLoad";
    JNINativeMethod methods[] = {
            {"sum",             "(II)I",                (void *) nativeSum},
            {"getNativeString", "()Ljava/lang/String;", (void *) nativeGetString}
    };

//    jclass clazz = env->FindClass(className);
//    env->RegisterNatives(clazz, methods, 2);

    registerNativeMethods(env, className, methods, sizeof(methods) / sizeof(methods[0]));

    return JNI_VERSION_1_6;
}