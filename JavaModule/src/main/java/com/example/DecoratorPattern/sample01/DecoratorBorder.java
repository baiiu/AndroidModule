package com.example.DecoratorPattern.sample01;

/**
 * auther: baiiu
 * time: 16/7/21 21 22:35
 * description:
 */
public class DecoratorBorder extends DecoratorComponent {
    public DecoratorBorder(Component component) {
        super(component);
    }


    @Override public void display() {
        System.out.println(component.toString() + " 添加边框");
        super.display();
    }
}
