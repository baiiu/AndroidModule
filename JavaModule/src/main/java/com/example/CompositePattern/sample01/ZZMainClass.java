package com.example.CompositePattern.sample01;

/**
 * author: baiiu
 * date: on 16/7/21 16:45
 * description: 组合模式
 *
 *
 * Sunny软件公司欲开发一个杀毒(AntiVirus)软件，
 * 该软件既可以对某个文件夹(Folder)杀毒，也可以对某个指定的文件(File)进行杀毒。
 *
 * 该杀毒软件还可以根据各类文件的特点，为不同类型的文件提供不同的杀毒方式，例如图像文件(ImageFile)和文本文件(TextFile)的杀毒方式就有所差异。现需要提供该杀毒软件的整体框架设计方案。
 */
public class ZZMainClass {

    public static void main(String[] args) {

        Folder folder1 = new Folder("Folder1");
        folder1.add(new ImageFile("ImageOne"));
        folder1.add(new TextFile("TextOne"));


        Folder folder2 = new Folder("Folder2");
        folder2.add(new ImageFile("ImageTwo"));
        folder2.add(new TextFile("TextTwo"));


        Folder folder3 = new Folder("Folder3");
        folder3.add(new ImageFile("ImageThree"));
        folder3.add(new TextFile("TextThree"));


        folder2.add(folder3);
        folder1.add(folder2);

        folder1.killVirus();
    }
}
