package com.example.AdapterPattern.sample02;

/**
 * author: baiiu
 * date: on 16/7/20 17:52
 * description:  中国插座
 */
public class GBSocket implements GBSocketInterface {

    @Override public void powerWithThreeFlat() {
        System.out.println("使用三项扁头插孔供电");
    }
}

