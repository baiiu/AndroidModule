package com.example.FacadePattern.sample01;

/**
 * author: baiiu
 * date: on 16/7/22 10:23
 * description:
 */
public class FileWriter {
    
    public void write(String s, String path) {
        System.out.println("写出到文件 " + path + ", 内容为: " + s);
    }

}
