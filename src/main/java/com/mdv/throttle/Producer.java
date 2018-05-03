package com.mdv.throttle;


import com.mdv.io.Queue;

public interface Producer {

    String getName();
    void stop();
    void setQueue(Queue queue);
    void sleep(long millisec);
    boolean isActive();
    void start();
    void slower(int millisec);
    void faster(int millisec);






}
