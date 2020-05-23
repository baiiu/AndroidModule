#include <jni.h>

//
// Created by baiiu on 2020/5/23.
//
#include <log.h>


extern "C"
JNIEXPORT jboolean JNICALL
Java_com_baiiu_jnitest_typeConvert_TypeConvertFragment_callNativeBoolean(JNIEnv *env, jobject thiz,
                                                                         jboolean boolean) {
    LOGD("java boolean value is %d", boolean);

    jboolean b = static_cast<jboolean>(!boolean);

    return b;
}

extern "C"
JNIEXPORT jbyte JNICALL
Java_com_baiiu_jnitest_typeConvert_TypeConvertFragment_callNativeByte(JNIEnv *env, jobject thiz,
                                                                      jbyte byte) {
    LOGD("java byte value is %d", byte);

    jbyte b = static_cast<jbyte>(byte + 1);

    return b;
}

extern "C"
JNIEXPORT jchar JNICALL
Java_com_baiiu_jnitest_typeConvert_TypeConvertFragment_callNativeChar(JNIEnv *env, jobject thiz,
                                                                      jchar jchar1) {
    LOGD("java char value is %c", jchar1);

    jchar c = jchar1 - '0';

    return c;
}

extern "C"
JNIEXPORT jshort JNICALL
Java_com_baiiu_jnitest_typeConvert_TypeConvertFragment_callNativeShort(JNIEnv *env, jobject thiz,
                                                                       jshort jshort1) {
    LOGD("java short value is %d", jshort1);

    jshort s = static_cast<jshort>(jshort1 + 2);

    return s;
}


extern "C"
JNIEXPORT jint JNICALL
Java_com_baiiu_jnitest_typeConvert_TypeConvertFragment_callNativeInt(JNIEnv *env, jobject thiz,
                                                                     jint num) {
    LOGD("java int value is %d", num);

    jint number = num + 2;

    return number;
}


extern "C"
JNIEXPORT jlong JNICALL
Java_com_baiiu_jnitest_typeConvert_TypeConvertFragment_callNativeLong(JNIEnv *env, jobject thiz,
                                                                      jlong num) {
    LOGD("java long value is %lld", num);

    jlong number = num + 2;

    return number;
}


extern "C"
JNIEXPORT jfloat JNICALL
Java_com_baiiu_jnitest_typeConvert_TypeConvertFragment_callNativeFloat(JNIEnv *env, jobject thiz,
                                                                       jfloat num) {
    LOGD("java long value is %f", num);

    jfloat number = num + 2.333f;

    return number;
}


extern "C"
JNIEXPORT jdouble JNICALL
Java_com_baiiu_jnitest_typeConvert_TypeConvertFragment_callNativeDouble(JNIEnv *env, jobject thiz,
                                                                        jdouble num) {
    LOGD("java long value is %f", num);

    jdouble number = num + 2.333;

    return number;
}