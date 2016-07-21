package com.example.DecoratorPattern.sample01;

/**
 * auther: baiiu
 * time: 16/7/21 21 22:32
 * description:
 */
public class DecoratorComponent extends Component {
    protected Component component;

    public DecoratorComponent(Component component) {
        this.component = component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    @Override public void display() {
        component.display();
    }
}
