#include <jni.h>
#include <log.h>

//
// Created by baiiu on 2020/5/23.
//
/*

| boolean | jboolean |
| byte | jbyte |
| char | jchar |
| short | jshort |
| int | jint |
| long | jlong |
| float | jfloat |
| double | jdouble |


| All objects | jobject |
| java.lang.Class | jobject |
| java.lang.String | jstring |
| java.lang.Throwable | jthrowable |
| Object[] | jobjectArray |
| boolean[] | jbooleanArray |
| byte[] | jbyteArray |
| char[] | jcharArray |
| short[] | jshortArray |
| int[] | jintArray |
| long[] | jlongArray |
| float[] | jfloatArray |
| double[] | jdoubleArray |
 */

extern "C"
JNIEXPORT jstring JNICALL
Java_com_baiiu_jnitest_reference_ReferenceFragment_callNativeStringArray(JNIEnv *env,
                                                                         jobject thiz,
                                                                         jobjectArray str_array) {
    int length = env->GetArrayLength(str_array);
    LOGD("string[] length is %d", length);

    jobject obj = env->GetObjectArrayElement(str_array, 0);
    LOGD("obj: %%", obj);

    jstring jstr = static_cast<jstring>(obj);
    const char *s = env->GetStringUTFChars(jstr, JNI_FALSE);
    LOGD("str: %s", s);
    env->ReleaseStringUTFChars(jstr, s);

    return env->NewStringUTF(s);
}

extern "C"
JNIEXPORT jintArray JNICALL
Java_com_baiiu_jnitest_reference_ReferenceFragment_getIntArray(JNIEnv *env, jobject thiz,
                                                               jint num) {
    jintArray jarr = env->NewIntArray(num);

    int buf[num];
    for (int i = 0; i < num; ++i) {
        buf[i] = i;
    }

    env->SetIntArrayRegion(jarr, 0, num, buf);

    return jarr;
}


extern "C"
JNIEXPORT void JNICALL
Java_com_baiiu_jnitest_reference_ReferenceFragment_nativeSetNum(JNIEnv *env, jclass clazz) {
    jfieldID fieldId = env->GetStaticFieldID(clazz, "sCount", "I");
    jint origin = env->GetStaticIntField(clazz, fieldId);
    env->SetStaticIntField(clazz, fieldId, ++origin);
}


extern "C"
JNIEXPORT void JNICALL
Java_com_baiiu_jnitest_reference_ReferenceFragment_accessInstanceField(JNIEnv *env,
                                                                       jobject thiz,
                                                                       jobject animal) {
    jclass clazz = env->GetObjectClass(animal);
    jfieldID fieldId = env->GetFieldID(clazz, "name", "Ljava/lang/String;");

    jstring str = env->NewStringUTF("this is new name");
    env->SetObjectField(animal, fieldId, str);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_baiiu_jnitest_reference_ReferenceFragment_accessStaticField(JNIEnv *env,
                                                                     jobject thiz,
                                                                     jobject animal) {
    jclass clazz = env->GetObjectClass(animal);
    jfieldID fieldId = env->GetStaticFieldID(clazz, "num", "I");
    jint origin = env->GetStaticIntField(clazz, fieldId);
    LOGD("origin is %d", origin);
    env->SetStaticIntField(clazz, fieldId, ++origin);
}


extern "C"
JNIEXPORT void JNICALL
Java_com_baiiu_jnitest_reference_ReferenceFragment_nativeCallMethod(JNIEnv *env,
                                                                    jobject thiz,
                                                                    jobject animal) {
    jclass clazz = env->GetObjectClass(animal);

    /*
     * accumulate
     */
    jmethodID accumulateMethod = env->GetMethodID(clazz, "accumulate", "(I)I");
    jint intResult = env->CallIntMethod(animal, accumulateMethod, 1);
    LOGD("result: %d", intResult);

    /*
     *  doubleString
     */
    jmethodID doubleStringMethod = env->GetMethodID(clazz, "doubleString",
                                                    "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;");

    jstring s1 = env->NewStringUTF("1");
    jstring s2 = env->NewStringUTF("2");
    jobject obj = env->CallObjectMethod(animal, doubleStringMethod, s1, s2);
    jstring jstrResult = static_cast<jstring>(obj);
    const char *strResult = env->GetStringUTFChars(jstrResult, JNI_FALSE);
    LOGD("doubleString result: %s", strResult);
    env->ReleaseStringUTFChars(jstrResult, strResult);

    /*
     *  nativeCall
     */
    jmethodID nativeCallMethod = env->GetStaticMethodID(clazz, "nativeCall",
                                                        "([Ljava/lang/String;I)Ljava/lang/String;");

    int size = 2;
    jclass strClass = env->FindClass("java/lang/String");
    jobjectArray array = env->NewObjectArray(size, strClass, nullptr);

    jstring strItem;
    for (int i = 0; i < size; ++i) {
        strItem = env->NewStringUTF("string in native");
        env->SetObjectArrayElement(array, i, strItem);
    }

    jobject obj2 = env->CallStaticObjectMethod(clazz, nativeCallMethod, array, size);
    jstring jstring2 = static_cast<jstring>(obj2);
    const char *s = env->GetStringUTFChars(jstring2, JNI_FALSE);
    LOGD("nativeCall result: %s", s);
    env->ReleaseStringUTFChars(jstring2, s);

}
extern "C"
JNIEXPORT jobject JNICALL
Java_com_baiiu_jnitest_reference_ReferenceFragment_createAnimal(JNIEnv *env, jobject thiz) {
    jclass animalClass = env->FindClass("com/baiiu/jnitest/reference/Animal");
    jmethodID initMethod = env->GetMethodID(animalClass, "<init>", "(Ljava/lang/String;)V");
    jstring str = env->NewStringUTF("dog");
    jobject animal = env->NewObject(animalClass, initMethod, str);
    LOGD("animal: %p", animal);
    return animal;
}

extern "C"
JNIEXPORT jobject JNICALL
Java_com_baiiu_jnitest_reference_ReferenceFragment_createAnimal2(JNIEnv *env, jobject thiz) {
    jclass animalClass = env->FindClass("com/baiiu/jnitest/reference/Animal");
    jmethodID initMethod = env->GetMethodID(animalClass, "<init>", "(Ljava/lang/String;)V");

    jobject animal = env->AllocObject(animalClass);

    jstring str = env->NewStringUTF("dog2");
    env->CallNonvirtualVoidMethod(animal, animalClass, initMethod, str);

    LOGD("animal: %p", animal);

    return animal;
}