//
// Created by baiiu on 2020/5/30.
//
#include <jni.h>
#include <string>
#include <people.h>
#include <log.h>
#include <android/bitmap.h>

jobject generateBitmap(JNIEnv *env, uint32_t width, uint32_t height);

extern "C"
JNIEXPORT jobject JNICALL
Java_com_baiiu_jnitest_bitmap_BitmapFragment_callNativeMirrorBitmap(JNIEnv *env, jobject thiz,
                                                                    jobject bitmap) {

    AndroidBitmapInfo bitmapInfo;
    AndroidBitmap_getInfo(env, bitmap, &bitmapInfo);

    LOGD("bitmap width: %d", bitmapInfo.width);
    LOGD("bitmap height: %d", bitmapInfo.height);

    void *bitmapPixels;
    AndroidBitmap_lockPixels(env, bitmap, &bitmapPixels);

    // 镜像后bitmap像素数据
    uint32_t newWidth = bitmapInfo.width;
    uint32_t newHeight = bitmapInfo.height;
    uint32_t *newBitmapPixels = new uint32_t[newWidth * newHeight];

    int whereToGet = 0;
    for (int y = 0; y < newHeight; ++y) {
        for (int x = newWidth - 1; x >= 0; --x) {
            uint32_t pixel = ((uint32_t *) bitmapPixels)[whereToGet++];
            newBitmapPixels[newWidth * y + x] = pixel;
        }
    }
    AndroidBitmap_unlockPixels(env, bitmap);

    // 生成新的bitmap
    jobject newBitmap = generateBitmap(env, newWidth, newHeight);

    void *resultBitmapPixels;
    AndroidBitmap_lockPixels(env, newBitmap, &resultBitmapPixels);

    memcpy(resultBitmapPixels, newBitmapPixels, sizeof(uint32_t) * newWidth * newHeight);

    AndroidBitmap_unlockPixels(env, newBitmap);

    delete[] newBitmapPixels;
    return newBitmap;
}

jobject generateBitmap(JNIEnv *env, uint32_t width, uint32_t height) {

    // 创建新的bitmap对象
    jclass bitmapClass = env->FindClass("android/graphics/Bitmap");

    jmethodID mid = env->GetStaticMethodID(bitmapClass, "createBitmap",
                                           "(IILandroid/graphics/Bitmap$Config;)Landroid/graphics/Bitmap;");

    // 构造bitmapConfig对象
    jclass configClass = env->FindClass("android/graphics/Bitmap$Config");

    jmethodID bitmapConfigMid = env->GetStaticMethodID(configClass, "valueOf",
                                                       "(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;");

    jstring str = env->NewStringUTF("ARGB_8888");
    jobject bitmapConfig = env->CallStaticObjectMethod(configClass, bitmapConfigMid, configClass,
                                                       str);

    jobject newBitmap = env->CallStaticObjectMethod(bitmapClass, mid, width, height, bitmapConfig);

    return newBitmap;
}
