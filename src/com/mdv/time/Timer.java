package com.mdv.time;

import com.mdv.throttle.Configuration;

import java.util.Calendar;

public class Timer {

    private static long offset;
    private static Timer timer;

    private Timer() {
    }

    public String currentTimeMillisec() {
        return new String("" + System.currentTimeMillis());

    }

    public String getFormettedDateTime() {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());

        String date = c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH) + "-" + c.get(Calendar.DAY_OF_MONTH);
        String time = c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND);

        String dateTime = date + " " + time;
        return dateTime;

    }

    public static Timer getTimer() {
        if (null == timer) timer = new Timer();
        return timer;
    }
}

