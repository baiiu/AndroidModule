#include <jni.h>
#include <log.h>
#include <string>

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

jintArray localObject;
jobject globalObject;

extern "C"
JNIEXPORT jstring JNICALL
Java_com_baiiu_jnitest_reference_PassReferenceFragment_callNativeStringArray(JNIEnv *env,
                                                                             jobject thiz,
                                                                             jobjectArray str_array) {
    LOGE("localObject: %p", localObject);
    LOGE("globalObject: %p", globalObject);

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
JNIEXPORT jobjectArray JNICALL
Java_com_baiiu_jnitest_reference_PassReferenceFragment_toStringArray(JNIEnv *env, jobject thiz,
                                                                     jintArray jarr) {
    localObject = jarr;
    globalObject = env->NewGlobalRef(jarr);
    LOGE("localObject: %p", localObject);
    LOGE("globalObject: %p", globalObject);

    int length = env->GetArrayLength(jarr);
    int *arr;
    arr = env->GetIntArrayElements(jarr, JNI_FALSE);


    jobjectArray jobjectArray = env->NewObjectArray(length,
                                                    env->FindClass("java/lang/String"),
                                                    nullptr);
    for (int i = 0; i < length; ++i) {

        jstring str = env->NewStringUTF(("this is: " + std::to_string(arr[i])).c_str());
        env->SetObjectArrayElement(jobjectArray, i, str);
    }

    env->ReleaseIntArrayElements(jarr, arr, 0);

    return jobjectArray;
}

extern "C"
JNIEXPORT jintArray JNICALL
Java_com_baiiu_jnitest_reference_PassReferenceFragment_getIntArray(JNIEnv *env, jobject thiz,
                                                                   jint num) {

    LOGE("localObject: %p", localObject);

    /*
     * Abort message: 'java_vm_ext.cc:545] JNI DETECTED ERROR IN APPLICATION: use of invalid jobject 0xffd2d890'
     * 崩溃：JNI层中创建的jobject对象默认是局部引用（Local Reference）。
     * 当函数从JNI层返回后，Local reference的对象很可能被回收。所以，不能在JNI层中永久保存一个Local Reference的对象。
     * 需要使用NewGlobalRef 和 NewWeakGlobalRef。
     */
//    LOGE("localObject: %p, %d", localObject, env->GetArrayLength(localObject));
    auto arrGlobal = static_cast<jarray>(globalObject);
    LOGE("localObject: %p, %d", localObject, env->GetArrayLength(arrGlobal));


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
Java_com_baiiu_jnitest_reference_PassReferenceFragment_nativeSetNum(JNIEnv *env, jclass clazz) {
    LOGE("localObject: %p", localObject);

    jfieldID fieldId = env->GetStaticFieldID(clazz, "sCount", "I");
    jint origin = env->GetStaticIntField(clazz, fieldId);
    env->SetStaticIntField(clazz, fieldId, ++origin);
}


extern "C"
JNIEXPORT void JNICALL
Java_com_baiiu_jnitest_reference_PassReferenceFragment_accessInstanceField(JNIEnv *env,
                                                                           jobject thiz,
                                                                           jobject animal) {
    LOGE("localObject: %p", localObject);

    jclass clazz = env->GetObjectClass(animal);
    jfieldID fieldId = env->GetFieldID(clazz, "name", "Ljava/lang/String;");

    jstring str = env->NewStringUTF("this is new name");
    env->SetObjectField(animal, fieldId, str);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_baiiu_jnitest_reference_PassReferenceFragment_accessStaticField(JNIEnv *env, jobject thiz,
                                                                         jobject animal) {
    LOGE("localObject: %p", localObject);

    jclass clazz = env->GetObjectClass(animal);
    jfieldID fieldId = env->GetStaticFieldID(clazz, "num", "I");
    jint origin = env->GetStaticIntField(clazz, fieldId);
    LOGD("origin is %d", origin);
    env->SetStaticIntField(clazz, fieldId, ++origin);
}


extern "C"
JNIEXPORT void JNICALL
Java_com_baiiu_jnitest_reference_PassReferenceFragment_nativeCallMethod(JNIEnv *env,
                                                                        jobject thiz,
                                                                        jobject animal) {
    LOGE("localObject: %p", localObject);

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
Java_com_baiiu_jnitest_reference_PassReferenceFragment_createAnimal(JNIEnv *env, jobject thiz) {
    LOGE("localObject: %p", localObject);

    jclass animalClass = env->FindClass("com/baiiu/jnitest/reference/Animal");
    jmethodID initMethod = env->GetMethodID(animalClass, "<init>", "(Ljava/lang/String;)V");
    jstring str = env->NewStringUTF("dog");
    jobject animal = env->NewObject(animalClass, initMethod, str);
    LOGD("animal: %p", animal);
    return animal;
}

extern "C"
JNIEXPORT jobject JNICALL
Java_com_baiiu_jnitest_reference_PassReferenceFragment_createAnimal2(JNIEnv *env, jobject thiz) {
    LOGE("localObject: %p", localObject);

    jclass animalClass = env->FindClass("com/baiiu/jnitest/reference/Animal");
    jmethodID initMethod = env->GetMethodID(animalClass, "<init>", "(Ljava/lang/String;)V");

    jobject animal = env->AllocObject(animalClass);

    jstring str = env->NewStringUTF("dog2");
    env->CallNonvirtualVoidMethod(animal, animalClass, initMethod, str);

    LOGD("animal: %p", animal);

    return animal;
}