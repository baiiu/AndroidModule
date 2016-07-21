package com.example.CompositePattern.sample02;

/**
 * author: baiiu
 * date: on 16/7/21 17:22
 * description:
 */
public class ZZMainClass {

    public static void main(String[] args) {

        View containerLayout = new ContainerLayout();
        containerLayout.addView(new TextView());
        containerLayout.addView(new ImageView());

        containerLayout.requestLayout();

    }
}
