package com.example.BridgePattern.sample04;


/**
 * author: baiiu
 * date: on 16/7/21 14:49
 * description:
 */
public class ConverterXML extends Converter {
    public ConverterXML(DB db) {
        super(db);
    }

    @Override public void convert() {
        System.out.println("转化成XML");
    }
}
