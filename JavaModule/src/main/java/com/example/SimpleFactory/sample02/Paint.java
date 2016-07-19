package com.example.SimpleFactory.sample02;

/**
 * author: baiiu
 * date: on 16/7/18 19:39
 * description:
 */
public abstract class Paint {
    public abstract void draw();

    public abstract void erase();

    public static final int PAINT_CIRCLE = 1;
    public static final int PAINT_RECT = 2;
    public static final int PAINT_TRIANGLE = 3;


    public static Paint getPaint(int paint) {
        Paint mPaint;

        switch (paint) {
            case PAINT_CIRCLE:
                mPaint = new CirclePaint();
                break;
            case PAINT_RECT:
                mPaint = new RectanglePaint();
                break;
            case PAINT_TRIANGLE:
                mPaint = new TrianglePaint();
                break;
            default:
                throw new UnSupportedShapeException();
        }

        return mPaint;
    }
}
