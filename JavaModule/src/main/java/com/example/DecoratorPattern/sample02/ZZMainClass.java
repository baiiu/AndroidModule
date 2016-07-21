package com.example.DecoratorPattern.sample02;

/**
 * auther: baiiu
 * time: 16/7/21 21 22:53
 * description:
 *
 * Sunny软件公司欲开发了一个数据加密模块，可以对字符串进行加密。
 * 最简单的加密算法通过对字母进行移位来实现，同时还提供了稍复杂的逆向输出加密，还提供了更为高级的求模加密。
 *
 * 用户先使用最简单的加密算法对字符串进行加密，如果觉得还不够可以对加密之后的结果使用其他加密算法进行二次加密，当然也可以进行第三次加密。
 */
public class ZZMainClass {

    public static void main(String[] args) {

        String s = "abcd";

        IEncrypt encryption = new Encryption(s);
        encryption = new DecoratorMove(encryption);
        encryption = new DecoratorReverse(encryption);
        encryption = new DecoratorMode(encryption);




        System.out.println(encryption.encrypt());

    }
}
