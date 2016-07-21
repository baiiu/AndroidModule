package com.example.CompositePattern.sample02;

/**
 * author: baiiu
 * date: on 16/7/21 17:24
 * description:
 */
public class ImageView extends View {
    @Override public void addView(View view) {

    }

    @Override public void removeView(View view) {

    }

    @Override public View getView(int i) {
        return null;
    }

    @Override protected void measure() {
        System.out.println("ImageView measure");
    }

    @Override protected void layout() {
        System.out.println("ImageView layout");
    }

    @Override protected void draw() {
        System.out.println("ImageView draw");
    }
}
