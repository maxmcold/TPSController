package com.cvas;

public class Timer {

    private static long offset;
    private static Timer timer;
    private Timer(){ }

    public String currentTimeMillisec(){
        return new String(""+System.currentTimeMillis());

    }

    public static Timer getTimer(){
        if (null == timer) timer = new Timer();
        return timer;
    }
}
