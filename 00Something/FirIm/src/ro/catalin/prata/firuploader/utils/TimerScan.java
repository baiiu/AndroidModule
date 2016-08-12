package ro.catalin.prata.firuploader.utils;

import java.util.Timer;

/**
 * Created with IntelliJ IDEA.
 * User: will
 * Date: 15/8/12
 * Time: 下午11:38
 * To change this template use File | Settings | File Templates.
 */
public class TimerScan {
    Timer timer;
    long Interval = 30000;
    long delay = 30000;

    public TimerScan(){
        timer = new Timer();
    }

}


