package com.example.AbstractFactory.sample01;

/**
 * author: baiiu
 * date: on 16/7/19 11:44
 * description: 抽象工厂模式
 * Sunny软件公司欲开发一套界面皮肤库，可以对Java桌面软件进行界面美化。为了保护版权，该皮肤库源代码不打算公开，而只向用户提供已打包为jar文件的class字节码文件。
 * 用户在使用时可以通过菜单来选择皮肤，不同的皮肤将提供视觉效果不同的按钮、文本框、组合框等界面元素.
 *
 * 提供两种皮肤,一种Spring风格的,一种Summer风格的.
 * 属于同一种皮肤下的各个风格样式要统一(产品族约束).
 *
 * 可以看出,新增加一个产品族很方便,但新增加一个产品类别很复杂,到处修改代码
 */
public class ZZMainClass {

    public static void main(String[] args) {
        /*
            可以通过读取配置文件获取该类实例
         */
        //SkinFactory sKinFactory = new SKinFactorySpring();
        SkinFactory sKinFactory = new SkinFactorySummer();

        Button button = sKinFactory.createButton();
        CheckBox checkBox = sKinFactory.createCheckBox();
        TextArea textArea = sKinFactory.createTextArea();

        button.display();
        checkBox.display();
        textArea.display();

    }

}
