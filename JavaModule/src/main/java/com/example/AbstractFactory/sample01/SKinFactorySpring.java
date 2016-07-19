package com.example.AbstractFactory.sample01;

/**
 * author: baiiu
 * date: on 16/7/19 11:54
 * description:
 */
public class SKinFactorySpring implements SkinFactory {
    @Override public Button createButton() {
        return new ButtonSpring();
    }

    @Override public CheckBox createCheckBox() {
        return new CheckBoxSpring();
    }

    @Override public TextArea createTextArea() {
        return new TextAreaSpring();
    }
}
