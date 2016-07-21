package com.example.BridgePattern.sample03;

/**
 * author: baiiu
 * date: on 16/7/21 14:31
 * description:
 */
public class ExcelAdapter implements DataSource {

    private ExcelAPI excelAPI;


    public ExcelAdapter(ExcelAPI excelAPI) {
        this.excelAPI = excelAPI;
    }

    @Override public void getData() {
        excelAPI.getDataExcel();
    }

    @Override public String toString() {
        return "从excel获取数据";
    }
}
