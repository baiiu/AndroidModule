package com.example.BridgePattern.sample03;

/**
 * author: baiiu
 * date: on 16/7/21 14:24
 * description:
 */
public class DataSourceFile implements DataSource {
    @Override public void getData() {
        System.out.println("从文本文件中获取数据");
    }

    @Override public String toString() {
        return "从File Source中获取";
    }
}
