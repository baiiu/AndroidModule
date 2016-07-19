package com.example.AbstractFactory.sample01;

/**
 * author: baiiu
 * date: on 16/7/19 11:52
 * description:
 *
 * 声明一组用于创建一族产品的方法，每一个方法对应一种产品。
 */
public interface SkinFactory {
    Button createButton();

    CheckBox createCheckBox();

    TextArea createTextArea();

}
