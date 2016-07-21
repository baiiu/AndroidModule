package com.example.CompositePattern.sample02;

/**
 * author: baiiu
 * date: on 16/7/21 17:24
 * description:
 */
public class TextView extends View {
    @Override public void addView(View view) {

    }

    @Override public void removeView(View view) {

    }

    @Override public View getView(int i) {
        return null;
    }

    @Override protected void measure() {
        System.out.println("TextView measure");
    }

    @Override protected void layout() {
        System.out.println("TextView layout");
    }

    @Override protected void draw() {
        System.out.println("TextView draw");
    }
}
