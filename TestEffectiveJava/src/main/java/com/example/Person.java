package com.example;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * author: baiiu
 * date: on 16/10/27 21:00
 * description:
 */
public class Person {

    // 每次调用时都会创建不必要的对象
    public static boolean isBabyBoomer(Date birthDate) {
        Calendar gmtCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        gmtCal.set(1946, Calendar.JANUARY, 1, 0, 0, 0);

        Date boomStart = gmtCal.getTime();

        gmtCal.set(1965, Calendar.JANUARY, 1, 0, 0, 0);
        Date boomEnd = gmtCal.getTime();

        return birthDate.compareTo(boomStart) >= 0 && birthDate.compareTo(boomEnd) < 0;
    }


    /*
        使用静态代码块来优化代码，提高程序性能。
        也可以延迟初始化，但请慎用。这样做会使方法的实现更为复杂，从而无法将性能显著提高到这种水平。
     */
    private static final Date BOOM_START;
    private static final Date BOOM_END;

    static {
        Calendar gmtCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        gmtCal.set(1946, Calendar.JANUARY, 1, 0, 0, 0);

        BOOM_START = gmtCal.getTime();

        gmtCal.set(1965, Calendar.JANUARY, 1, 0, 0, 0);
        BOOM_END = gmtCal.getTime();
    }

    public static boolean isBabyBoomerBetter(Date birthDate) {
        /*
            使用静态代码块进行初始化，避免总重复创建对象
         */
        return birthDate.compareTo(BOOM_START) >= 0 && birthDate.compareTo(BOOM_END) < 0;
    }

}
