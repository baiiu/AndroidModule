package com.example.DecoratorPattern.sample01;

/**
 * auther: baiiu
 * time: 16/7/21 21 22:30
 * description:
 */
public class ComponentWindow extends Component {
    @Override public void display() {
        System.out.println("显示窗体");
    }

    @Override public String toString() {
        return "Window窗体";
    }
}
