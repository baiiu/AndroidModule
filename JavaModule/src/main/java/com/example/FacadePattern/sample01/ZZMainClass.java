package com.example.FacadePattern.sample01;

/**
 * auther: baiiu
 * time: 16/7/21 21 22:54
 * description:
 *
 * 某软件公司欲开发一个可应用于多个软件的文件加密模块，该模块可以对文件中的数据进行加密并将加密之后的数据存储在一个新文件中，
 * 具体的流程包括三个部分，分别是读取源文件、加密、保存加密之后的文件，其中，读取文件和保存文件使用流来实现，加密操作通过求模运算实现。
 *
 * 这三个操作相对独立，为了实现代码的独立重用，让设计更符合单一职责原则，这三个操作的业务代码封装在三个不同的类中。
 */
public class ZZMainClass {

    public static void main(String[] args) {

        //String s = new FileReader().readFile("一个路径");
        //String encrypt = new Encryption().encrypt(s);
        //new FileWriter().write(encrypt, "新的路径");

        new FacadeEncryption().encrypt("一个路径", "新的路径");
    }
}
