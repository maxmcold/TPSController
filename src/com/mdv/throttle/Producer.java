package com.mdv.throttle;


import com.mdv.io.Queue;

public interface Producer extends Runnable{

    String getName();
    void stop();
    void setQueue(Queue queue);
    void sleep(long millisec);
    boolean isActive();
    void start();






}
