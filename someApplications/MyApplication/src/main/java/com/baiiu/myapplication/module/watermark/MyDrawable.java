package com.baiiu.myapplication.module.watermark;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

/**
 * author: baiiu
 * date: on 17/2/4 11:48
 * description:
 */

public class MyDrawable extends Drawable {
    Paint mPaint;

    public MyDrawable() {
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#1A000000"));
        mPaint.setAntiAlias(true);
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setTextSize(54);
    }

    @Override public void draw(@NonNull Canvas canvas) {
        Rect r = getBounds();

        canvas.save();
        canvas.rotate(-30, r.left, r.bottom);
        canvas.drawText("哈哈哈哈哈哈哈", r.left, r.bottom, mPaint);
        canvas.restore();
    }

    @Override public int getIntrinsicHeight() {
        return (int) (Math.sqrt(3) / 3 * getIntrinsicWidth() + 0.5F);

    }

    @Override public int getIntrinsicWidth() {
        return (int) (mPaint.measureText("DecorationDraw") + 0.5F);
    }

    @Override public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
        invalidateSelf();
    }

    @Override public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

}
