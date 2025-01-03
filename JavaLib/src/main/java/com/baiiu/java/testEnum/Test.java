package com.baiiu.java.testEnum;

/**
 * Created by zhuzhe.zz on 2024/6/28
 *
 * @author zhuzhe.zz@bytedance.com
 */
public class Test {

    public static void main(String[] args) {
        EnumTest enumTest = new EnumTest();
        EnumTest enumTest1 = new EnumTest();
        EnumTest enumTest2 = new EnumTest();

        System.out.println("Test::: " + enumTest + ", " + enumTest1 + ", " + enumTest2);
        System.out.println("Test::: " + (enumTest.state == enumTest1.state) + ", " + (enumTest1.state == enumTest2.state));

        int i;
        for (i = 0; i < 10; ++i) {
            if (i == 5){
                break;
            }
        }
        System.out.println("i = " + i);
    }

}
