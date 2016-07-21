package com.example.CompositePattern.sample02;

/**
 * author: baiiu
 * date: on 16/7/21 17:23
 * description:
 */
public abstract class View {

    public abstract void addView(View view);

    public abstract void removeView(View view);

    public abstract View getView(int i);

    protected abstract void measure();

    protected abstract void layout();

    protected abstract void draw();

    public void requestLayout() {
        measure();
        layout();
        draw();
    }
}
