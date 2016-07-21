package com.example.AdapterPattern.sample02;

/**
 * author: baiiu
 * date: on 16/7/20 17:50
 * description: 德国宾馆
 *
 * 让德国插座支持中国插座 --> 充电
 */
public class Hotel {

    //旅馆中有一个德标的插口
    private DBSocketInterface dbSocket;

    public Hotel() {
    }

    public Hotel(DBSocketInterface dbSocket) {
        this.dbSocket = dbSocket;
    }

    public void setSocket(DBSocketInterface dbSocket) {
        this.dbSocket = dbSocket;
    }

    //旅馆中有一个充电的功能
    public void charge() {

        //使用德标插口充电
        dbSocket.powerWithTwoRound();
    }
}
