package com.baiiu.workhard.spi.print;

/**
 * author: zhuzhe
 * time: 2020-04-21
 * description:
 */
public class JavaPrint implements IPrint {
    @Override
    public void print(Object obj) {
        System.out.println(obj);
    }
}
