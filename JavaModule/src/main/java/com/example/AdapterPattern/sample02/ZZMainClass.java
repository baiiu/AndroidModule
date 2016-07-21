package com.example.AdapterPattern.sample02;

/**
 * author: baiiu
 * date: on 16/7/20 17:54
 * description:
 */
public class ZZMainClass {
    public static void main(String[] args) {
        //拿了个中国插座去德国
        GBSocketInterface gbSocket = new GBSocket();

        //住在德国旅馆里
        Hotel hotel = new Hotel();

        // 拿出适配器,插在德国插座上
        SocketAdapter socketAdapter = new SocketAdapter(gbSocket);
        hotel.setSocket(socketAdapter);

        //充电
        hotel.charge();
    }
}
