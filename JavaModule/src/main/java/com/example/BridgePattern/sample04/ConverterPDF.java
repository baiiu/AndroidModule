package com.example.BridgePattern.sample04;


/**
 * author: baiiu
 * date: on 16/7/21 14:49
 * description:
 */
public class ConverterPDF extends Converter {
    public ConverterPDF(DB db) {
        super(db);
    }

    @Override public void convert() {
        System.out.println(db.toString() + "转化成PDF");
    }
}
