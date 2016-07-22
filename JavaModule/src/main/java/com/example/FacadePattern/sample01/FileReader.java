package com.example.FacadePattern.sample01;

/**
 * author: baiiu
 * date: on 16/7/22 10:21
 * description:
 */
public class FileReader {

    public String readFile(String path) {
        System.out.println("读取文件: " + path);
        return "读取文件:" + path;
    }
}
