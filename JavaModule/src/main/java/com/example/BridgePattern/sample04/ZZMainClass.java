package com.example.BridgePattern.sample04;

/**
 * author: baiiu
 * date: on 16/7/21 14:44
 * description:
 *
 * Sunny软件公司欲开发一个数据转换工具，
 * 可以将数据库中的数据转换成多种文件格式，例如txt、xml、pdf等格式，
 * 同时该工具需要支持多种不同的数据库。
 */
public class ZZMainClass {

    public static void main(String[] args) {

        Converter converter = new ConverterPDF(new DBSQL());
        converter.convert();

    }
}
