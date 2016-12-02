package com.baiiu.performance.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import com.baiiu.library.LogUtil;

/**
 * author: baiiu
 * date: on 16/11/21 14:47
 * description: 测试内存抖动
 */

public class ChurnView extends View {
    public ChurnView(Context context) {
        super(context);
    }

    public ChurnView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChurnView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ChurnView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        for (int i = 0; i < 1000000; ++i) {
            paint = new Paint();
            paint.setColor(Color.BLUE);
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(1);
            paint.setAntiAlias(true);
        }

        int measuredWidth = getMeasuredWidth();
        canvas.drawCircle(measuredWidth / 2, measuredWidth / 2, 500, paint);
        LogUtil.d("哈哈哈哈");
        //postInvalidate();
    }
}
