package com.mdv.data;

import com.mdv.utils.Timer;

public class Message {

    public static String getMessage(String append, boolean lf){

        String lineFeed = (lf) ? "\n" : "";
        return Timer.getTimer().getFormettedDateTime()+" | Message from " + append +lineFeed;

    }
}
