package com.example.AbstractFactory.sample01;

/**
 * author: baiiu
 * date: on 16/7/19 11:55
 * description:
 */
public class SkinFactorySummer implements SkinFactory {
    @Override public Button createButton() {
        return new ButtonSummer();
    }

    @Override public CheckBox createCheckBox() {
        return new CheckBoxSummer();
    }

    @Override public TextArea createTextArea() {
        return new TextAreaSummmer();
    }
}
