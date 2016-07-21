package com.example.BridgePattern.sample02;

/**
 * author: baiiu
 * date: on 16/7/21 10:21
 * description:
 *
 * Sunny软件公司欲开发一个跨平台图像浏览系统，
 * 要求该系统能够显示BMP、JPG、GIF、PNG等多种格式的文件，并且能够在Windows、Linux、Unix等多个操作系统上运行, 系统需具有较好的扩展性以支持新的文件格式和操作系统。
 *
 * 系统首先将各种格式的文件解析为像素矩阵(Matrix)，然后将像素矩阵显示在屏幕上，在不同的操作系统中可以调用不同的绘制函数来绘制像素矩阵。
 */
public class ZZMainClass {

    public static void main(String[] args) {
        Image image = new ImageJPG(new OSWindows());
        image.draw("filePath");


        image = new ImageGIF(new OSUnix());
        image.draw("filePath");
    }

}
