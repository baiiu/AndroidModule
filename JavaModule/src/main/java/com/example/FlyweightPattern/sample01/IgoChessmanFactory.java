package com.example.FlyweightPattern.sample01;

import java.util.HashMap;

/**
 * author: baiiu
 * date: on 16/7/22 13:44
 * description: 享元工厂类
 */
public enum IgoChessmanFactory {
    INSTANCE;
    public static final int WHITE = 0;
    public static final int BLACK = 1;

    private HashMap<Integer, IgoChessman> hashMap;

    IgoChessmanFactory() {
        hashMap = new HashMap<>(2);
    }

    public IgoChessman getIgoChessman(int color) {
        IgoChessman igoChessman = hashMap.get(color);

        if (igoChessman == null) {
            if (WHITE == color) {
                igoChessman = new IgoChessmanWhite();
            } else if (BLACK == color) {
                igoChessman = new IgoChessmanBlack();
            }

            hashMap.put(color, igoChessman);
        }

        return igoChessman;
    }


}
