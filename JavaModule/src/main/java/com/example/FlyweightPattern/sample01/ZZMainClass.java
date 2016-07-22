package com.example.FlyweightPattern.sample01;

/**
 * auther: baiiu
 * time: 16/7/21 21 22:54
 * description:
 *
 * 围棋棋子的设计.很明显的享元模式
 */
public class ZZMainClass {

    public static void main(String[] args) {


        IgoChessman igoChessmanWhite1 =
                IgoChessmanFactory.INSTANCE.getIgoChessman(IgoChessmanFactory.WHITE);
        IgoChessman igoChessmanWhite2 =
                IgoChessmanFactory.INSTANCE.getIgoChessman(IgoChessmanFactory.WHITE);


        IgoChessman igoChessmanBlack1 =
                IgoChessmanFactory.INSTANCE.getIgoChessman(IgoChessmanFactory.BLACK);
        IgoChessman igoChessmanBlack2 =
                IgoChessmanFactory.INSTANCE.getIgoChessman(IgoChessmanFactory.BLACK);


        System.out.println(igoChessmanWhite1 == igoChessmanWhite2);
        System.out.println(igoChessmanBlack1 == igoChessmanBlack2);


        //只需要Draw时候画在不同的位置即可
        igoChessmanWhite1.display(new Coordinates(1, 1));
        igoChessmanWhite2.display(new Coordinates(2, 2));
        igoChessmanBlack1.display(new Coordinates(3, 3));
        igoChessmanBlack2.display(new Coordinates(6, 6));
    }
}
