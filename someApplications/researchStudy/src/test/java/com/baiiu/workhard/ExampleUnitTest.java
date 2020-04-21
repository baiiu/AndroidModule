package com.baiiu.workhard;

import com.baiiu.workhard.spi.print.IPrint;

import org.junit.Test;

import java.util.Iterator;
import java.util.ServiceLoader;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void serviceLoader() throws Exception {
        ServiceLoader<IPrint> load = ServiceLoader.load(IPrint.class);

        Iterator<IPrint> iterator = load.iterator();
        while (iterator.hasNext()) {
            IPrint next = iterator.next();
            next.print("test");
        }

    }


}