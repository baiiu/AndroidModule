package com.baiiu.workhard.bitmap

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.baiiu.library.LogUtil
import com.baiiu.workhard.R

/**
 * author: zhuzhe
 * time: 2020-04-13
 * description:
 */
class CountBitmap {

    // 一张 480*300 的png图片放在xxhdpi下，用RGB_8888加载，占用的内存大小计算
    fun count(context: Context) {
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.image_test)
        LogUtil.e("allocationByteCount: ${bitmap.allocationByteCount}")
        LogUtil.e("allocationByteCount: ${bitmap.byteCount}")

        LogUtil.e("config: ${bitmap.config}")
        LogUtil.e("wh: ${bitmap.width} , ${bitmap.height}")
        LogUtil.e("inDensity: ${context.resources.displayMetrics.density}")
        LogUtil.e("inTargetDensity: ${context.resources.displayMetrics.densityDpi}")
        LogUtil.e("hasAlpha: ${bitmap.hasAlpha()}")

        val inTargetDensity: Int = context.resources.displayMetrics.densityDpi
        val inDensity = 480F

        val scaledWidth: Int = (480 * inTargetDensity / inDensity + 0.5F).toInt()
        val scaledHeight: Int = (300 * inTargetDensity / inDensity + 0.5F).toInt()

        LogUtil.e("count: ${scaledWidth * scaledHeight * 4}")
    }

    fun use565(context: Context) {
        val opts: BitmapFactory.Options = BitmapFactory.Options()
        val inTargetDensity: Int = context.resources.displayMetrics.densityDpi
        val inDensity = 480F

        val scaledWidth: Int = (480 * inTargetDensity / inDensity + 0.5F).toInt()
        val scaledHeight: Int = (300 * inTargetDensity / inDensity + 0.5F).toInt()

        LogUtil.e("count: ${scaledWidth * scaledHeight * 4}")

        opts.inDensity = inDensity.toInt()
        opts.inTargetDensity = inTargetDensity
        opts.inPreferredConfig = Bitmap.Config.RGB_565 // 没乱用，看注释

        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.image_test, opts)
        LogUtil.e("allocationByteCount: ${bitmap.allocationByteCount}")
        LogUtil.e("allocationByteCount: ${bitmap.byteCount}")

        LogUtil.e("config: ${bitmap.config}") // ARGB_8888
        LogUtil.e("wh: ${bitmap.width} , ${bitmap.height}")
        LogUtil.e("inDensity: ${context.resources.displayMetrics.density}")
        LogUtil.e("inTargetDensity: ${context.resources.displayMetrics.densityDpi}")
        LogUtil.e("hasAlpha: ${bitmap.hasAlpha()}")
    }

    fun fromAssets(context: Context) {
        val bitmap = BitmapFactory.decodeStream(context.assets.open("test.png"))
        LogUtil.e("allocationByteCount: ${bitmap.allocationByteCount}")
        LogUtil.e("allocationByteCount: ${bitmap.byteCount}")

        LogUtil.e("config: ${bitmap.config}")
        LogUtil.e("hasAlpha: ${bitmap.hasAlpha()}")


        val scaledWidth = 480
        val scaledHeight = 300

        LogUtil.e("count: ${scaledWidth * scaledHeight * 4}")
    }


}