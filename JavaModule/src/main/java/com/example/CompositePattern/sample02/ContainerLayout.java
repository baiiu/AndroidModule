package com.example.CompositePattern.sample02;

import java.util.ArrayList;
import java.util.List;

/**
 * author: baiiu
 * date: on 16/7/21 17:25
 * description:
 */
public class ContainerLayout extends View {
    private List<View> viewList = new ArrayList<>();


    @Override public void addView(View view) {
        viewList.add(view);
    }

    @Override public void removeView(View view) {
        viewList.remove(view);
    }

    @Override public View getView(int i) {
        return viewList.get(i);
    }

    @Override protected void measure() {
        System.out.println("ContainerLayout measure");

        for (View view : viewList) {
            view.measure();
        }
    }

    @Override protected void layout() {
        System.out.println("ContainerLayout layout");

        for (View view : viewList) {
            view.layout();
        }
    }

    @Override protected void draw() {
        System.out.println("ContainerLayout draw");

        for (View view : viewList) {
            view.draw();
        }
    }
}
