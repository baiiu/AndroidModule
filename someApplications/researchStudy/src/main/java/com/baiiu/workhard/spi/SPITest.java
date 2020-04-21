package com.baiiu.workhard.spi;

import com.baiiu.workhard.spi.log.ILog;
import com.baiiu.workhard.spi.print.IPrint;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * author: zhuzhe
 * time: 2020-04-21
 * description:
 */
public class SPITest {

    public static void test() {
        ServiceLoader<IPrint> prints = ServiceLoader.load(IPrint.class);

        Iterator<IPrint> iterator = prints.iterator();
        while (iterator.hasNext()) {
            IPrint next = iterator.next();
            next.print("print");
        }

        ServiceLoader<ILog> logs = ServiceLoader.load(ILog.class);
        for (ILog log : logs) {
            log.log("log");
        }
    }


}
